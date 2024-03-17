package com.example.spotthenatureapp.ui.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import com.example.spotthenatureapp.model.ObservationEntity
import com.example.spotthenatureapp.viewmodels.SpotViewModel

@Composable
fun ObservationsList(observationsViewModel: SpotViewModel, observations: List<ObservationEntity>, radioOption: String, searchString: String) {
    var list: List<ObservationEntity> = emptyList()
    val observationListState = remember { mutableStateOf(observations) }
    LaunchedEffect(observations) {
        observationListState.value = observations
    }
    when (radioOption) {
        "all observations" -> list = observationListState.value
        "observation name" -> list = observations.filter { it.name.contains(searchString, ignoreCase = true) }
        "date" -> list = observations.filter {it.date.contains(searchString, ignoreCase = true)}
    }
    LazyColumn() {
        items(list.size) { index ->
            val observation = list[index]
            ElevatedCard(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                shape = MaterialTheme.shapes.medium,
                elevation = CardDefaults.cardElevation(
                    defaultElevation = 8.dp
                )
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column(modifier = Modifier
                        .padding(8.dp)
                    ) {
                        if (radioOption != "type") {
                            Text(
                                text = "Type: ${observation.type}",
                                style = MaterialTheme.typography.bodySmall
                            )
                        }
                        Text(
                            text = "Observation name: ${observation.name}",
                            style = MaterialTheme.typography.bodySmall)
                        Text(
                            text = "Scientific name: ${observation.scientificName}",
                            style = MaterialTheme.typography.bodySmall)
                        Text(
                            text = "Description: ${observation.description}",
                            style = MaterialTheme.typography.bodySmall
                        )
                        Text(
                            text = "Date: ${observation.date}",
                            style = MaterialTheme.typography.bodySmall
                        )
                        Text(
                            text = "Location: ${observation.latitude}, ${observation.longitude}",
                            style = MaterialTheme.typography.bodySmall
                        )
                        Text(text = "Optional location: ${observation.optionalLocation}",
                            style = MaterialTheme.typography.bodySmall)
                    }
                    IconButton(
                        onClick = {
                            observationListState.value = observationListState.value.filter { it.id != observation.id }
                            observationsViewModel.deleteObservation(observation.id)
                        },
                        content = {
                            Icon(
                                imageVector = Icons.Outlined.Delete,
                                contentDescription = null
                            )
                        }
                    )
                }
            }
        }
    }
}