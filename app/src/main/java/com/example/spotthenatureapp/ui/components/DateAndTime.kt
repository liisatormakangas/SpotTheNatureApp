package com.example.spotthenatureapp.ui.components

import android.annotation.SuppressLint
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.util.Date
import java.time.ZoneId


@RequiresApi(Build.VERSION_CODES.O)
@SuppressLint("SimpleDateFormat")
@Composable
fun dateAndTime(): String {
    val current = LocalDateTime.now()
    // Add 2 hours to the current time
    //val twoHoursLater = current.plusHours(2)
    val zoneId = ZoneId.systemDefault()
    val date = Date.from(current.atZone(zoneId).toInstant())
    val formatter = SimpleDateFormat("yyyy-MM-dd   HH:mm")
    val formatted = formatter.format(date)
    return formatted
}



