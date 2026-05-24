package com.example.katalognapojow.ui.screens

import android.content.res.Configuration
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.navigation.NavController
import com.example.katalognapojow.R
import com.example.katalognapojow.ui.theme.LocalDimens

@Composable
fun SparklingDrinksScreen(navController: NavController) {
    val products = listOf(
        "Coca Cola" to R.drawable.cola,
        "Fanta" to R.drawable.fanta,
        "Dzik cytrynowy" to R.drawable.dzik,
        "Sprite" to R.drawable.sprite
    )

    BaseProductScreen(
        products = products,
        navController = navController
    )
}

@Composable
fun DrinkCard(name: String, imageRes: Int) {
    val dimens = LocalDimens.current
    var showFullScreen by remember { mutableStateOf(false) }
    val cardShape = RoundedCornerShape(40.dp)
    val imageInsideShape = RoundedCornerShape(24.dp)
    val configuration = LocalConfiguration.current
    val isLandscape = configuration.orientation == Configuration.ORIENTATION_LANDSCAPE

    if (showFullScreen) {
        Dialog(
            onDismissRequest = { showFullScreen = false },
            properties = DialogProperties(usePlatformDefaultWidth = false)
        ) {
            Surface(
                modifier = Modifier.fillMaxSize(),
                color = Color.Black.copy(alpha = 0.7f)
            ) {
                Box(modifier = Modifier.fillMaxSize()) {
                    Image(
                        painter = painterResource(id = imageRes),
                        contentDescription = "Powiększone zdjęcie $name",
                        modifier = Modifier
                            .align(Alignment.Center)
                            .fillMaxWidth()
                    )
                    IconButton(
                        onClick = { showFullScreen = false },
                        modifier = Modifier.align(Alignment.TopEnd)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Close,
                            contentDescription = "Zamknij",
                            tint = Color.White,
                            modifier = Modifier.size(40.dp)
                        )
                    }
                }
            }
        }
    }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .border(1.dp, MaterialTheme.colorScheme.outline, cardShape),
        shape = cardShape,
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = dimens.cardPadding, start = dimens.cardPadding, end = dimens.cardPadding),
            // Usunąłem padding od dołu z głównego kontenera, żeby precyzyjnie kontrolować go Spacerem
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = name,
                fontSize = dimens.cardFontSize,
                fontWeight = FontWeight.Medium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )

            Spacer(modifier = Modifier.height(dimens.cardPadding))

            Image(
                painter = painterResource(id = imageRes),
                contentDescription = name,
                modifier = Modifier
                    .then(
                        if (isLandscape) Modifier.height(dimens.imageHeight)
                        // Teraz automatycznie pobierze 240.dp na telefonie (wyższa karta, szerszy produkt)
                        else Modifier.fillMaxWidth().height(dimens.imageHeight)
                    )
                    .clip(imageInsideShape)
                    .clickable { showFullScreen = true },
                contentScale = ContentScale.Crop
            )

            // ZMIANA: Dodatkowy odstęp na dole, żeby karta dobrze wyglądała przy większej wysokości
            Spacer(modifier = Modifier.height(dimens.cardPadding))
        }
    }
}