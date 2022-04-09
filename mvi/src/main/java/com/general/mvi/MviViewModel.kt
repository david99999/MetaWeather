package com.general.mvi

import android.os.Looper
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

/**
 * Principal element of this reactive MVI approach, handles the actions and effects dispatching
 * on their proper coroutine context, exposes the #State, #UiState and #Event as #Flow so they
 * can be listened/collected in a pure Kotlin coroutine fashion
 */
open class MviViewModel<Action : Any, State : Any, UiState : Any, Effect : Any, Event : Any>(
    private val stateUpdater: StateUpdater<State, Action, Effect, Event>,
    private val effectsProcessor: EffectsProcessor<Effect, Action>,
    private val stateMapper: StateMapper<State, UiState>,
    initialState: State,
    initialEffects: Set<Effect> = emptySet()
) : ViewModel(), ActionDispatcher<Action>, EffectsDispatcher<Effect> {

    private val state = MutableStateFlow(initialState)
    private val events = MutableSharedFlow<Event>()
    private val uiState by lazy { state.map { stateMapper.mapToUiState(it) } }

    fun getStateFlow(): Flow<State> = state.asStateFlow()
    fun getUiStateFlow(): Flow<UiState> = uiState
    fun getEventsFlow(): Flow<Event> = events.asSharedFlow()

    init {
        if (initialEffects.isNotEmpty()) {
            dispatchEffects(initialEffects)
        }
    }

    private fun isOnMainThread(currentThread: Thread): Boolean {
        return currentThread == Looper.getMainLooper().thread
    }

    final override fun dispatchAction(action: Action) {
        assert(isOnMainThread(Thread.currentThread())) { "Actions must be dispatched on Main Thread" }
        val nextState = stateUpdater.processNewAction(action, state.value)
        if (nextState.effects.isNotEmpty()) {
            dispatchEffects(nextState.effects)
        }
        if (nextState.events.isNotEmpty()) {
            viewModelScope.launch {
                nextState.events.forEach { event ->
                    events.emit(event)
                }
            }
        }
        viewModelScope.launch {
            state.emit(nextState.state)
        }
    }

    final override fun dispatchEffects(effects: Set<Effect>) {
        effects.forEach { effect ->
            viewModelScope.launch {
                withContext(Dispatchers.IO) {
                    effectsProcessor.processEffect(effect).collect { resultAction ->
                        withContext(Dispatchers.Main) {
                            dispatchAction(resultAction)
                        }
                    }
                }
            }
        }
    }
}
