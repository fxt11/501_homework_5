# 5_2 Compose Navigation Homework

## What was built
- Three screens using Jetpack Compose:
  - Notes: add freeform text notes; list persists via ViewModel
  - Tasks: add items with checkboxes; toggling persists via ViewModel
  - Calendar: static placeholder text (no dynamic state)
- Bottom navigation using Material 3 `NavigationBar` and `NavigationBarItem`
- Sealed route objects for type-safe destinations
- Navigation managed with `NavHost` and `NavController`
- State retained across recompositions using `ViewModel`s
- Icons added via `Icons.Default.*`
- Simple fade animations driven by a navigation argument

## Where to look in code
- `app/src/main/java/com/example/a5_2/MainActivity.kt`
  - Sealed `Screen` with routes, labels, and icons
  - Bottom bar using `NavigationBar`
  - `NavHost` with three destinations
  - `NotesViewModel`, `TasksViewModel`
  - `NotesScreen`, `TasksScreen`, `CalendarScreen`

## Navigation details
- Sealed routes include an optional argument: `?anim={anim}`
  - When switching tabs, we pass `anim=slide` (or `none` if reselecting)
  - Screens read the `anim` argument and apply a simple fade via `AnimatedVisibility`
- Bottom bar navigation options used:
  - `popUpTo(navController.graph.findStartDestination()) { saveState = true }`
  - `launchSingleTop = true`
  - `restoreState = true`
- Dynamic highlighting uses `currentBackStackEntryAsState()` to set the selected item

## Backstack and back button behavior
- Switching tabs keeps each tab’s back stack and state (notes/tasks remain as-entered)
- Reselection of the current tab uses `launchSingleTop`, avoiding duplicate destinations
- `restoreState=true` brings back the last scrolled/entered state of a tab
- Back button behavior to test:
  1. Start on Notes → press Back: app exits (Notes is start destination)
  2. Navigate Notes → Tasks → Calendar via bottom bar, then press Back: returns to Tasks, then Notes, then exits
  3. Enter several notes/tasks, switch tabs, come back: state is preserved

## Icons
- Notes: `Icons.Default.Description`
- Tasks: `Icons.Default.List`
- Calendar: `Icons.Default.DateRange`

## Animation using navigation arguments
- A simple fade-in/out animation is triggered by the `anim` argument each time a destination is navigated to
- This demonstrates how arguments can influence UI transitions without additional libraries

## Where AI was used and misunderstandings
- AI assisted in planning and implementing:
  - Sealed route pattern and bottom bar setup
  - ViewModel-based state hoisting for Notes and Tasks
  - Navigation options (`popUpTo`, `launchSingleTop`, `restoreState`)
  - Argument-driven animations
- Misunderstanding acknowledged: Jetpack Compose’s `NavHost` does not provide built-in transitions without the accompanist navigation-animation library. The implementation instead uses a screen-level fade animation triggered by a nav argument to satisfy the requirement without extra dependencies.

## How to run
1. Open the project in Android Studio (latest Arctic Fox+)
2. Build and run on an emulator or device (minSdk 24)
