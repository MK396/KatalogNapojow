package com.example.katalognapojow.ui.screens

import android.content.res.Configuration
import androidx.compose.animation.animateColor
import androidx.compose.animation.core.*
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.katalognapojow.R
import com.example.katalognapojow.Screen

@Composable
fun HomeScreen(navController: NavController) {
    val products = listOf(
        Triple(R.drawable.cola, "Coca Cola", Screen.SparklingDrinks.route),
        Triple(R.drawable.sok, "Sok jabłkowy", Screen.StillDrinks.route),
        Triple(R.drawable.czekolada, "Gorąca czekolada", Screen.HotDrinks.route)
    )

    val configuration = LocalConfiguration.current
    val isLandscape = configuration.orientation == Configuration.ORIENTATION_LANDSCAPE

    // Stan karuzeli
    val pagerState = rememberPagerState(pageCount = { products.size })

    // Definiujemy przejście nieskończone (infinite transition)
    val infiniteTransition = rememberInfiniteTransition(label = "HomeButtonAnim")

    // Zmiana rozmiaru przycisku
    val scale by infiniteTransition.animateFloat(
        initialValue = 1f,
        targetValue = 1.1f,
        animationSpec = infiniteRepeatable(
            animation = tween(1000, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
    )

    // Zmiana koloru przycisku
    val animatedColor by infiniteTransition.animateColor(
        initialValue = Color(0xFFF57C00),
        targetValue = Color(0xFFF5B576),
        animationSpec = infiniteRepeatable(
            animation = tween(1000, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse
        ),
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
    ) {
        if (isLandscape) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Lewa strona: Logo i teksty
                Column(
                    modifier = Modifier
                        .weight(1f)
                        .padding(end = 16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.logo),
                        contentDescription = null,
                        modifier = Modifier.size(120.dp)
                    )
                    Text(
                        text = "Wybierz idealny napój",
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Center
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    Button(
                        onClick = { navController.navigate(Screen.Catalog.route) },
                        modifier = Modifier
                            .fillMaxWidth(0.8f)
                            .height(50.dp)
                            .graphicsLayer(scaleX = scale, scaleY = scale),
                        colors = ButtonDefaults.buttonColors(containerColor = animatedColor)
                    ) {
                        Text("Katalog")
                    }
                }

                // Prawa strona: Karuzela
                Column(
                    modifier = Modifier.weight(1.2f),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "Przykładowe produkty",
                        style = MaterialTheme.typography.titleMedium,
                        modifier = Modifier.padding(bottom = 8.dp)
                    )
                    HorizontalPager(
                        state = pagerState,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(180.dp)
                            .clip(RoundedCornerShape(16.dp)),
                        contentPadding = PaddingValues(horizontal = 24.dp),
                        pageSpacing = 12.dp
                    ) { page ->
                        ProductCarouselCard(products[page], navController)
                    }
                }
            }
        } else {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 16.dp),
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
                    text = "W katalogu znajdziesz 12 produktów podzielonych na 3 kategorie: gazowane, niegazowane oraz napoje gorące.",
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(vertical = 16.dp)
                )

                Spacer(modifier = Modifier.height(30.dp))

                // Przycisk
                Button(
                    onClick = { navController.navigate(Screen.Catalog.route) },
                    modifier = Modifier
                        .fillMaxWidth(0.6f)
                        .height(60.dp)
                        .graphicsLayer(scaleX = scale, scaleY = scale),
                    colors = ButtonDefaults.buttonColors(containerColor = animatedColor)
                ) {
                    Text("Katalog")
                }

                Spacer(modifier = Modifier.height(40.dp))

                // Karuzela
                Text(
                    text = "Przykładowe produkty",
                    modifier = Modifier
                        .align(Alignment.Start)
                        .padding(bottom = 16.dp)
                )

                HorizontalPager(
                    state = pagerState,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(250.dp)
                        .clip(RoundedCornerShape(16.dp)),
                    contentPadding = PaddingValues(horizontal = 32.dp),
                    pageSpacing = 16.dp
                ) { page ->
                    ProductCarouselCard(products[page], navController)
                }
                Spacer(modifier = Modifier.height(32.dp))
            }
        }
    }
}

@Composable
fun ProductCarouselCard(
    product: Triple<Int, String, String>,
    navController: NavController
) {
    Card(
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
        shape = RoundedCornerShape(16.dp),
        modifier = Modifier
            .fillMaxSize()
            .clickable { navController.navigate(product.third) }
    ) {
        Box(modifier = Modifier.fillMaxSize()) {
            Image(
                painter = painterResource(id = product.first),
                contentDescription = "Zdjęcie produktu: ${product.second}",
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop
            )
            // Opcjonalnie: Nakładka z nazwą produktu na karuzeli
            Surface(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .fillMaxWidth(),
                color = Color.Black.copy(alpha = 0.5f)
            ) {
                Text(
                    text = product.second,
                    color = Color.White,
                    modifier = Modifier.padding(8.dp),
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        }
    }
}