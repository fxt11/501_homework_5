# AI Usage Documentation

This app was implemented with assistance from an AI coding assistant (Cursor + GPT-5).

What AI did:
- Added Navigation-Compose dependency and set up a dedicated `NavGraph` (`app/src/main/java/com/example/a53/NavGraph.kt`).
- Implemented four screens: `HomeScreen`, `CategoriesScreen`, `ListScreen`, and `DetailScreen` (under `app/src/main/java/com/example/a53/screens/`).
- Passed both String and Int arguments through routes:
  - List route: `list/{category}` (String)
  - Detail route: `detail/{category}/{locationId}` (String, Int)
- Demonstrated `NavController.navigate()` with structured route strings (e.g., `Routes.detail(category, locationId)`).
- Implemented `popUpTo(Routes.HOME) { inclusive = true }` to clear the stack when navigating Home.
- Integrated a reusable `AppTopBar` for all screens.
- Used `rememberNavController()` in `MainActivity` and kept navigation logic inside `NavGraph`.
- Disabled the back button after a full navigation cycle back to Home using a flag controlled in `MainActivity` and a `BackHandler` in `HomeScreen`.

Where AI misunderstood navigation:
- Initially, AI considered relying solely on the empty back stack at Home to imply disabled back behavior. The requirement explicitly asked to "disable back button" after a full cycle, which required adding an explicit `BackHandler(enabled = true)` in `HomeScreen` plus a state flag toggled when calling `navigate(Home) { popUpTo(Home) { inclusive = true } }`.
- AI also first placed `rememberNavController()` inside the NavHost itself; we corrected by lifting it to `MainActivity` to keep navigation ownership at the activity level and allow the top app bar to react to route changes consistently.

How to find the code:
- Routes and NavGraph: `app/src/main/java/com/example/a53/NavGraph.kt`
- Screens: `app/src/main/java/com/example/a53/screens/`
- Top bar: `app/src/main/java/com/example/a53/ui/AppBars.kt`
- `rememberNavController()` and scaffolding: `app/src/main/java/com/example/a53/MainActivity.kt`


