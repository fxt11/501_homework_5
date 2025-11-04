how and where you used AI to build this app. Where did AI misunderstand navigation:
I described features and constraints (like use NavHostController, launchSingleTop, popUpTo()) and 
AI help me generate the sealed Routes class and navigation logic in Jetpack Compose and I reviewed/accepted them.
But the AI suggested using navController.navigate("detail") without appending the argument, which caused a runtime error. I corrected it by using navController.navigate("detail/${id}") and defining composable("detail/{id}") in NavHost.