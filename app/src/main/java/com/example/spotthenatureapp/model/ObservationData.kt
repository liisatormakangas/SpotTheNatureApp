package com.example.spotthenatureapp.model

data class NewObservation(
    var type: String = "",
    var name: String = "",
    var scientificName: String = "",
    var description: String = "",
    var date: String = "",
    var latitude: Double = 0.0,
    var longitude: Double = 0.0,
    var optionalLocation: String = "",
)
