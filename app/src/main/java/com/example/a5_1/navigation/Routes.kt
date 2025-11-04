package com.example.a5_1.navigation

sealed class Routes(val route: String) {
    object Home : Routes("home")
    object AddRecipe : Routes("add_recipe")
    object Settings : Routes("settings")
    object Detail : Routes("detail/{id}") {
        fun createRoute(id: String) = "detail/$id"
    }
}

