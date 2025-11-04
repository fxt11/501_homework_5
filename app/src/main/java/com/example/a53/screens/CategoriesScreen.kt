package com.example.a53.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun CategoriesScreen(
    onCategorySelected: (category: String) -> Unit,
    onNavigateHomeClearStack: () -> Unit,
) {
    Column(modifier = Modifier.fillMaxSize()) {
        Text(text = "Categories")
        Button(onClick = { onCategorySelected("Museums") }) { Text("Museums") }
        Button(onClick = { onCategorySelected("Parks") }) { Text("Parks") }
        Button(onClick = { onCategorySelected("Restaurants") }) { Text("Restaurants") }

        // Demonstrate clearing the stack back to Home from here too
        Button(onClick = onNavigateHomeClearStack) { Text("Go Home (clear stack)") }
    }
}


