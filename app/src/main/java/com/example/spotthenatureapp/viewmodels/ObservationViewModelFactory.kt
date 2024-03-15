package com.example.spotthenatureapp.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.spotthenatureapp.model.ObservationDao

class ObservationViewModelFactory(
    private val observationDao: ObservationDao
): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SpotViewModel::class.java)) {
            return SpotViewModel(observationDao) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }


}