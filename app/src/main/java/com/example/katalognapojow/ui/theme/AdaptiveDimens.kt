package com.example.katalognapojow.ui.theme

import androidx.compose.material3.windowsizeclass.WindowHeightSizeClass
import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

data class AdaptiveDimens(
    val titleFontSize: TextUnit,
    val cardFontSize: TextUnit,
    val buttonFontSize: TextUnit,
    val bodyFontSize: TextUnit,

    val logoSize: Dp,           // logo w portrait
    val logoSizeLandscape: Dp,  // logo w landscape
    val buttonHeight: Dp,
    val cardPadding: Dp,
    val screenPadding: Dp,
    val imageHeight: Dp,
    val carouselHeight: Dp,
    val categoryButtonHeight: Dp,
)

fun adaptiveDimens(windowSizeClass: WindowSizeClass): AdaptiveDimens {
    val isCompactHeight = windowSizeClass.heightSizeClass == WindowHeightSizeClass.Compact
    val widthDp = windowSizeClass.widthSizeClass

    return when {
        // Kompaktowa szerokość (< 600dp) — Telefony pionowo
        widthDp == WindowWidthSizeClass.Compact -> AdaptiveDimens(
            titleFontSize = 22.sp,
            cardFontSize = 18.sp,
            buttonFontSize = 16.sp,
            bodyFontSize = 14.sp,
            logoSize = 160.dp,
            logoSizeLandscape = if (isCompactHeight) 60.dp else 80.dp,
            buttonHeight = if (isCompactHeight) 44.dp else 56.dp,
            cardPadding = 12.dp,
            screenPadding = 16.dp,
            imageHeight = if (isCompactHeight) 120.dp else 160.dp,
            carouselHeight = if (isCompactHeight) 160.dp else 220.dp,
            categoryButtonHeight = if (isCompactHeight) 70.dp else 90.dp,
        )

        // Średnia szerokość (600–840dp) — Tablety pionowo (Twój przypadek z drugiego zdjęcia)
        widthDp == WindowWidthSizeClass.Medium -> AdaptiveDimens(
            titleFontSize = 28.sp,
            cardFontSize = 22.sp,
            buttonFontSize = 18.sp,
            bodyFontSize = 16.sp,
            logoSize = 220.dp,
            logoSizeLandscape = if (isCompactHeight) 100.dp else 160.dp,
            buttonHeight = 64.dp,
            cardPadding = 16.dp,
            screenPadding = 32.dp,
            imageHeight = 200.dp,
            carouselHeight = if (isCompactHeight) 240.dp else 400.dp, // Podniesione z 320.dp na 400.dp
            categoryButtonHeight = if (isCompactHeight) 90.dp else 110.dp,
        )

        // Expanded (840–1200dp) — Tablety poziomo
        widthDp == WindowWidthSizeClass.Expanded -> AdaptiveDimens(
            titleFontSize = 32.sp,
            cardFontSize = 24.sp,
            buttonFontSize = 20.sp,
            bodyFontSize = 18.sp,
            logoSize = 260.dp,
            logoSizeLandscape = if (isCompactHeight) 120.dp else 260.dp,
            buttonHeight = 72.dp,
            cardPadding = 20.dp,
            screenPadding = 40.dp,
            imageHeight = 240.dp,
            carouselHeight = if (isCompactHeight) 300.dp else 480.dp, // Podniesione z 420.dp na 480.dp
            categoryButtonHeight = if (isCompactHeight) 100.dp else 130.dp,
        )

        // Large (1200–1600dp) — Duże tablety poziomo
        widthDp.toString().contains("Large", ignoreCase = true) &&
                !widthDp.toString().contains("Extra", ignoreCase = true) -> AdaptiveDimens(
            titleFontSize = 34.sp,
            cardFontSize = 24.sp,
            buttonFontSize = 22.sp,
            bodyFontSize = 20.sp,
            logoSize = 280.dp,
            logoSizeLandscape = if (isCompactHeight) 140.dp else 280.dp,
            buttonHeight = 80.dp,
            cardPadding = 24.dp,
            screenPadding = 48.dp,
            imageHeight = 280.dp,
            carouselHeight = if (isCompactHeight) 340.dp else 540.dp, // Zwiększone na 540.dp
            categoryButtonHeight = if (isCompactHeight) 110.dp else 150.dp,
        )

        // Extra-large (≥ 1600dp)
        else -> AdaptiveDimens(
            titleFontSize = 38.sp,
            cardFontSize = 26.sp,
            buttonFontSize = 24.sp,
            bodyFontSize = 22.sp,
            logoSize = 320.dp,
            logoSizeLandscape = if (isCompactHeight) 160.dp else 320.dp,
            buttonHeight = 88.dp,
            cardPadding = 28.dp,
            screenPadding = 56.dp,
            imageHeight = 320.dp,
            carouselHeight = if (isCompactHeight) 360.dp else 600.dp,
            categoryButtonHeight = if (isCompactHeight) 120.dp else 170.dp,
        )
    }
}

val LocalDimens = compositionLocalOf {
    AdaptiveDimens(
        titleFontSize = 22.sp, cardFontSize = 18.sp, buttonFontSize = 16.sp,
        bodyFontSize = 14.sp, logoSize = 160.dp, logoSizeLandscape = 80.dp,
        buttonHeight = 56.dp, cardPadding = 12.dp, screenPadding = 16.dp,
        imageHeight = 160.dp, carouselHeight = 220.dp, categoryButtonHeight = 90.dp,
    )
}