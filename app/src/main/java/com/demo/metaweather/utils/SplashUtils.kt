package com.demo.metaweather.utils

import android.animation.ObjectAnimator
import android.app.Activity
import android.os.Bundle
import android.view.View
import androidx.core.animation.doOnEnd
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.interpolator.view.animation.FastOutSlowInInterpolator
import com.demo.metaweather.R

const val SPLASH_SECONDS = 2L
const val DEFAULT_SPLASH_OUT_MILLISECONDS = 500L

/**
 * Extension method for showing a splash screen using the AndroidX Splashscreen library,
 * currently the splash is dismissed with a fade out animation, but it can be customized
 * to fulfill the UI Design requirements
 */
fun Activity.showSplash(
    savedInstanceState: Bundle?,
    splashDurationMilliSeconds: Long,
    outDurationMilliSeconds: Long = DEFAULT_SPLASH_OUT_MILLISECONDS
) {
    if (savedInstanceState == null) { // avoid showing splash on activity recreation
        val splashScreen = installSplashScreen()
        splashScreen.setOnExitAnimationListener { splashScreenView ->
            // smooth animation from fully visible (1F) to fully invisible (0F)
            val fadeOut = ObjectAnimator.ofFloat(splashScreenView.view, View.ALPHA, 1F, 0F)
            fadeOut.interpolator = FastOutSlowInInterpolator()
            fadeOut.duration = outDurationMilliSeconds
            fadeOut.startDelay = splashDurationMilliSeconds
            fadeOut.doOnEnd { splashScreenView.remove() }
            fadeOut.start()
        }
    } else { // on activity recreation we set the normal theme, otherwise activity crashes
        setTheme(R.style.Theme_MetaWeather)
    }
}
