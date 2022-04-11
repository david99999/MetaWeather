package com.demo.metaweather.utils

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import javax.inject.Inject

/**
 * Parses dates returned from Metaweather API into human readable dates
 */
interface DateFormatter {
    fun getLongDateFromString(date: String): String
}

const val LONG_DATE_PATTERN = "EEEE, MMMM dd"
const val SHORT_DATE_PATTERN = "yyyy-MM-dd"

class WeatherDatesFormatter @Inject constructor() : DateFormatter {
    private val longDateFormatter = SimpleDateFormat(LONG_DATE_PATTERN, Locale.getDefault())
    private val shortFormatter = SimpleDateFormat(SHORT_DATE_PATTERN, Locale.getDefault())

    override fun getLongDateFromString(date: String) =
        longDateFormatter.format(shortFormatter.parse(date) ?: Date()).orEmpty()
}
