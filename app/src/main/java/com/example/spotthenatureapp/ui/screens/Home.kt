package com.example.spotthenatureapp.ui.screens

import android.os.Build
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.DateRange
import androidx.compose.material.icons.outlined.ExitToApp
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material.icons.outlined.Phone
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.spotthenatureapp.R
import com.example.spotthenatureapp.ui.components.DateButton
import com.example.spotthenatureapp.ui.components.DatePicker
import com.example.spotthenatureapp.ui.components.DropdownMenu
import com.example.spotthenatureapp.ui.components.MinorButton
import com.example.spotthenatureapp.ui.components.MyBottomBar
import com.example.spotthenatureapp.ui.components.MyTopAppBar
import com.example.spotthenatureapp.ui.components.dateAndTime


@Composable
fun ShowIMage() {
    Image(
        painter = painterResource(id = R.drawable.taustakuva),
        contentDescription = "Background image of the bottom bar",
        contentScale = androidx.compose.ui.layout.ContentScale.Crop,
    )
}
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun Home(navController: NavController) {
    var selectedType by remember { mutableStateOf("Select type") }
    val date = dateAndTime()
    var selectedDate by remember { mutableStateOf("") }
    var showDatePickerDialog by remember { mutableStateOf(false) }
    val context = LocalContext.current
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()
    val handler = LocalUriHandler.current
    var buttonEnabled by remember { mutableStateOf(false) }

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            ModalDrawerSheet {
                Text("Drawer title", modifier = Modifier.padding(16.dp))
                Divider()
                NavigationDrawerItem(
                    icon = { Icon(imageVector = Icons.Outlined.Settings, contentDescription = null) },
                    label = { Text(text = "Application rights") },
                    selected = false,
                    onClick = { /*TODO*/ }
                )
                NavigationDrawerItem(
                    icon = { Icon(imageVector = Icons.Outlined.ExitToApp, contentDescription = null) },
                    label = { Text(text = "LuontoPortti") },
                    selected = false,
                    onClick = { handler.openUri("https://luontoportti.com/")}
                )
                NavigationDrawerItem(
                    icon = { Icon(imageVector = Icons.Outlined.Info, contentDescription = null) },
                    label = { Text(text = "About") },
                    selected = false,
                    onClick = { /*TODO*/ }
                )
                NavigationDrawerItem(
                    icon = { Icon(imageVector = Icons.Outlined.Phone, contentDescription = null) },
                    label = { Text(text = "Contact") },
                    selected = false,
                    onClick = { /*TODO*/ }
                )
            }
        }) {
        Box {
            ShowIMage()
            Scaffold(
                topBar = { MyTopAppBar(drawerState, scope) },
                bottomBar = { MyBottomBar(navController) },
                containerColor = Color.Transparent,
                content = { paddingValues ->
                    Column(
                        modifier = Modifier
                            .padding(paddingValues = paddingValues)
                            .padding(top = 30.dp, bottom = 50.dp, start = 25.dp, end = 25.dp)
                            .fillMaxSize()
                            .clip(MaterialTheme.shapes.medium)
                            .background(MaterialTheme.colorScheme.background.copy(alpha = 0.93f)),
                        verticalArrangement = Arrangement.SpaceAround,
                        horizontalAlignment = Alignment.CenterHorizontally,

                        ) {
                        Text(
                            text = stringResource(R.string.heading),
                            style = MaterialTheme.typography.titleLarge,
                            color = MaterialTheme.colorScheme.primary,
                            textAlign = TextAlign.Center,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(top = 30.dp)
                        )
                        Text(
                            text = stringResource(R.string.startBy),
                            style = MaterialTheme.typography.bodySmall,
                            textAlign = TextAlign.Justify,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(top = 10.dp, start = 30.dp, end = 30.dp)
                        )
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(top = 10.dp, start = 30.dp, end = 30.dp),
                            verticalArrangement = Arrangement.Center,
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text(
                                text = stringResource(R.string.typeOfObs),
                                style = MaterialTheme.typography.bodyMedium,
                            )
                            Spacer(modifier = Modifier.height(10.dp))
                            DropdownMenu(
                                selectedType = selectedType,
                                onSelectedTypeChange = { selectedType = it },
                            )
                        }
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(bottom = 16.dp, start = 16.dp, end = 16.dp),
                            verticalArrangement = Arrangement.spacedBy(10.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text(
                                text = stringResource(R.string.useCurrDate),
                                style = MaterialTheme.typography.bodyMedium,
                                )
                            MinorButton(text = date, onClick = {
                                selectedDate = date;
                                Toast.makeText(context, "Date and time: $date", Toast.LENGTH_SHORT).show()
                            })
                            Text(
                                text = stringResource(R.string.orSelectDiff),
                                style = MaterialTheme.typography.bodyMedium,
                                )
                            DateButton(text = "Pick a date", onClick = {
                                showDatePickerDialog = true
                            })
                            if (showDatePickerDialog) {
                                DatePicker() { date ->
                                    selectedDate = date
                                    Toast.makeText(
                                        context,
                                        "Date selected: $date",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                    showDatePickerDialog = false
                                }
                            }
                            Spacer(modifier = Modifier.height(20.dp))
                            if (selectedType != "Select type" && selectedDate != "") {
                                buttonEnabled = true
                            }
                            Button(
                                enabled = buttonEnabled,
                                shape = MaterialTheme.shapes.medium,
                                modifier = Modifier
                                    .height(50.dp)
                                    .clip(MaterialTheme.shapes.medium),
                                onClick = {
                                    navController.navigate("addNew/$selectedType/$selectedDate")
                                }) {
                                Text(
                                        text = stringResource(R.string.addObs, selectedType),
                                    style = MaterialTheme.typography.bodySmall,
                                    color = Color.White
                                )
                            }

                        }
                    }
                }
            )
        }
    }
}
