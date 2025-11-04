package com.example.a53

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.a53.ui.AppTopBar
import com.example.a53.ui.theme._53Theme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            _53Theme {
                val navController = rememberNavController()
                var disableBackOnHome by remember { mutableStateOf(false) }

                val backStackEntry by navController.currentBackStackEntryAsState()
                val route = backStackEntry?.destination?.route ?: ""
                val title = when {
                    route.startsWith(Routes.DETAIL.substringBefore("/{")) -> "Detail"
                    route.startsWith(Routes.LIST.substringBefore("/{")) -> "List"
                    route == Routes.CATEGORIES -> "Categories"
                    else -> "Home"
                }

                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    topBar = { AppTopBar(title = title) }
                ) { innerPadding ->
                    AppNavHost(
                        disableBackOnHome = disableBackOnHome,
                        onHomeConsumed = { disableBackOnHome = false },
                        onStackClearedToHome = { disableBackOnHome = true },
                        navController = navController,
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    _53Theme {
        // Preview placeholder
    }
}