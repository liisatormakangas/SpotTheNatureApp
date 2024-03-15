package com.example.spotthenatureapp.ui.screens

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.spotthenatureapp.ui.components.MyBottomBar
import com.example.spotthenatureapp.ui.components.SecondTopAppBar
import com.example.spotthenatureapp.viewmodels.SpotViewModel

@Composable
fun ListObservations(
    navController: NavController,
    observationsViewModel: SpotViewModel = viewModel()
) {
    val observationsList by observationsViewModel.observationList.collectAsState()

    Log.d("ListObservations", "Observations: $observationsList")
    Scaffold(
        topBar = { SecondTopAppBar(navController) },
        bottomBar = { MyBottomBar(navController) },
        content = { paddingValues ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues = paddingValues),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Text (text = "List of the earlier observations will be displayed here.")
            }
            if (observationsList.isNotEmpty()) {
                LazyColumn() {
                    items(observationsList.size) { index ->
                        Text(text = observationsList[index].name)
                    }
                }
            } else {
                Text(text = "No observations yet.")
            }
        }
    )
}