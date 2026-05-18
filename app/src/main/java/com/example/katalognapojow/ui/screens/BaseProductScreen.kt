package com.example.katalognapojow.ui.screens

import android.content.res.Configuration
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.katalognapojow.ui.theme.LocalDimens

@Composable
fun BaseProductScreen(
    title: String,
    products: List<Pair<String, Int>>,
    navController: NavController
) {
    val dimens = LocalDimens.current
    val configuration = LocalConfiguration.current
    val isLandscape = configuration.orientation == Configuration.ORIENTATION_LANDSCAPE

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = if (isLandscape) 0.dp else dimens.screenPadding),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        Text(
            text = title,
            fontSize = dimens.titleFontSize,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(vertical = dimens.cardPadding)
        )

        if (isLandscape) {
            LazyVerticalGrid(
                columns = GridCells.Fixed(3),
                modifier = Modifier
                    .fillMaxHeight(1f)
                    .fillMaxWidth(0.95f),
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp),
            ) {
                products.forEach { (name, imageRes) ->
                    item { DrinkCard(name, imageRes) }
                }
            }
        } else {
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(16.dp),
                contentPadding = PaddingValues(bottom = 32.dp)
            ) {
                products.forEach { (name, imageRes) ->
                    item { DrinkCard(name, imageRes) }
                }
            }
        }
    }
}