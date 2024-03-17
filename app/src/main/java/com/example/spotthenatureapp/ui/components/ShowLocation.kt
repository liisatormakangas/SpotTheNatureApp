package com.example.spotthenatureapp.ui.components

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.spotthenatureapp.viewmodels.LocationViewModel
import kotlin.math.abs

@Composable
fun ShowLocation(locationViewModel: LocationViewModel) {
    val location by locationViewModel.getLocationLiveData().observeAsState()
    Log.d("ShowLocation", "Location: $location")

    var latitude = location?.latitude ?: 0.0
    var longitude = location?.longitude ?: 0.0

    val lat = formatLatitude(latitude)
    val lon = formatLongitude(longitude)

    Column {
        Text(
            text = "Latitude: $lat",
            style = MaterialTheme.typography.bodySmall,
            modifier = Modifier.padding(4.dp)
        )
        Text(
            text = "Longitude: $lon",
            style = MaterialTheme.typography.bodySmall,
            modifier = Modifier.padding(4.dp)
        )
    }
}

fun formatLongitude(longitude: Double): String {
    val absLongitude = abs(longitude)
    val degrees = absLongitude.toInt()
    val minutes = ((absLongitude - degrees) * 60).toInt()
    val seconds = ((absLongitude - degrees - (minutes / 60.0)) * 3600).toInt()
    val direction = if (longitude >= 0) "E" else "W"
    return String.format("%d°%02d'%03d''%s", degrees, minutes, seconds, direction)
}

fun formatLatitude(latitude: Double): String {
    val absLatitude = abs(latitude)
    val degrees = absLatitude.toInt()
    val minutes = ((absLatitude - degrees) * 60).toInt()
    val seconds = ((absLatitude - degrees - (minutes / 60.0)) * 3600).toInt()
    val direction = if (latitude >= 0) "N" else "S"
    return String.format("%d°%02d'%03d''%s", degrees, minutes, seconds, direction)
}
