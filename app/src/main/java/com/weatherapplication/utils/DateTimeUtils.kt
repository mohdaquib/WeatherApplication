package com.weatherapplication.utils

import java.text.SimpleDateFormat
import java.util.*

class DateTimeUtils {
    companion object {
        fun nextDay(position: Int): String {
            val sdf = SimpleDateFormat("EEEE")
            val calendar = GregorianCalendar()
            calendar.add(Calendar.DATE, position)
            return sdf.format(calendar.time)
        }
    }

}