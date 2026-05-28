package com.example.katalognapojow.ui.screens

import android.content.res.Configuration
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.katalognapojow.R

@Composable
fun AboutUsScreen() {
    val configuration = LocalConfiguration.current
    // Sprawdzamy, czy użytkownik trzyma telefon poziomo
    val isLandscape = configuration.orientation == Configuration.ORIENTATION_LANDSCAPE
    val scrollState = rememberScrollState()

    Box(
        modifier = Modifier
            .fillMaxSize()
            // Przewijanie włączamy TYLKO w trybie poziomym
            .then(if (isLandscape) Modifier.verticalScroll(scrollState) else Modifier)
            .padding(16.dp),
        // W poziomie wyrównujemy do góry (łatwiej się czyta od początku), w pionie idealnie na środku
        contentAlignment = if (isLandscape) Alignment.TopCenter else Alignment.Center
    ) {
        Image(
            painter = painterResource(id = R.drawable.plakat_swiatnapojow),
            contentDescription = "Zdjęcie sekcji O nas",
            modifier = Modifier
                .then(
                    if (isLandscape) {
                        // W poziomie wymuszamy określoną szerokość (np. 55% ekranu)
                        Modifier.fillMaxWidth(0.55f)
                    } else {
                        // W pionie kurczymy komponent do fizycznych granic dopasowanego obrazu
                        Modifier.wrapContentSize()
                    }
                )
                .clip(RoundedCornerShape(24.dp)),
            // Dobieramy odpowiednie skalowanie w zależności od orientacji
            contentScale = if (isLandscape) ContentScale.FillWidth else ContentScale.Fit
        )
    }
}