package com.example.a53

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.navigation.NavHostController
import com.example.a53.screens.CategoriesScreen
import com.example.a53.screens.DetailScreen
import com.example.a53.screens.HomeScreen
import com.example.a53.screens.ListScreen

object Routes {
    const val HOME = "home"
    const val CATEGORIES = "categories"
    const val LIST = "list/{category}" // category: String
    const val DETAIL = "detail/{category}/{locationId}" // category: String, locationId: Int

    fun list(category: String) = "list/$category"
    fun detail(category: String, locationId: Int) = "detail/$category/$locationId"
}

@Composable
fun AppNavHost(
    disableBackOnHome: Boolean,
    onHomeConsumed: () -> Unit,
    onStackClearedToHome: () -> Unit,
    navController: NavHostController,
    modifier: Modifier = Modifier,
) {
    NavHost(navController = navController, startDestination = Routes.HOME, modifier = modifier) {
        addHome(navController, disableBackOnHome, onHomeConsumed)
        addCategories(navController, onStackClearedToHome)
        addList(navController)
        addDetail(navController, onStackClearedToHome)
    }
}

private fun NavGraphBuilder.addHome(
    navController: NavController,
    disableBackOnHome: Boolean,
    onHomeConsumed: () -> Unit,
) {
    composable(Routes.HOME) {
        HomeScreen(
            disableBack = disableBackOnHome,
            onBackHandled = onHomeConsumed,
            onEnterCategories = {
                navController.navigate(Routes.CATEGORIES)
            }
        )
    }
}

private fun NavGraphBuilder.addCategories(navController: NavController, onStackClearedToHome: () -> Unit) {
    composable(Routes.CATEGORIES) {
        CategoriesScreen(
            onCategorySelected = { category ->
                // Structured route string with String argument
                navController.navigate(Routes.list(category))
            },
            onNavigateHomeClearStack = {
                // popUpTo with inclusive = true clears the stack
                navController.navigate(Routes.HOME) {
                    popUpTo(Routes.HOME) { inclusive = true }
                }
                onStackClearedToHome()
            }
        )
    }
}

private fun NavGraphBuilder.addList(navController: NavController) {
    composable(
        route = Routes.LIST,
        arguments = listOf(
            navArgument("category") { type = NavType.StringType }
        )
    ) { backStackEntry ->
        val category = backStackEntry.arguments?.getString("category").orEmpty()
        ListScreen(
            category = category,
            onLocationSelected = { locationId ->
                // Structured route string with String and Int args
                navController.navigate(Routes.detail(category, locationId))
            },
            onNavigateUp = { navController.popBackStack() }
        )
    }
}

private fun NavGraphBuilder.addDetail(navController: NavController, onStackClearedToHome: () -> Unit) {
    composable(
        route = Routes.DETAIL,
        arguments = listOf(
            navArgument("category") { type = NavType.StringType },
            navArgument("locationId") { type = NavType.IntType }
        )
    ) { backStackEntry ->
        val category = backStackEntry.arguments?.getString("category").orEmpty()
        val locationId = backStackEntry.arguments?.getInt("locationId") ?: -1
        DetailScreen(
            category = category,
            locationId = locationId,
            onNavigateUp = { navController.popBackStack() },
            onDoneGoHomeClearStack = {
                // Demonstrate popUpTo inclusive true to clear entire stack
                navController.navigate(Routes.HOME) {
                    popUpTo(Routes.HOME) { inclusive = true }
                }
                onStackClearedToHome()
            }
        )
    }
}


