package com.example.spotthenatureapp.ui.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.List
import androidx.compose.material.icons.outlined.Place
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavController


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