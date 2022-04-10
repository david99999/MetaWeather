package com.demo.metaweather.screens

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.demo.metaweather.R
import com.demo.metaweather.utils.SPLASH_SECONDS
import com.demo.metaweather.utils.showSplash
import dagger.hilt.android.AndroidEntryPoint
import java.util.concurrent.TimeUnit

@AndroidEntryPoint
class WeatherExplorerActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        showSplash(savedInstanceState, TimeUnit.SECONDS.toMillis(SPLASH_SECONDS))
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}
