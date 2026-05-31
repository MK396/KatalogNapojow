package com.example.katalognapojow.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import androidx.compose.ui.text.googlefonts.GoogleFont
import androidx.compose.ui.text.googlefonts.Font
import com.example.katalognapojow.R

val provider = GoogleFont.Provider(
    providerAuthority = "com.google.android.gms.fonts",
    providerPackage = "com.google.android.gms",
    certificates = R.array.com_google_android_gms_fonts_certs
)
val LatoFontName = GoogleFont("Lato")

val LatoFontFamily = FontFamily(
    Font(googleFont = LatoFontName, fontProvider = provider, weight = FontWeight.Normal),
    Font(googleFont = LatoFontName, fontProvider = provider, weight = FontWeight.Medium),
    Font(googleFont = LatoFontName, fontProvider = provider, weight = FontWeight.Bold)
)

val defaultStyle = TextStyle(
    fontFamily = LatoFontFamily
)

val Typography = Typography(
    displayLarge = defaultStyle.copy(fontSize = 57.sp, lineHeight = 64.sp),
    displayMedium = defaultStyle.copy(fontSize = 45.sp, lineHeight = 52.sp),
    displaySmall = defaultStyle.copy(fontSize = 36.sp, lineHeight = 44.sp),
    headlineLarge = defaultStyle.copy(fontSize = 32.sp, lineHeight = 40.sp),
    headlineMedium = defaultStyle.copy(fontSize = 28.sp, lineHeight = 36.sp),
    headlineSmall = defaultStyle.copy(fontSize = 24.sp, lineHeight = 32.sp),
    titleLarge = defaultStyle.copy(fontSize = 22.sp, lineHeight = 28.sp, fontWeight = FontWeight.Bold),
    titleMedium = defaultStyle.copy(fontSize = 16.sp, lineHeight = 24.sp, fontWeight = FontWeight.Medium),
    titleSmall = defaultStyle.copy(fontSize = 14.sp, lineHeight = 20.sp, fontWeight = FontWeight.Medium),
    bodyLarge = defaultStyle.copy(fontSize = 16.sp, lineHeight = 24.sp),
    bodyMedium = defaultStyle.copy(fontSize = 14.sp, lineHeight = 20.sp),
    bodySmall = defaultStyle.copy(fontSize = 12.sp, lineHeight = 16.sp),
    labelLarge = defaultStyle.copy(fontSize = 14.sp, lineHeight = 20.sp, fontWeight = FontWeight.Medium),
    labelMedium = defaultStyle.copy(fontSize = 12.sp, lineHeight = 16.sp, fontWeight = FontWeight.Medium),
    labelSmall = defaultStyle.copy(fontSize = 11.sp, lineHeight = 16.sp, fontWeight = FontWeight.Medium)
)
