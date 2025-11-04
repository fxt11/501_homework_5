package com.example.a53.screens

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

@Composable
fun HomeScreen(
    disableBack: Boolean,
    onBackHandled: () -> Unit,
    onEnterCategories: () -> Unit,
) {
    // If we've returned Home via a full cycle and want to disable back, consume it
    BackHandler(enabled = disableBack) {
        onBackHandled()
    }

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(text = "Home (Intro)")
        Button(onClick = onEnterCategories) {
            Text(text = "Browse Categories")
        }
    }
}


