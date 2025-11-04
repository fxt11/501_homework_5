## 🧠 AI Usage Documentation

### How and Where AI Was Used
I described features and constraints (like use NavHostController, launchSingleTop, popUpTo()) and 
AI help me generate the sealed Routes class and navigation logic in Jetpack Compose and I reviewed/accepted them.

After reviewing the generated code, I accepted and integrated these parts into my project.

---

### Where AI Misunderstood Navigation
The AI suggested using navController.navigate("detail") without appending the argument, which caused a runtime error. I corrected it by using navController.navigate("detail/${id}") and defining composable("detail/{id}") in NavHost.
