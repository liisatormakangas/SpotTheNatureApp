package com.example.spotthenatureapp

import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.core.app.ActivityCompat
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.spotthenatureapp.ui.screens.AddNewObservation
import com.example.spotthenatureapp.ui.screens.ConfirmObservation
import com.example.spotthenatureapp.ui.screens.Home
import com.example.spotthenatureapp.ui.screens.ListObservations
import com.example.spotthenatureapp.ui.theme.SpotTheNatureAppTheme
import com.example.spotthenatureapp.viewmodels.SpotViewModel
import androidx.room.Room
import com.example.spotthenatureapp.model.ObservationDatabase
import android.Manifest
import androidx.compose.foundation.background
import androidx.compose.ui.graphics.Color
import com.example.spotthenatureapp.viewmodels.LocationViewModel
import com.example.spotthenatureapp.viewmodels.ObservationViewModelFactory

class MainActivity : ComponentActivity() {
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val db = Room.databaseBuilder(
            applicationContext,
            ObservationDatabase::class.java, "observation-database"
        ).build()
        val observationDao = db.observationDao()

        val factory = ObservationViewModelFactory(observationDao)

        val observationsViewModel = ViewModelProvider(this, factory)[SpotViewModel::class.java]
        val locationViewModel: LocationViewModel = LocationViewModel(applicationContext)

        permissionForLocation(applicationContext, locationViewModel)

        setContent {
            SpotTheNatureAppTheme {
                Surface(
                    modifier = Modifier
                        .fillMaxSize()
                ) {
                    AppLayout(observationsViewModel, locationViewModel)
                }
            }
        }
    }
    private fun permissionForLocation(context: Context, locationViewModel: ViewModel) {
        if (ActivityCompat.checkSelfPermission(
                context,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                context,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            val locationPermissionRequest = registerForActivityResult(
                ActivityResultContracts.RequestMultiplePermissions()
            ) { permissions ->
                when {
                    permissions.getOrDefault(Manifest.permission.ACCESS_FINE_LOCATION, defaultValue = false) -> {
                        // The permission is granted for precise location
                        (locationViewModel as LocationViewModel).getLocationLiveData().getLocationData()

                    }
                    permissions.getOrDefault(Manifest.permission.ACCESS_COARSE_LOCATION, defaultValue = false) -> {
                        // The permission is granted for coarse location only
                        (locationViewModel as LocationViewModel).getLocationLiveData().getLocationData()


                    }
                    else -> {
                        // The permission is denied
                        Toast.makeText(
                            context,
                            "Location permission is required to use the app",
                            Toast.LENGTH_SHORT).show()
                    }
                }
            }
            locationPermissionRequest.launch(arrayOf(
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ))
        }
    }
}
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun AppLayout(observationsViewModel: ViewModel, locationViewModel: ViewModel) {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = "home"
    ) {
        composable("home") {
            Home(navController)
        }
        composable("addNew/{selectedType}/{selectedDate}") { backStackEntry ->
            AddNewObservation(
                navController,
                observationsViewModel as SpotViewModel,
                locationViewModel as LocationViewModel,
                backStackEntry.arguments?.getString("selectedType") ?: "",
                backStackEntry.arguments?.getString("selectedDate") ?: "")
        }
        composable("confirm/{locationAdded}") {backStackEntry ->
            ConfirmObservation(
                navController,
                observationsViewModel as SpotViewModel,
                locationViewModel as LocationViewModel,
                backStackEntry.arguments?.getString("locationAdded")?.toBoolean() ?: false)
        }
        composable("list") {
            ListObservations(
                navController,
                observationsViewModel as SpotViewModel
            )
        }
    }
}

