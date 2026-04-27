package com.example.katalognapojow

sealed class Screen(val route: String) {
    object Home : Screen("home")
    object Catalog : Screen("catalog")
    object About : Screen("about")
    object SparklingDrinks : Screen("sparkling")
    object StillDrinks : Screen("still")
    object HotDrinks : Screen("hot")
}