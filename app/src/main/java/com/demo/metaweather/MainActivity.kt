package com.demo.metaweather

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.demo.metaweather.utils.SPLASH_SECONDS
import com.demo.metaweather.utils.showSplash
import java.util.concurrent.TimeUnit

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        showSplash(TimeUnit.SECONDS.toMillis(SPLASH_SECONDS))
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}