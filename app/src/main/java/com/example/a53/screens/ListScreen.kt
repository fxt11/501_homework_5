package com.example.a53.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun ListScreen(
    category: String,
    onLocationSelected: (locationId: Int) -> Unit,
    onNavigateUp: () -> Unit,
) {
    Column(modifier = Modifier.fillMaxSize()) {
        Text(text = "All $category")
        // Demo list of IDs
        Button(onClick = { onLocationSelected(1) }) { Text("Open #1") }
        Button(onClick = { onLocationSelected(42) }) { Text("Open #42") }
        Button(onClick = onNavigateUp) { Text("Back") }
    }
}


