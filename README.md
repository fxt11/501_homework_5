## 🧠 AI Usage Documentation

### Where AI Was Used
AI assisted in planning and implementing several core parts of the application:

- Sealed route pattern and BottomNavigation setup  
- ViewModel-based state hoisting for Notes and Tasks screens  
- Navigation stack control using:
  - `popUpTo`
  - `launchSingleTop`
  - `restoreState`
- Argument-driven animations for screen transitions  

---

### Misunderstandings and Corrections

> **AI misunderstanding acknowledged**  
> Jetpack Compose’s `NavHost` does **not** provide built-in screen transition animations.

To satisfy the assignment requirement **without adding extra dependencies**, the implementation instead applies a **screen-level fade animation** that is **triggered by a navigation argument**.

---
