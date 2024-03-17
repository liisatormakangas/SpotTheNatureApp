package com.example.spotthenatureapp.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Delete
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.spotthenatureapp.R
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
        "list by type" -> list = observations.filter { it.type.contains(searchString, ignoreCase = true) }
        "list by observation name" -> list = observations.filter { it.name.contains(searchString, ignoreCase = true) }
        "list by date" -> list = observations.filter {it.date.contains(searchString, ignoreCase = true)}
    }
    LazyColumn {
        items(list.size) { index ->
            val observation = list[index]
            ElevatedCard(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
                    .background(color = Color.White)
                    .border(width = 0.5.dp, color = Color.DarkGray, shape = MaterialTheme.shapes.medium),
                shape = MaterialTheme.shapes.medium,
                elevation = CardDefaults.cardElevation(
                    defaultElevation = 8.dp
                )
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column(
                        modifier = Modifier
                            .padding(8.dp)
                            .background(color = Color.White)
                            .clip(shape = MaterialTheme.shapes.medium)
                    ) {
                        if (radioOption != "type") {
                            Text(
                                text = stringResource(R.string.type, observation.type),
                                style = MaterialTheme.typography.bodySmall
                            )
                        }
                        Text(
                            text = stringResource(R.string.observation_name, observation.name),
                            style = MaterialTheme.typography.bodySmall)
                        Text(
                            text = stringResource(
                                R.string.scientific_name_obs_listing,
                                observation.scientificName
                            ),
                            style = MaterialTheme.typography.bodySmall)
                        Text(
                            text = stringResource(
                                R.string.description_obs_listing,
                                observation.description
                            ),
                            style = MaterialTheme.typography.bodySmall
                        )
                        Text(
                            text = stringResource(R.string.date_obs_listring, observation.date),
                            style = MaterialTheme.typography.bodySmall
                        )
                        Text(
                            text = stringResource(
                                R.string.location_obs_listing,
                                observation.latitude,
                                observation.longitude
                            ),
                            style = MaterialTheme.typography.bodySmall
                        )
                        Text(text = stringResource(
                            R.string.optional_location_obs_listing,
                            observation.optionalLocation
                        ),
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