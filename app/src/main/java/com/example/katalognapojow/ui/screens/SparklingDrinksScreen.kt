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
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.navigation.NavController
import com.example.katalognapojow.R

@Composable
fun SparklingDrinksScreen(navController: NavController) {
    val products = listOf(
        "Coca Cola" to R.drawable.cola,
        "Fanta pomarańczowa" to R.drawable.fanta
    )
    
    BaseProductScreen(
        title = "Napoje gazowane",
        products = products,
        navController = navController
    )
}

@Composable
fun DrinkCard(name: String, imageRes: Int) {
    var showFullScreen by remember { mutableStateOf(false) }
    val cardShape = RoundedCornerShape(40.dp)
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
                        modifier = Modifier
                            .align(Alignment.TopEnd)
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
                .padding(if (isLandscape) 10.dp else 15.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = name,
                fontSize = if (isLandscape) 18.sp else 24.sp,
                fontWeight = FontWeight.Medium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            Spacer(modifier = Modifier.height(if (isLandscape) 8.dp else 12.dp))
            Image(
                painter = painterResource(id = imageRes),
                contentDescription = name,
                modifier = Modifier
                    .then(if (isLandscape) Modifier.height(120.dp) else Modifier.fillMaxWidth())
                    .fillMaxWidth()
                    .clip(cardShape)
                    .clickable { showFullScreen = true },
                contentScale = if (isLandscape) ContentScale.Crop else ContentScale.Fit
            )
        }
    }
}