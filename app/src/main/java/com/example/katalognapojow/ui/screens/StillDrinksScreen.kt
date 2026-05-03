package com.example.katalognapojow.ui.screens

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import com.example.katalognapojow.R

@Composable
fun StillDrinksScreen(navController: NavController) {
    val products = listOf(
        "Sok jabłkowy" to R.drawable.sok,
        "Oshee jagodowe" to R.drawable.oshee,
        "Skyr wiśniowy" to R.drawable.skyr
    )

    BaseProductScreen(
        title = "Napoje niegazowane",
        products = products,
        navController = navController
    )
}