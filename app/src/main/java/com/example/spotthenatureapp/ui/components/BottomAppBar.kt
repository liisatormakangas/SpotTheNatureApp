package com.example.spotthenatureapp.ui.components

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Place
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.List
import androidx.compose.material.icons.outlined.Place
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.spotthenatureapp.R


@Composable
fun MyBottomBar(navController: NavController) {
    val bottomBarItemsList = mutableListOf<BottomBarItem>()
    bottomBarItemsList.add(BottomBarItem(icon = Icons.Outlined.Home, name = "Home", route = "home"))
    bottomBarItemsList.add(BottomBarItem(icon = Icons.Outlined.List, name = "Observations", route = "list"))
    bottomBarItemsList.add(BottomBarItem(icon = Icons.Outlined.Place, name = "Map", route = "map"))

    NavigationBar(
        //make color partially transparent
        containerColor = MaterialTheme.colorScheme.tertiary.copy(alpha = 0.8f),
        //align the bottom bar to the bottom of the box
        modifier = Modifier
            .clip(MaterialTheme.shapes.medium),

    ) {
        bottomBarItemsList.forEachIndexed { index, item ->
            NavigationBarItem(
                icon = { Icon(item.icon, contentDescription = null) },
                label = { Text(item.name) },
                selected = true,
                onClick = {
                    navController.navigate(item.route)
                }
            )
        }
    }
    }


data class BottomBarItem(val icon: ImageVector, val name: String, val route: String)