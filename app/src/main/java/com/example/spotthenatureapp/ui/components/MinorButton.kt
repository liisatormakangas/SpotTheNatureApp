package com.example.spotthenatureapp.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun MinorButton(text: String, onClick: () -> Unit){
    Button(
        shape = MaterialTheme.shapes.medium,
        border = BorderStroke(1.dp, color = MaterialTheme.colorScheme.tertiary),
        colors = ButtonDefaults.buttonColors(
            containerColor = MaterialTheme.colorScheme.tertiary.copy(alpha = 0.5f),
            contentColor = Color.Black
        ),
        modifier = Modifier
            .width(200.dp),
        onClick = {
            onClick()
        }) {
        Text(text)
    }
}

