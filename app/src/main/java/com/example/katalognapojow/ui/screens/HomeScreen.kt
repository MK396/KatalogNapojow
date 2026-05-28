package com.example.katalognapojow.ui.screens

import android.content.res.Configuration
import android.net.Uri
import androidx.annotation.OptIn
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.media3.common.MediaItem
import androidx.media3.common.Player
import androidx.media3.common.util.UnstableApi
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.ui.PlayerView
import androidx.navigation.NavController
import com.example.katalognapojow.R
import com.example.katalognapojow.Screen
import com.example.katalognapojow.ui.theme.LocalDimens

// 1. KROK: Klasa reprezentująca elementy karuzeli (zdjęcie lub wideo)
sealed class CarouselItem(val title: String, val route: String) {
    class ImageItem(val imageRes: Int, title: String, route: String) : CarouselItem(title, route)
    class VideoItem(val videoRawRes: Int, title: String, route: String) : CarouselItem(title, route)
}

// 2. KROK: Komponent odtwarzacza reagujący na parametr isPlaying
@OptIn(UnstableApi::class)
@Composable
fun VideoPlayer(
    videoResId: Int,
    isPlaying: Boolean,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current

    val exoPlayer = remember {
        ExoPlayer.Builder(context).build().apply {
            val videoUri = Uri.parse("android.resource://${context.packageName}/$videoResId")
            setMediaItem(MediaItem.fromUri(videoUri))
            repeatMode = Player.REPEAT_MODE_ALL
            prepare()
        }
    }

    // Dynamiczne sterowanie odtwarzaniem i głośnością
    LaunchedEffect(isPlaying) {
        if (isPlaying) {
            exoPlayer.volume = 1f         // Włącz dźwięk, gdy karta jest aktywna
            exoPlayer.playWhenReady = true // Uruchom wideo
        } else {
            exoPlayer.playWhenReady = false // Zatrzymaj wideo, gdy użytkownik przewinie dalej
            exoPlayer.volume = 0f          // Wycisz na wszelki wypadek
        }
    }

    DisposableEffect(Unit) {
        onDispose {
            exoPlayer.release()
        }
    }

    AndroidView(
        factory = { ctx ->
            PlayerView(ctx).apply {
                player = exoPlayer
                useController = false
                resizeMode = androidx.media3.ui.AspectRatioFrameLayout.RESIZE_MODE_ZOOM
            }
        },
        modifier = modifier
    )
}

@Composable
fun HomeScreen(navController: NavController) {
    val dimens = LocalDimens.current

    // 3. KROK: Zmiana listy produktów na obiekty CarouselItem
    val products = listOf(
        CarouselItem.ImageItem(R.drawable.sok, "Sok", Screen.StillDrinks.route),
        // Podmień R.raw.sok_video na dokładną nazwę swojego pliku w res/raw (bez rozszerzenia .mp4)
        CarouselItem.VideoItem(R.raw.cola_video, "Coca-Cola", Screen.SparklingDrinks.route),
        CarouselItem.ImageItem(R.drawable.czekolada, "Gorąca czekolada", Screen.HotDrinks.route)
    )

    val configuration = LocalConfiguration.current
    val isLandscape = configuration.orientation == Configuration.ORIENTATION_LANDSCAPE
    val pagerState = rememberPagerState(pageCount = { products.size })

    val infiniteTransition = rememberInfiniteTransition(label = "HomeButtonAnim")

    val scale by infiniteTransition.animateFloat(
        initialValue = 1f,
        targetValue = 1.05f,
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
                            .fillMaxWidth(0.7f)
                            .height(dimens.buttonHeight)
                            .graphicsLayer(scaleX = scale, scaleY = scale),
                        colors = ButtonDefaults.buttonColors(containerColor = animatedColor)
                    ) {
                        Text("Katalog", fontSize = dimens.buttonFontSize)
                    }
                }

                Column(
                    modifier = Modifier.weight(1.2f),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = "Przykładowe produkty",
                        fontSize = dimens.cardFontSize,
                        modifier = Modifier.padding(bottom = 12.dp)
                    )
                    HorizontalPager(
                        state = pagerState,
                        modifier = Modifier
                            .fillMaxWidth(0.65f)
                            .height(dimens.carouselHeight)
                            .clip(RoundedCornerShape(16.dp)),
                        contentPadding = PaddingValues(horizontal = 16.dp),
                        pageSpacing = 12.dp
                    ) { page ->
                        // 4. KROK: Sprawdzenie, czy strona jest aktualnie zaznaczona (Landscape)
                        val isCurrentPage = pagerState.currentPage == page
                        ProductCarouselCard(
                            item = products[page],
                            navController = navController,
                            isCurrentPage = isCurrentPage
                        )
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
                        .height(dimens.carouselHeight)
                        .clip(RoundedCornerShape(16.dp)),
                    contentPadding = PaddingValues(horizontal = 32.dp),
                    pageSpacing = 16.dp
                ) { page ->
                    // 5. KROK: Sprawdzenie, czy strona jest aktualnie zaznaczona (Portrait)
                    val isCurrentPage = pagerState.currentPage == page
                    ProductCarouselCard(
                        item = products[page],
                        navController = navController,
                        isCurrentPage = isCurrentPage
                    )
                }
                Spacer(modifier = Modifier.height(32.dp))
            }
        }
    }
}

@Composable
fun ProductCarouselCard(
    item: CarouselItem, // 6. KROK: Zmiana typu z Triple na CarouselItem
    navController: NavController,
    isCurrentPage: Boolean // 7. KROK: Dodatkowy parametr sprawdzający widoczność
) {
    val dimens = LocalDimens.current

    Card(
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
        shape = RoundedCornerShape(16.dp),
        modifier = Modifier
            .fillMaxSize()
            .clickable { navController.navigate(item.route) }
    ) {
        Box(modifier = Modifier.fillMaxSize()) {
            // 8. KROK: Warunkowe renderowanie obrazka lub odtwarzacza wideo
            when (item) {
                is CarouselItem.ImageItem -> {
                    Image(
                        painter = painterResource(id = item.imageRes),
                        contentDescription = "Zdjęcie produktu: ${item.title}",
                        modifier = Modifier.fillMaxSize(),
                        contentScale = ContentScale.Crop
                    )
                }
                is CarouselItem.VideoItem -> {
                    VideoPlayer(
                        videoResId = item.videoRawRes,
                        isPlaying = isCurrentPage,
                        modifier = Modifier.fillMaxSize()
                    )
                }
            }

            Surface(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .fillMaxWidth(),
                color = Color.Black.copy(alpha = 0.5f)
            ) {
                Text(
                    text = item.title,
                    color = Color.White,
                    fontSize = dimens.bodyFontSize,
                    modifier = Modifier.padding(8.dp),
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}