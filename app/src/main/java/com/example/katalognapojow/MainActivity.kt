package com.example.katalognapojow

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.navigation.compose.*
import com.example.katalognapojow.ui.screens.*
import com.example.katalognapojow.ui.theme.KatalogNapojowTheme
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.katalognapojow.ui.theme.Orange

class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            var isDarkTheme by remember { mutableStateOf(false) }

            KatalogNapojowTheme(darkTheme = isDarkTheme) {
                val navController = rememberNavController()
                val navBackStackEntry by navController.currentBackStackEntryAsState()
                val currentRoute = navBackStackEntry?.destination?.route

                Scaffold(
                    topBar = {
                        TopAppBar(
                            title = {},
                            navigationIcon = {
                                if (currentRoute != Screen.Home.route) {
                                    IconButton(onClick = { navController.popBackStack() }) {
                                        Icon(Icons.Default.ArrowBack, contentDescription = "Wstecz")
                                    }
                                }
                            },
                            actions = {
                                IconButton(onClick = { isDarkTheme = !isDarkTheme }) {
                                    Icon(
                                        imageVector = if (isDarkTheme) Icons.Default.LightMode else Icons.Default.DarkMode,
                                        contentDescription = "Zmień motyw"
                                    )
                                }
                            }
                        )
                    },
                    bottomBar = { MyBottomBar(navController) }
                ) { innerPadding ->
                    NavHost(
                        navController = navController,
                        startDestination = Screen.Home.route,
                        modifier = Modifier.padding(innerPadding)
                    ) {
                        composable(Screen.Home.route) { HomeScreen(navController) }
                        composable(Screen.Catalog.route) { CatalogScreen(navController) }
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
    val inactiveColor = MaterialTheme.colorScheme.onSurface

    NavigationBar(containerColor = MaterialTheme.colorScheme.surface, tonalElevation = 2.dp) {
        NavigationBarItem(
            selected = currentRoute == Screen.Catalog.route,
            onClick = { navController.navigate(Screen.Catalog.route) { launchSingleTop = true } },
            icon = { Icon(Icons.Default.MenuBook, contentDescription = null, tint = if (currentRoute == Screen.Catalog.route) activeColor else inactiveColor) },
            label = { Text("Katalog") },
            colors = NavigationBarItemDefaults.colors(indicatorColor = Color.Transparent, selectedTextColor = activeColor)
        )
        NavigationBarItem(
            selected = currentRoute == Screen.Home.route,
            onClick = { navController.navigate(Screen.Home.route) { launchSingleTop = true } },
            icon = { Icon(Icons.Default.Home, contentDescription = null, tint = if (currentRoute == Screen.Home.route) activeColor else inactiveColor) },
            label = { Text("Start") },
            colors = NavigationBarItemDefaults.colors(indicatorColor = Color.Transparent, selectedTextColor = activeColor)
        )
        NavigationBarItem(
            selected = currentRoute == Screen.About.route,
            onClick = { /* O nas navigation */ },
            icon = { Icon(Icons.Default.Person, contentDescription = null, tint = if (currentRoute == Screen.About.route) activeColor else inactiveColor) },
            label = { Text("O nas") },
            colors = NavigationBarItemDefaults.colors(indicatorColor = Color.Transparent, selectedTextColor = activeColor)
        )
    }
}