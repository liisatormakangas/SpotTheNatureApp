package com.example.spotthenatureapp.ui.screens

import android.annotation.SuppressLint
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.spotthenatureapp.model.NewObservation
import com.example.spotthenatureapp.ui.components.MyBottomBar
import com.example.spotthenatureapp.ui.components.MyTopAppBar
import com.example.spotthenatureapp.ui.components.SecondTopAppBar
import com.example.spotthenatureapp.ui.components.ShowLocation
import com.example.spotthenatureapp.viewmodels.LocationViewModel
import com.example.spotthenatureapp.viewmodels.SpotViewModel
import kotlin.math.sin


@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun AddNewObservation(
    navController: NavController,
    observationsViewModel: SpotViewModel,
    locationViewModel: LocationViewModel,
    selectedType: String,
    selectedDate: String
) {
    ObservationInput(
        navController,
        observationsViewModel,
        locationViewModel,
        saveObservation = observationsViewModel::saveNewObservation,
        selectedType,
        selectedDate
    )
}
@SuppressLint("StateFlowValueCalledInComposition")
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun ObservationInput(
    navController: NavController,
    observationsViewModel: SpotViewModel,
    locationViewModel: LocationViewModel,
    saveObservation: (NewObservation) -> Unit,
    selectedType: String,
    selectedDate: String
) {
    val observation by observationsViewModel.newObservation.collectAsState()
    val location by locationViewModel.getLocationLiveData().observeAsState()
    Log.d("AddNewObservation", "location: ${location} value")
    var locationAdded by rememberSaveable { mutableStateOf(true) }
    val scrollState = rememberScrollState()

    Scaffold(
        topBar = { SecondTopAppBar(navController) },
        bottomBar = { MyBottomBar(navController) },
        content = { paddingValues ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .scrollable(state = scrollState, orientation = Orientation.Vertical)
            ) {
                Column(
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(20.dp)
                ) {
                    Text(
                        text = "Enter details of the observation:",
                        style = MaterialTheme.typography.bodyMedium,
                        textAlign = TextAlign.Center
                    )
                    Spacer(modifier = Modifier.height(10.dp))
                    OutlinedTextField(
                        value = observation.name,
                        onValueChange = {  observationsViewModel.changeNameInput(it) },
                        label = {
                            Text(
                                text = if (selectedType === "Other") "Observation" else "$selectedType species",
                                style = MaterialTheme.typography.bodySmall
                            )
                        },
                        shape = MaterialTheme.shapes.medium,
                        textStyle = MaterialTheme.typography.bodySmall
                    )
                    if (selectedType != "Other") {
                        OutlinedTextField(
                            value = observation.scientificName,
                            onValueChange = { observationsViewModel.changeScientificNameInput(it) },
                            label = {
                                Text(
                                    text = "Scientific Name",
                                    style = MaterialTheme.typography.bodySmall,
                                )
                            },
                            shape = MaterialTheme.shapes.medium,
                            textStyle = MaterialTheme.typography.bodySmall
                        )
                    }
                    OutlinedTextField(
                        value = observation.description,
                        onValueChange = { observationsViewModel.changeDescriptionInput(it)},
                        label = {
                            Text(
                                text = "Describe the observation",
                                style = MaterialTheme.typography.bodySmall
                            )
                        },
                        shape = MaterialTheme.shapes.medium,
                        textStyle = MaterialTheme.typography.bodySmall
                    )
                }
                Column(
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(10.dp)
                ) {
                    Text(
                        text = "Location:",
                        style = MaterialTheme.typography.bodyMedium)
                    Spacer(modifier = Modifier.height(10.dp))

                    ShowLocation(locationViewModel)

                    Spacer(modifier = Modifier.height(10.dp))
                    TextButton(
                        onClick = {
                            observationsViewModel.changeLatitudeInput(0.0)
                            observationsViewModel.changeLongitudeInput(0.0)
                            locationAdded = false
                        }
                    ) {
                        Text(
                            text = "Click here if you wish not to use the current location but enter location manually below",
                            style = MaterialTheme.typography.bodySmall,
                            textAlign = TextAlign.Center
                        )

                    }
                    OutlinedTextField(
                        value = observation.optionalLocation,
                        onValueChange = { observationsViewModel.changeOptionalLocationInput(it) },
                        label = { Text(
                            text = "Location (optional)",
                            style = MaterialTheme.typography.bodySmall) },
                        shape = MaterialTheme.shapes.medium,
                        textStyle = MaterialTheme.typography.bodySmall
                    )
                }
                Column(
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier
                        .fillMaxWidth()
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        Text (
                            text = "Download image",
                            style = MaterialTheme.typography.bodyMedium)
                        Icon(
                            modifier = Modifier.padding(16.dp),
                            imageVector = Icons.Outlined.Add,
                            contentDescription = null
                        )
                    }
                }
                Button(
                    shape = MaterialTheme.shapes.medium,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(75.dp)
                        .padding(10.dp),
                    onClick = {
                        // add values in observation
                        observation.type = selectedType
                        observation.date = selectedDate
                        observation.latitude = location?.latitude!!
                        observation.longitude = location?.longitude!!
                        saveObservation(observation)
                        Log.d("AddNewObservation", "Name: ${observation} New values")

                        navController.navigate("confirm/$locationAdded")
                    }) {
                    Text(
                        text = "Go to Confirm Observation",
                        style = MaterialTheme.typography.bodySmall
                    )
                }
            }

        }
    )
}


