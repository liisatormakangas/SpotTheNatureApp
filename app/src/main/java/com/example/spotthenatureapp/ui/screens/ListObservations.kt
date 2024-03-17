package com.example.spotthenatureapp.ui.screens

import android.os.Build
import android.util.Log
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.selection.selectable
import androidx.compose.material3.Button
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
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
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
    var selectedType by remember { mutableStateOf("Select type") }
    val scrollState = rememberScrollState()
    val radioButtonOptions = listOf("all observations", "list by type", "list by observation name", "list by date")
    val (selectedRadioOption, onOptionSelected) = remember { mutableStateOf(radioButtonOptions[0])}
    var searchString = ""
    var selectedDate by remember { mutableStateOf("") }
    var showDatePickerDialog by remember { mutableStateOf(false) }
    val context = LocalContext.current

    Log.d("ListObservations", "Observations: $allObservations")
    Scaffold(
        topBar = { SecondTopAppBar(navController) },
        bottomBar = { MyBottomBar(navController) },
        content = { paddingValues ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .scrollable(state = scrollState, orientation = Orientation.Vertical)
                    .padding(paddingValues = paddingValues),

                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Text (
                    text = "Select the desired observations listing:",
                    style = MaterialTheme.typography.bodyMedium)
                radioButtonOptions.forEach { text ->
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .fillMaxWidth()
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
                if (selectedRadioOption == "list by type") {
                    DropdownMenu(
                        selectedType = selectedType,
                        onSelectedTypeChange = { selectedType = it },
                    )
                    searchString = selectedType
                }
                if (selectedRadioOption == "list by observation name") {
                    OutlinedTextField(
                        value = "",
                        onValueChange = { searchString = it },
                        label = {
                            Text(
                                text = "Species or observation name",
                                style = MaterialTheme.typography.bodySmall
                            )
                        },
                        shape = MaterialTheme.shapes.medium,
                        textStyle = MaterialTheme.typography.bodySmall
                    )
                }
                if (selectedRadioOption == "list by date") {
                    DatePicker() { date ->
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
                MinorButton(text = "Get observations", onClick = {
                    observationsViewModel.getAllObservations()
                })
                if (allObservations.isNotEmpty()) {
                    ObservationsList(observationsViewModel, allObservations, selectedRadioOption, searchString)
                } else {
                    Text(text = "No observations yet.")
                }
            }
        }
    )
}