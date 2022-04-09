package com.general.mvi

/**
 * Converts from #State into a #UiState
 */
interface StateMapper<State : Any, UiState : Any> {
    fun mapToUiState(state: State): UiState
}
