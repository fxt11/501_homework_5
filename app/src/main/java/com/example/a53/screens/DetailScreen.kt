package com.example.a53.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun DetailScreen(
    category: String,
    locationId: Int,
    onNavigateUp: () -> Unit,
    onDoneGoHomeClearStack: () -> Unit,
) {
    Column(modifier = Modifier.fillMaxSize()) {
        Text(text = "$category Detail")
        Text(text = "Location ID: $locationId")
        Button(onClick = onNavigateUp) { Text("Back") }
        Button(onClick = onDoneGoHomeClearStack) { Text("Home (clear stack)") }
    }
}


