package com.example.katalognapojow.ui.screens

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import com.example.katalognapojow.R

@Composable
fun HotDrinksScreen(navController: NavController) {
    val products = listOf(
        "Herbata miętowa" to R.drawable.herbata_mietowa,
        "Gorąca czekolada" to R.drawable.czekolada
    )

    BaseProductScreen(
        title = "Napoje gorące",
        products = products,
        navController = navController
    )
}