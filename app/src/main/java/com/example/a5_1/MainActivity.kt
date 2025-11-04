package com.example.a5_1

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.a5_1.navigation.Routes
import com.example.a5_1.ui.screens.AddRecipeScreen
import com.example.a5_1.ui.screens.DetailScreen
import com.example.a5_1.ui.screens.HomeScreen
import com.example.a5_1.ui.screens.SettingsScreen
import com.example.a5_1.ui.theme._5_1Theme
import com.example.a5_1.viewmodel.RecipeViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            _5_1Theme {
                RecipeApp()
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RecipeApp() {
    val navController = rememberNavController()
    val viewModel: RecipeViewModel = viewModel()
    
    Scaffold(
        bottomBar = {
            BottomNavigationBar(navController = navController)
        }
    ) { paddingValues ->
        NavHost(
            navController = navController,
            startDestination = Routes.Home.route,
            modifier = Modifier.padding(paddingValues)
        ) {
            composable(Routes.Home.route) { backStackEntry ->
                HomeScreen(
                    navController = navController,
                    viewModel = viewModel
                )
            }
            
            composable(
                route = Routes.Detail.route,
                arguments = listOf(
                    androidx.navigation.navArgument("id") {
                        type = androidx.navigation.NavType.StringType
                    }
                )
            ) { backStackEntry ->
                DetailScreen(
                    backStackEntry = backStackEntry,
                    navController = navController,
                    viewModel = viewModel
                )
            }
            
            composable(Routes.AddRecipe.route) { backStackEntry ->
                AddRecipeScreen(
                    navController = navController,
                    viewModel = viewModel
                )
            }
            
            composable(Routes.Settings.route) { backStackEntry ->
                SettingsScreen(viewModel = viewModel)
            }
        }
    }
}

@Composable
fun BottomNavigationBar(navController: NavHostController) {
    val items = listOf(
        BottomNavItem("Home", Routes.Home.route, Icons.Default.Home),
        BottomNavItem("Add", Routes.AddRecipe.route, Icons.Default.Add),
        BottomNavItem("Settings", Routes.Settings.route, Icons.Default.Settings)
    )
    
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route
    
    NavigationBar {
        items.forEach { item ->
            NavigationBarItem(
                icon = { Icon(item.icon, contentDescription = item.label) },
                label = { Text(item.label) },
                selected = currentRoute == item.route,
                onClick = {
                    navController.navigate(item.route) {
                        // Pop up to the start destination of the graph to
                        // avoid building up a large stack of destinations
                        // on the back stack as users select items
                        popUpTo(navController.graph.startDestinationId) {
                            saveState = true
                        }
                        // Avoid multiple copies of the same destination when
                        // reselecting the same item
                        launchSingleTop = true
                        // Restore state when reselecting a previously selected item
                        restoreState = true
                    }
                }
            )
        }
    }
}

data class BottomNavItem(
    val label: String,
    val route: String,
    val icon: androidx.compose.ui.graphics.vector.ImageVector
)
