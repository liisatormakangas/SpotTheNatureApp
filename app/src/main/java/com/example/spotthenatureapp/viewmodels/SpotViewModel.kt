package com.example.spotthenatureapp.viewmodels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.flow.asStateFlow
import com.example.spotthenatureapp.model.NewObservation
import com.example.spotthenatureapp.model.ObservationDao
import com.example.spotthenatureapp.model.ObservationEntity

class SpotViewModel(private val observationDao: ObservationDao): ViewModel() {

    private val _newObservation = MutableStateFlow(NewObservation())
    val newObservation: StateFlow<NewObservation> = _newObservation.asStateFlow()

    private val _observationList = MutableStateFlow<List<ObservationEntity>>(emptyList())
    val observationList: StateFlow<List<ObservationEntity>> = _observationList.asStateFlow()

    fun saveNewObservation(newObservation: NewObservation) {
        viewModelScope.launch {
            _newObservation.value = newObservation
        }
        Log.d("AppViewModel", "Name: ${_newObservation.value} value")
    }
    fun changeNameInput(newName: String) {
        _newObservation.value = _newObservation.value.copy(name = newName)
    }
    fun changeScientificNameInput(newScientificName: String) {
        _newObservation.value = _newObservation.value.copy(scientificName = newScientificName)
    }
    fun changeDescriptionInput(newDescription: String) {
        _newObservation.value = _newObservation.value.copy(description = newDescription)
    }
    fun changeDateInput(newDate: String) {
        _newObservation.value = _newObservation.value.copy(date = newDate)
    }
    fun changeLatitudeInput(newLatitude: Double) {
        _newObservation.value = _newObservation.value.copy(latitude = newLatitude)
    }
    fun changeLongitudeInput(newLongitude: Double) {
        _newObservation.value = _newObservation.value.copy(longitude = newLongitude)
    }
    fun changeOptionalLocationInput(newOptionalLocation: String) {
        _newObservation.value = _newObservation.value.copy(optionalLocation = newOptionalLocation)
    }

    fun saveObservation(observation: NewObservation) {
        viewModelScope.launch {
            val observationEntity = ObservationEntity(
                type = observation.type,
                name = observation.name,
                scientificName = observation.scientificName,
                description = observation.description,
                date = observation.date,
                latitude = observation.latitude,
                longitude = observation.longitude,
                optionalLocation = observation.optionalLocation
            )
            observationDao.insertObservation(observationEntity)
        }
    }
    fun getAllObservations() {
        viewModelScope.launch {
            observationDao.getAllObservations()?.let {
                _observationList.value = it

            }
        }
    }

}

