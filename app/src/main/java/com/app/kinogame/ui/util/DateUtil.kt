package com.app.kinogame.ui.util

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

object DateUtil {

    fun format(time: Long): String{
        if(time == 0L) return "U TOKU";
        val simpleDateFormat = SimpleDateFormat("mm:ss", Locale.getDefault())
        return simpleDateFormat.format(Date(time))
    }

    fun formatTime(time: Long): String{
        val simpleDateFormat = SimpleDateFormat("HH:mm", Locale.getDefault())
        return simpleDateFormat.format(Date(time))
    }

    fun formatTimeResult(time: Long): String{
        val simpleDateFormat = SimpleDateFormat("dd.MM.yyyy HH:mm", Locale.getDefault())
        return simpleDateFormat.format(Date(time))
    }
}