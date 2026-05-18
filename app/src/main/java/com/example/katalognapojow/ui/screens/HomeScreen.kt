package com.example.katalognapojow.ui.screens

import android.content.res.Configuration
import androidx.compose.animation.animateColor
import androidx.compose.animation.core.*
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Arrangement
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
import com.example.katalognapojow.ui.theme.LocalDimens

@Composable
fun HomeScreen(navController: NavController) {
    val dimens = LocalDimens.current
    val products = listOf(
        Triple(R.drawable.cola, "Coca Cola", Screen.SparklingDrinks.route),
        Triple(R.drawable.sok, "Sok jabłkowy", Screen.StillDrinks.route),
        Triple(R.drawable.czekolada, "Gorąca czekolada", Screen.HotDrinks.route)
    )

    val configuration = LocalConfiguration.current
    val isLandscape = configuration.orientation == Configuration.ORIENTATION_LANDSCAPE
    val pagerState = rememberPagerState(pageCount = { products.size })

    val infiniteTransition = rememberInfiniteTransition(label = "HomeButtonAnim")

    val scale by infiniteTransition.animateFloat(
        initialValue = 1f,
        targetValue = 1.05f, // Zmniejszyłem do 1.05f, żeby pulsujący przycisk nie nachodził na tekst w landscape
        animationSpec = infiniteRepeatable(
            animation = tween(1000, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
    )

    val animatedColor by infiniteTransition.animateColor(
        initialValue = Color(0xFFF57C00),
        targetValue = Color(0xFFF5B576),
        animationSpec = infiniteRepeatable(
            animation = tween(1000, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse
        ),
    )

    Box(modifier = Modifier.fillMaxSize()) {
        if (isLandscape) {
            // --- TRYB POZIOMY ---
            Row(
                modifier = Modifier.fillMaxSize(),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                // Lewa strona: Logo i teksty
                Column(
                    modifier = Modifier
                        .weight(1.3f)
                        .fillMaxHeight()
                        .verticalScroll(rememberScrollState())
                        .padding(horizontal = dimens.screenPadding),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.logo),
                        contentDescription = null,
                        modifier = Modifier.size(dimens.logoSizeLandscape)
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "Wybierz idealny napój",
                        fontSize = dimens.titleFontSize,
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Center
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    Button(
                        onClick = { navController.navigate(Screen.Catalog.route) },
                        modifier = Modifier
                            .fillMaxWidth(0.7f) // Usunięto fillMaxHeight(0.6f) - wysokość kontroluje dimens.buttonHeight
                            .height(dimens.buttonHeight)
                            .graphicsLayer(scaleX = scale, scaleY = scale),
                        colors = ButtonDefaults.buttonColors(containerColor = animatedColor)
                    ) {
                        Text("Katalog", fontSize = dimens.buttonFontSize)
                    }
                }

                // Prawa strona: Karuzela
                Column(
                    modifier = Modifier.weight(1.2f),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = "Przykładowe produkty",
                        fontSize = dimens.cardFontSize, // Zmieniono na cardFontSize, by tekst był czytelniejszy na tablecie
                        modifier = Modifier.padding(bottom = 12.dp)
                    )
                    HorizontalPager(
                        state = pagerState,
                        modifier = Modifier
                            .fillMaxWidth(0.65f) // Zmniejszona szerokość z fillMaxSize(), aby zbliżyć proporcje karty do pionowego prostokąta
                            .height(dimens.carouselHeight) // Zmieniono z imageHeight na poprawne carouselHeight
                            .clip(RoundedCornerShape(16.dp)),
                        contentPadding = PaddingValues(horizontal = 16.dp),
                        pageSpacing = 12.dp
                    ) { page ->
                        ProductCarouselCard(products[page], navController)
                    }
                }
            }
        } else {
            // --- TRYB PIONOWY ---
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
                    .padding(horizontal = dimens.screenPadding),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Spacer(modifier = Modifier.height(24.dp))

                Image(
                    painter = painterResource(id = R.drawable.logo),
                    contentDescription = null,
                    modifier = Modifier.size(dimens.logoSize)
                )

                Spacer(modifier = Modifier.height(30.dp))

                Text(
                    text = "Wybierz idealny napój dla siebie",
                    fontSize = dimens.titleFontSize,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center
                )

                Text(
                    text = "W katalogu znajdziesz 12 produktów podzielonych na 3 kategorie: gazowane, niegazowane oraz napoje gorące.",
                    fontSize = dimens.bodyFontSize,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(vertical = 16.dp)
                )

                Spacer(modifier = Modifier.height(24.dp))

                Button(
                    onClick = { navController.navigate(Screen.Catalog.route) },
                    modifier = Modifier
                        .fillMaxWidth(0.6f)
                        .height(dimens.buttonHeight)
                        .graphicsLayer(scaleX = scale, scaleY = scale),
                    colors = ButtonDefaults.buttonColors(containerColor = animatedColor)
                ) {
                    Text("Katalog", fontSize = dimens.buttonFontSize)
                }

                Spacer(modifier = Modifier.height(40.dp))

                Text(
                    text = "Przykładowe produkty",
                    fontSize = dimens.cardFontSize,
                    modifier = Modifier
                        .align(Alignment.Start)
                        .padding(bottom = 16.dp)
                )

                HorizontalPager(
                    state = pagerState,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(dimens.carouselHeight) // Konsekwentnie używamy carouselHeight dla głównej karuzeli
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
    val dimens = LocalDimens.current

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
            Surface(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .fillMaxWidth(),
                color = Color.Black.copy(alpha = 0.5f)
            ) {
                Text(
                    text = product.second,
                    color = Color.White,
                    fontSize = dimens.bodyFontSize,
                    modifier = Modifier.padding(8.dp),
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}