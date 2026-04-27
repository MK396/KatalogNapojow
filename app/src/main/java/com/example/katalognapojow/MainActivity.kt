package com.example.katalognapojow

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.*
import androidx.navigation.compose.rememberNavController
import com.example.katalognapojow.ui.screens.*
import com.example.katalognapojow.ui.theme.KatalogNapojowTheme
import androidx.compose.runtime.getValue
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.MenuBook
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.NavigationBarItemDefaults
import com.example.katalognapojow.ui.theme.Orange

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            KatalogNapojowTheme {
                val navController = rememberNavController() // Kontroler nawigacji

                Scaffold(
                    bottomBar = { MyBottomBar(navController) }
                ) { innerPadding ->
                    NavHost(
                        navController = navController,
                        startDestination = Screen.Home.route,
                        modifier = Modifier.padding(innerPadding)
                    ) {
                        composable(Screen.Home.route) { HomeScreen(navController) }
                        composable(Screen.Catalog.route) { CatalogScreen(navController) }
                        // composable(Screen.About.route) { AboutScreen(navController) }
                        composable(Screen.SparklingDrinks.route) { SparklingDrinksScreen(navController) }
                        composable(Screen.StillDrinks.route) { StillDrinksScreen(navController) }
                        composable(Screen.HotDrinks.route) { HotDrinksScreen(navController) }
                    }
                }
            }
        }
    }
}

@Composable
fun MyBottomBar(navController: androidx.navigation.NavController) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    val activeColor = Orange
    val inactiveColor = Color.Black

    NavigationBar(
        containerColor = Color.White,
        tonalElevation = 8.dp
    ) {
        // Katalog
        NavigationBarItem(
            selected = currentRoute == Screen.Catalog.route,
            onClick = {
                navController.navigate(Screen.Catalog.route) {
                    popUpTo(navController.graph.startDestinationId)
                    launchSingleTop = true
                }
            },
            icon = {
                Icon(
                    Icons.Default.MenuBook,
                    contentDescription = "Katalog",
                    // Dynamiczna zmiana koloru ikony
                    tint = if (currentRoute == Screen.Catalog.route) activeColor else inactiveColor
                )
            },
            label = { Text("Katalog") },
            colors = NavigationBarItemDefaults.colors(
                indicatorColor = Color.Transparent, // Usuwa owalną plamę pod aktywną ikoną
                selectedTextColor = activeColor,
                unselectedTextColor = inactiveColor
            )
        )

        // Start
        NavigationBarItem(
            selected = currentRoute == Screen.Home.route,
            onClick = {
                navController.navigate(Screen.Home.route) {
                    popUpTo(navController.graph.startDestinationId)
                    launchSingleTop = true
                }
            },
            icon = {
                Icon(
                    Icons.Default.Home,
                    contentDescription = "Start",
                    tint = if (currentRoute == Screen.Home.route) activeColor else inactiveColor
                )
            },
            label = { Text("Start") },
            colors = NavigationBarItemDefaults.colors(
                indicatorColor = Color.Transparent,
                selectedTextColor = activeColor,
                unselectedTextColor = inactiveColor
            )
        )

        // 3. O NAS
        NavigationBarItem(
            selected = currentRoute == Screen.About.route,
            onClick = {
                // navController.navigate(Screen.About.route)
            },
            icon = {
                Icon(
                    Icons.Default.Person,
                    contentDescription = "O nas",
                    tint = if (currentRoute == Screen.About.route) activeColor else inactiveColor
                )
            },
            label = { Text("O nas") },
            colors = NavigationBarItemDefaults.colors(
                indicatorColor = Color.Transparent,
                selectedTextColor = activeColor,
                unselectedTextColor = inactiveColor
            )
        )
    }
}