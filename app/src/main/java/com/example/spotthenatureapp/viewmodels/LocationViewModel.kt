package com.example.spotthenatureapp.viewmodels

import android.content.Context
import androidx.lifecycle.ViewModel
import com.example.spotthenatureapp.model.Location
import com.example.spotthenatureapp.model.LocationLiveData
import com.example.spotthenatureapp.model.NewObservation
import kotlinx.coroutines.flow.StateFlow

class LocationViewModel(context: Context): ViewModel() {
    private val locationLiveData = LocationLiveData(context)
    val locationData: Location? = locationLiveData.value

    fun getLocationLiveData() = locationLiveData




}