package com.example.katalognapojow.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.katalognapojow.R
import com.example.katalognapojow.Screen
import androidx.navigation.NavController

@Composable
fun HomeScreen(navController: NavController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Logo
        Image(
            painter = painterResource(id = R.drawable.logo),
            contentDescription = null,
            modifier = Modifier.size(200.dp)
        )

        Spacer(modifier = Modifier.height(30.dp))

        // Nagłówek
        Text(
            text = "Wybierz idealny napój dla siebie",
            style = MaterialTheme.typography.headlineSmall,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center
        )

        // Opis
        Text(
            text = "W naszym katalogu znajdziesz 12 wyjątkowych propozycji podzielonych na sekcje: gazowane, niegazowane oraz napoje gorące.",
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(vertical = 16.dp)
        )

        Spacer(modifier = Modifier.height(30.dp))

        // Przycisk
        Button(
            onClick = { navController.navigate(Screen.Catalog.route) },
            modifier = Modifier.fillMaxWidth(0.4f).height(60.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFF57C00))
        ) {
            Text("Katalog")
        }
    }
}