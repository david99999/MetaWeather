package com.demo.metaweather.screens

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
import com.demo.metaweather.R
import com.demo.metaweather.utils.SPLASH_SECONDS
import com.demo.metaweather.utils.showSplash
import dagger.hilt.android.AndroidEntryPoint
import java.util.concurrent.TimeUnit

@AndroidEntryPoint
class WeatherExplorerActivity : AppCompatActivity() {

    private val navController by lazy { findNavController(R.id.navHostWeatherExplorer) }

    override fun onCreate(savedInstanceState: Bundle?) {
        showSplash(savedInstanceState, TimeUnit.SECONDS.toMillis(SPLASH_SECONDS))
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        NavigationUI.setupActionBarWithNavController(this, navController)
    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp() || super.onSupportNavigateUp()
    }
}
