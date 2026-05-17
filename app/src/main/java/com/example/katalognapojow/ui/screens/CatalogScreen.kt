package com.example.katalognapojow.ui.screens

import android.content.res.Configuration
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.katalognapojow.Screen
import com.example.katalognapojow.ui.theme.Orange

@Composable
fun CatalogScreen(navController: NavController) {
    val configuration = LocalConfiguration.current
    val isLandscape = configuration.orientation == Configuration.ORIENTATION_LANDSCAPE

    if (isLandscape) {
        // --- TRYB LANDSCAPE ---
        // W poziomie zawartość może wymagać przewijania na niskich ekranach,
        // dlatego tutaj zostawiamy verticalScroll, a elementy układamy w Row.
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 8.dp, start = 24.dp, end = 24.dp)
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                CategoryButton(
                    text = "Napoje gazowane",
                    modifier = Modifier.weight(1f).height(140.dp),
                    onClick = { navController.navigate(Screen.SparklingDrinks.route) }
                )
                CategoryButton(
                    text = "Napoje niegazowane",
                    modifier = Modifier.weight(1f).height(140.dp),
                    onClick = { navController.navigate(Screen.StillDrinks.route) }
                )
                CategoryButton(
                    text = "Napoje gorące",
                    modifier = Modifier.weight(1f).height(140.dp),
                    onClick = { navController.navigate(Screen.HotDrinks.route) }
                )
            }
        }
    } else {
        // --- TRYB PORTRAIT ---
        // Usunęliśmy stąd verticalScroll. Kolumna zajmuje dokładnie 100% wysokości ekranu,
        // a Arrangement.Center idealnie i równomiernie wyśrodkuje cały blok przycisków.
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top // To teraz zadziała perfekcyjnie
        ) {
            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(20.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                CategoryButton(
                    text = "Napoje gazowane",
                    modifier = Modifier.height(100.dp),
                    onClick = { navController.navigate(Screen.SparklingDrinks.route) }
                )
                CategoryButton(
                    text = "Napoje niegazowane",
                    modifier = Modifier.height(100.dp),
                    onClick = { navController.navigate(Screen.StillDrinks.route) }
                )
                CategoryButton(
                    text = "Napoje gorące",
                    modifier = Modifier.height(100.dp),
                    onClick = { navController.navigate(Screen.HotDrinks.route) }
                )
            }
        }
    }
}

@Composable
fun CategoryButton(
    text: String,
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {}
) {
    Button(
        onClick = onClick,
        modifier = modifier.fillMaxWidth(),
        colors = ButtonDefaults.buttonColors(
            containerColor = Orange,
            contentColor = Color.Black
        ),
        shape = RoundedCornerShape(40.dp)
    ) {
        Text(text = text, fontSize = 18.sp, textAlign = TextAlign.Center)
    }
}