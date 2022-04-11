package com.demo.metaweather.utils

import android.content.res.Configuration
import android.view.Gravity
import android.widget.FrameLayout
import androidx.fragment.app.Fragment

/**
 * Extension for showing centered content on a scrollview in portrait mode,
 * when showing centered content on landscape it becomes cut off on the top,
 * so we just set the gravity to top and it will distribute its content properly
 */
fun Fragment.ensureContentCentered(params: FrameLayout.LayoutParams?) {
    params?.apply {
        gravity = if (resources.configuration.orientation == Configuration.ORIENTATION_PORTRAIT) {
            Gravity.CENTER
        } else {
            Gravity.TOP
        }
    }
}
