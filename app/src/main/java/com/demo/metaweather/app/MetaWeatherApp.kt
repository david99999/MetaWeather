package com.demo.metaweather.app

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

/**
 * Main entry point for the app, currently it only exists
 * for providing Hilt D.I. support
 */
@HiltAndroidApp
class MetaWeatherApp : Application()
