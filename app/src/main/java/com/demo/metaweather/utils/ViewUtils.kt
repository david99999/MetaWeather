package com.demo.metaweather.utils

import android.content.res.Configuration
import android.view.Gravity
import android.widget.FrameLayout
import androidx.fragment.app.Fragment

fun Fragment.ensureContentCentered(params: FrameLayout.LayoutParams?) {
    params?.apply {
        gravity = if (resources.configuration.orientation == Configuration.ORIENTATION_PORTRAIT) {
            Gravity.CENTER
        } else {
            Gravity.TOP
        }
    }
}
