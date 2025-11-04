## 🧠 AI Usage Documentation

### What AI Did
AI assistance was used to plan, scaffold, and refine the navigation system and related logic.

- Added **Navigation-Compose** dependency and set up `NavGraph`  
  (`app/src/main/java/com/example/a53/NavGraph.kt`).
- Implemented all four screens (`HomeScreen`, `CategoriesScreen`, `ListScreen`, `DetailScreen`) under `app/src/main/java/com/example/a53/screens/`.
- Designed route structures

### Where AI Misunderstood Navigation
- Initially, AI considered relying solely on the empty back stack at Home to imply disabled back behavior.
The requirement explicitly asked to "disable back button" after a full cycle, which required adding an
explicit `BackHandler(enabled = true)` in `HomeScreen` plus a state flag toggled when calling `navigate(Home) { popUpTo(Home) { inclusive = true } }`.
- AI also first placed `rememberNavController()` inside the NavHost itself; we corrected by lifting it to `MainActivity` to keep navigation ownership at the activity level and allow the top app bar to react to route changes consistently.
