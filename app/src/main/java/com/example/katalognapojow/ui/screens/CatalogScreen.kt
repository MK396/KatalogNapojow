package com.example.katalognapojow.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.katalognapojow.Screen
import com.example.katalognapojow.ui.theme.Orange

@Composable
fun CatalogScreen(navController: NavController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(20.dp)
    ) {
        Spacer(modifier = Modifier.height(20.dp))

        // Przypisanie nawigacji do przycisków
        CategoryButton(
            text = "Napoje gazowane",
            onClick = { navController.navigate(Screen.SparklingDrinks.route) }
        )

        CategoryButton(
            text = "Napoje niegazowane",
            onClick = { navController.navigate(Screen.StillDrinks.route) }
        )

        CategoryButton(
            text = "Napoje gorące",
            onClick = { navController.navigate(Screen.HotDrinks.route) }
        )
    }
}

@Composable
fun CategoryButton(text: String, onClick: () -> Unit = {}) {
    Button(
        onClick = onClick,
        modifier = Modifier
            .fillMaxWidth()
            .height(100.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = Orange,
            contentColor = Color.Black
        ),
        shape = RoundedCornerShape(40.dp)
    ) {
        Text(text = text, fontSize = 20.sp)
    }
}