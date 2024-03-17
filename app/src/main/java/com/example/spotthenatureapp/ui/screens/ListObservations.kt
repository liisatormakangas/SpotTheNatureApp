package com.example.spotthenatureapp.ui.screens

import android.os.Build
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.selection.selectable
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.spotthenatureapp.R
import com.example.spotthenatureapp.ui.components.DatePicker
import com.example.spotthenatureapp.ui.components.DropdownMenu
import com.example.spotthenatureapp.ui.components.MinorButton
import com.example.spotthenatureapp.ui.components.MyBottomBar
import com.example.spotthenatureapp.ui.components.ObservationsList
import com.example.spotthenatureapp.ui.components.SecondTopAppBar
import com.example.spotthenatureapp.viewmodels.SpotViewModel

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun ListObservations(
    navController: NavController,
    observationsViewModel: SpotViewModel = viewModel()
) {
    val allObservations by observationsViewModel.observationList.collectAsState()
    var selectedType by rememberSaveable { mutableStateOf("Select type") }
    val scrollState = rememberScrollState()
    val radioButtonOptions = listOf("all observations", "list by type", "list by observation name", "list by date")
    val (selectedRadioOption, onOptionSelected) = remember { mutableStateOf(radioButtonOptions[0])}
    var searchString by rememberSaveable { mutableStateOf("") }
    var selectedDate by rememberSaveable { mutableStateOf("") }
    var showDatePickerDialog by rememberSaveable { mutableStateOf(false) }
    val context = LocalContext.current

    Scaffold(
        topBar = { SecondTopAppBar(navController) },
        bottomBar = { MyBottomBar(navController) },
        content = { paddingValues ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .scrollable(state = scrollState, orientation = Orientation.Vertical)
                    .padding(paddingValues = paddingValues),

                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Text (
                    text = stringResource(R.string.select_the_desired_observations_listing),
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier.padding(20.dp)
                )
                radioButtonOptions.forEach { text ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(30.dp)
                            .selectable(
                                selected = (text == selectedRadioOption),
                                onClick = {
                                    onOptionSelected(text)
                                }
                            )
                            .padding(horizontal = 16.dp)
                    ) {
                        RadioButton(
                            selected = (text == selectedRadioOption),
                            onClick = { onOptionSelected(text) }
                        )
                        Text(
                            text = text,
                            style = MaterialTheme.typography.bodySmall,
                            modifier = Modifier.padding(start = 16.dp)
                        )
                    }
                }
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(20.dp)
                ) {
                    if (selectedRadioOption == "list by type") {
                        DropdownMenu(
                            selectedType = selectedType,
                            onSelectedTypeChange = { selectedType = it },
                        )
                        searchString = selectedType
                    }
                    if (selectedRadioOption == "list by observation name") {
                        OutlinedTextField(
                            value = searchString,
                            onValueChange = { searchString = it },
                            label = {
                                Text(
                                    text = stringResource(R.string.species_or_observation_name),
                                    style = MaterialTheme.typography.bodySmall
                                )
                            },
                            shape = MaterialTheme.shapes.medium,
                            textStyle = MaterialTheme.typography.bodySmall
                        )
                    }
                    if (selectedRadioOption == "list by date") {
                        MinorButton(
                            text = "Select date",
                            onClick = {
                                showDatePickerDialog = true
                            }
                        )
                        if (showDatePickerDialog) {
                            DatePicker { date ->
                                selectedDate = date
                                Toast.makeText(
                                    context,
                                    "Date selected: $date",
                                    Toast.LENGTH_SHORT
                                ).show()
                                showDatePickerDialog = false
                            }
                            searchString = selectedDate
                        }
                    }
                }
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 20.dp)
                ) {
                    MinorButton(text = stringResource(R.string.get_observations), onClick = {
                        observationsViewModel.getAllObservations()
                    })
                }

                if (allObservations.isNotEmpty()) {
                    ObservationsList(observationsViewModel, allObservations, selectedRadioOption, searchString)
                } else {
                    Text(
                        text = stringResource(R.string.no_observations_yet),
                        style = MaterialTheme.typography.bodyMedium)
                }
            }
        }
    )
}