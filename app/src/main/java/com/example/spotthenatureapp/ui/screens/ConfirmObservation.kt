package com.example.spotthenatureapp.ui.screens

import android.annotation.SuppressLint
import android.os.Build
import android.util.Log
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.DateRange
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.spotthenatureapp.R
import com.example.spotthenatureapp.ui.components.DatePicker
import com.example.spotthenatureapp.ui.components.MyBottomBar
import com.example.spotthenatureapp.ui.components.SecondTopAppBar
import com.example.spotthenatureapp.ui.components.ShowLocation
import com.example.spotthenatureapp.viewmodels.LocationViewModel
import com.example.spotthenatureapp.viewmodels.SpotViewModel


@RequiresApi(Build.VERSION_CODES.O)
@SuppressLint("StateFlowValueCalledInComposition")
@Composable
fun ConfirmObservation(
    navController: NavController,
    observationsViewModel: SpotViewModel = viewModel(),
    locationViewModel: LocationViewModel = viewModel(),
    locationAdded: Boolean
) {
    val observation by observationsViewModel.newObservation.collectAsState()
    var showDatePickerDialog by remember { mutableStateOf(false) }
    val context = LocalContext.current

    Log.d("ConfirmObservation", "Name: ${observation.name}  as new value")

    Scaffold(
        topBar = { SecondTopAppBar(navController) },
        bottomBar = { MyBottomBar(navController) },
        content = { paddingValues ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues = paddingValues),
                verticalArrangement = Arrangement.SpaceEvenly,
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                if (observation.type == "Other") {
                    Text (text = "Observation")
                }
                Text (
                    text = observation.type,
                    style = MaterialTheme.typography.bodyMedium)
                OutlinedTextField(
                    value = observation.name,
                    onValueChange = { observationsViewModel.changeNameInput(it) },
                    label = { Text(
                        if (observation.type == "Other") stringResource(R.string.observation_confirm) else observation.type + " species") },
                    shape = MaterialTheme.shapes.medium,
                    textStyle = MaterialTheme.typography.bodySmall
                )
                if (observation.type != "Other") {
                    OutlinedTextField(
                        value = observation.scientificName,
                        onValueChange = { observationsViewModel.changeScientificNameInput(it) },
                        label = { Text(
                            text = stringResource(R.string.scientific_name_confirm),
                            style = MaterialTheme.typography.bodySmall
                        ) },
                        shape = MaterialTheme.shapes.medium,
                        textStyle = MaterialTheme.typography.bodySmall
                    )
                }
                OutlinedTextField(
                    value = observation.description,
                    onValueChange = { observationsViewModel.changeDescriptionInput(it) },
                    label = { Text(
                        text = stringResource(R.string.description),
                        style = MaterialTheme.typography.bodySmall
                    ) },
                    shape = MaterialTheme.shapes.medium,
                    textStyle = MaterialTheme.typography.bodySmall
                )
                Text(
                    text = stringResource(R.string.date, observation.date),
                    style = MaterialTheme.typography.bodyMedium
                )
                Button(
                    shape = MaterialTheme.shapes.medium,
                    border = BorderStroke(1.dp, color = MaterialTheme.colorScheme.tertiary),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.tertiary.copy(alpha = 0.5f),
                        contentColor = Color.Black
                    ),
                    modifier = Modifier
                        .width(200.dp),
                    onClick = { showDatePickerDialog = true }
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        Text(text = stringResource(R.string.change_date))
                        Icon(
                            imageVector = Icons.Outlined.DateRange,
                            contentDescription = null,
                            modifier = Modifier.padding(start = 30.dp)
                        )
                    }
                }
                if (showDatePickerDialog) {
                    DatePicker { date ->
                        observationsViewModel.changeDateInput(date)
                        Toast.makeText(
                            context,
                            "Date selected: $date",
                            Toast.LENGTH_SHORT
                        ).show()
                        showDatePickerDialog = false
                    }
                }
                if (locationAdded) {
                    Column(
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = stringResource(R.string.location_confirm),
                            style = MaterialTheme.typography.bodyMedium
                        )
                        ShowLocation(locationViewModel)
                    }
                } else {
                    OutlinedTextField(
                        value = observation.optionalLocation,
                        onValueChange = { observationsViewModel.changeOptionalLocationInput(it) },
                        label = { Text(
                            text = stringResource(R.string.optional_location),
                            style = MaterialTheme.typography.bodySmall
                        ) },
                        shape = MaterialTheme.shapes.medium,
                        textStyle = MaterialTheme.typography.bodySmall
                    )
                }
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(30.dp),
                ) {
                    Button(
                        shape = MaterialTheme.shapes.medium,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(75.dp)
                            .padding(10.dp),
                        onClick = {
                            observationsViewModel.saveObservation(observation)
                            navController.navigate("list")
                        }) {
                        Text(
                            text = stringResource(R.string.save_observation),
                            style = MaterialTheme.typography.bodyMedium)
                    }
                }

            }
        }
    )
}