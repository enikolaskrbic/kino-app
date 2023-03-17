package com.app.kinogame.data.api.dto

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

data class KinoResultRequest(
    val gameId: Int = 1100,
    val fromDate: String = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(Date()),
    val toDate: String = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(Date()),
)