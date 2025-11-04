package com.example.a5_1.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.a5_1.model.Recipe
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class RecipeViewModel : ViewModel() {
    private val _recipes = MutableStateFlow<List<Recipe>>(emptyList())
    val recipes: StateFlow<List<Recipe>> = _recipes.asStateFlow()

    init {
        // Add some sample recipes for testing
        _recipes.value = listOf(
            Recipe(
                id = "1",
                title = "Chocolate Cake",
                ingredients = listOf(
                    "2 cups flour",
                    "1 cup sugar",
                    "1/2 cup cocoa powder",
                    "2 eggs",
                    "1 cup milk"
                ),
                steps = listOf(
                    "Preheat oven to 350°F",
                    "Mix dry ingredients",
                    "Add wet ingredients",
                    "Bake for 30 minutes"
                )
            ),
            Recipe(
                id = "2",
                title = "Pasta Carbonara",
                ingredients = listOf(
                    "400g spaghetti",
                    "200g bacon",
                    "4 eggs",
                    "100g parmesan cheese",
                    "Black pepper"
                ),
                steps = listOf(
                    "Cook pasta according to package directions",
                    "Fry bacon until crispy",
                    "Mix eggs and cheese",
                    "Combine pasta with bacon and egg mixture",
                    "Season with black pepper"
                )
            )
        )
    }

    fun addRecipe(recipe: Recipe) {
        viewModelScope.launch {
            _recipes.value = _recipes.value + recipe
        }
    }

    fun getRecipeById(id: String): Recipe? {
        return _recipes.value.find { it.id == id }
    }
}

