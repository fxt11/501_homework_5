package com.example.a5_2

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.padding
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Divider
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Description
import androidx.compose.material.icons.filled.List
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.Alignment
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.a5_2.ui.theme._5_2Theme
import androidx.lifecycle.ViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            _5_2Theme {
                App()
            }
        }
    }
}

private sealed class Screen(
    val route: String,
    val label: String,
    val icon: androidx.compose.ui.graphics.vector.ImageVector
) {
    data object Notes : Screen("notes?anim={anim}", "Notes", Icons.Filled.Description)
    data object Tasks : Screen("tasks?anim={anim}", "Tasks", Icons.Filled.List)
    data object Calendar : Screen("calendar?anim={anim}", "Calendar", Icons.Filled.DateRange)

    companion object {
        val bottomItems = listOf(Notes, Tasks, Calendar)
        fun baseRoute(route: String?): String? = route?.substringBefore("?")
    }
}

@Composable
private fun App() {
    val navController = rememberNavController()
    val backStackEntry by navController.currentBackStackEntryAsState()
    val currentRouteBase = Screen.baseRoute(backStackEntry?.destination?.route)

    Scaffold(
        bottomBar = {
            NavigationBar {
                Screen.Companion.bottomItems.forEach { item ->
                    val isSelected = currentRouteBase == item.route.substringBefore("?")
                    NavigationBarItem(
                        selected = isSelected,
                        onClick = {
                            navController.navigate(item.route.replace("{anim}", if (isSelected) "none" else "slide")) {
                                // popUpTo start destination to keep a single instance of each tab
                                popUpTo(navController.graph.findStartDestination().id) {
                                    saveState = true
                                }
                                launchSingleTop = true
                                restoreState = true
                            }
                        },
                        icon = { Icon(item.icon, contentDescription = item.label) },
                        label = { Text(item.label) }
                    )
                }
            }
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = "notes?anim=none",
            modifier = Modifier.padding(innerPadding)
        ) {
            composable(
                route = Screen.Notes.route,
                arguments = listOf(
                    androidx.navigation.navArgument("anim") {
                        nullable = true
                        defaultValue = "none"
                    }
                )
            ) { entry ->
                val vm: NotesViewModel = viewModel()
                val anim = entry.arguments?.getString("anim") ?: "none"
                NotesScreen(vm = vm, anim = anim)
            }
            composable(
                route = Screen.Tasks.route,
                arguments = listOf(
                    androidx.navigation.navArgument("anim") {
                        nullable = true
                        defaultValue = "none"
                    }
                )
            ) { entry ->
                val vm: TasksViewModel = viewModel()
                val anim = entry.arguments?.getString("anim") ?: "none"
                TasksScreen(vm = vm, anim = anim)
            }
            composable(
                route = Screen.Calendar.route,
                arguments = listOf(
                    androidx.navigation.navArgument("anim") {
                        nullable = true
                        defaultValue = "none"
                    }
                )
            ) { entry ->
                val anim = entry.arguments?.getString("anim") ?: "none"
                CalendarScreen(anim = anim)
            }
        }
    }
}

// Screens and ViewModels

class NotesViewModel : ViewModel() {
    val notes = mutableStateListOf<String>()
}

class TasksViewModel : ViewModel() {
    data class TaskItem(val text: String, val done: Boolean)
    val tasks = mutableStateListOf<TaskItem>()
    fun toggle(index: Int) {
        val item = tasks[index]
        tasks[index] = item.copy(done = !item.done)
    }
}

@Composable
private fun NotesScreen(vm: NotesViewModel, anim: String) {
    var visible by remember { mutableStateOf(false) }
    LaunchedEffect(anim) { visible = true }
    Surface {
        AnimatedVisibility(visible, enter = fadeIn(), exit = fadeOut()) {
            Column(modifier = Modifier.padding(16.dp)) {
                var text by remember { mutableStateOf("") }
                OutlinedTextField(
                    value = text,
                    onValueChange = { text = it },
                    label = { Text("Enter note") },
                    modifier = Modifier.fillMaxWidth(),
                    keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
                    keyboardActions = KeyboardActions(onDone = {
                        if (text.isNotBlank()) {
                            vm.notes.add(text.trim())
                            text = ""
                        }
                    })
                )
                Spacer(Modifier.height(12.dp))
                vm.notes.forEachIndexed { index, n ->
                    Text(text = "${index + 1}. $n")
                    Divider(modifier = Modifier.padding(vertical = 8.dp))
                }
            }
        }
    }
}

@Composable
private fun TasksScreen(vm: TasksViewModel, anim: String) {
    var visible by remember { mutableStateOf(false) }
    LaunchedEffect(anim) { visible = true }
    Surface {
        AnimatedVisibility(visible, enter = fadeIn(), exit = fadeOut()) {
            Column(modifier = Modifier.padding(16.dp)) {
                var text by remember { mutableStateOf("") }
                OutlinedTextField(
                    value = text,
                    onValueChange = { text = it },
                    label = { Text("Add task") },
                    modifier = Modifier.fillMaxWidth(),
                    keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
                    keyboardActions = KeyboardActions(onDone = {
                        if (text.isNotBlank()) {
                            vm.tasks.add(TasksViewModel.TaskItem(text.trim(), false))
                            text = ""
                        }
                    })
                )
                Spacer(Modifier.height(12.dp))
                vm.tasks.forEachIndexed { index, task ->
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Checkbox(checked = task.done, onCheckedChange = { vm.toggle(index) })
                        Text(text = task.text, modifier = Modifier.weight(1f))
                    }
                    Divider(modifier = Modifier.padding(vertical = 8.dp))
                }
            }
        }
    }
}

@Composable
private fun CalendarScreen(anim: String) {
    var visible by remember { mutableStateOf(false) }
    LaunchedEffect(anim) { visible = true }
    Surface {
        AnimatedVisibility(visible, enter = fadeIn(), exit = fadeOut()) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text("Calendar Placeholder")
                Spacer(Modifier.height(8.dp))
                Text("Static content to represent a calendar view.")
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun AppPreview() {
    _5_2Theme { App() }
}