package com.example.katalognapojow

import android.content.Context
import android.content.res.Configuration
import android.media.AudioManager
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.calculateWindowSizeClass
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.*
import com.example.katalognapojow.ui.screens.*
import com.example.katalognapojow.ui.theme.KatalogNapojowTheme
import com.example.katalognapojow.ui.theme.LocalDimens
import com.example.katalognapojow.ui.theme.Orange
import com.example.katalognapojow.ui.theme.adaptiveDimens

class MainActivity : ComponentActivity() {

    private val TAG = "Z8_LOGS"

    @OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterial3WindowSizeClassApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        logDeviceConfiguration(resources.configuration)
        checkMediaStatus()
        logAudioParameters()

        enableEdgeToEdge()
        setContent {
            val windowSizeClass = calculateWindowSizeClass(this)
            val dimens = adaptiveDimens(windowSizeClass)
            var isDarkTheme by rememberSaveable { mutableStateOf(false) }

            CompositionLocalProvider(LocalDimens provides dimens) {
                KatalogNapojowTheme(darkTheme = isDarkTheme, dynamicColor = false) {
                    val navController = rememberNavController()
                    val navBackStackEntry by navController.currentBackStackEntryAsState()
                    val currentRoute = navBackStackEntry?.destination?.route

                    val configuration = LocalConfiguration.current
                    val isLandscape = configuration.orientation == Configuration.ORIENTATION_LANDSCAPE

                    Row(modifier = Modifier.fillMaxSize()) {

                        if (isLandscape) {
                            MyNavigationRail(navController, currentRoute)
                        }

                        Scaffold(
                            contentWindowInsets = WindowInsets.navigationBars,
                            topBar = {
                                TopAppBar(
                                    windowInsets = if (isLandscape) WindowInsets(top = 24.dp, left = 10.dp, right = 10.dp) else TopAppBarDefaults.windowInsets,
                                    title = {
                                        val titleText = when (currentRoute) {
                                            Screen.SparklingDrinks.route -> "Napoje gazowane"
                                            Screen.StillDrinks.route -> "Napoje niegazowane"
                                            Screen.HotDrinks.route -> "Napoje gorące"
                                            Screen.Catalog.route -> "Katalog"
                                            Screen.Home.route -> "Katalog Napojów"
                                            Screen.About.route -> "O nas"
                                            else -> "Katalog Napojów"
                                        }
                                        Text(titleText)
                                    },
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
                            bottomBar = {
                                if (!isLandscape) {
                                    MyBottomBar(navController, currentRoute)
                                }
                            }
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
                                composable(Screen.About.route) { AboutUsScreen() }
                            }
                        }
                    }
                }
            }
        }
    }

    private fun logDeviceConfiguration(config: Configuration) {
        Log.i(TAG, "Rozdzielczość: ${config.screenWidthDp}x${config.screenHeightDp} dp")
        val orientationText = if (config.orientation == Configuration.ORIENTATION_LANDSCAPE) "Pozioma" else "Pionowa"
        Log.i(TAG, "Orientacja: $orientationText")
        Log.i(TAG, "Gęstość (DPI): ${config.densityDpi}")
    }

    private fun checkMediaStatus() {
        val audioManager = getSystemService(AUDIO_SERVICE) as AudioManager
        Log.i(TAG, "Czy odtwarzany jest dźwięk: ${if (audioManager.isMusicActive) "TAK" else "NIE"}")
    }

    private fun logAudioParameters() {
        val audioManager = getSystemService(Context.AUDIO_SERVICE) as AudioManager
        val sampleRate = audioManager.getProperty(AudioManager.PROPERTY_OUTPUT_SAMPLE_RATE)
        val framesPerBuffer = audioManager.getProperty(AudioManager.PROPERTY_OUTPUT_FRAMES_PER_BUFFER)
        Log.i(TAG, "Parametry audio - Sample Rate: $sampleRate Hz, Frames: $framesPerBuffer")
    }
}

@Composable
fun MyBottomBar(navController: NavController, currentRoute: String?) {
    val activeColor = Orange
    val inactiveColor = MaterialTheme.colorScheme.onSurface

    NavigationBar(containerColor = MaterialTheme.colorScheme.surface, tonalElevation = 2.dp) {
        val items = listOf(
            NavigationItem("Katalog", Screen.Catalog.route, Icons.Default.MenuBook),
            NavigationItem("Start", Screen.Home.route, Icons.Default.Home),
            NavigationItem("O nas", Screen.About.route, Icons.Default.Person)
        )

        items.forEach { item ->
            NavigationBarItem(
                selected = currentRoute == item.route,
                onClick = { if (currentRoute != item.route) navController.navigate(item.route) { launchSingleTop = true } },
                icon = { Icon(item.icon, contentDescription = null, tint = if (currentRoute == item.route) activeColor else inactiveColor) },
                label = { Text(item.label) },
                colors = NavigationBarItemDefaults.colors(indicatorColor = Color.Transparent, selectedTextColor = activeColor)
            )
        }
    }
}

@Composable
fun MyNavigationRail(navController: NavController, currentRoute: String?) {
    val activeColor = Orange
    val inactiveColor = MaterialTheme.colorScheme.onSurface

    NavigationRail(
        containerColor = MaterialTheme.colorScheme.surface,
        header = { Spacer(Modifier.height(12.dp)) }
    ) {
        val items = listOf(
            NavigationItem("Katalog", Screen.Catalog.route, Icons.Default.MenuBook),
            NavigationItem("Start", Screen.Home.route, Icons.Default.Home),
            NavigationItem("O nas", Screen.About.route, Icons.Default.Person)
        )

        items.forEach { item ->
            NavigationRailItem(
                selected = currentRoute == item.route,
                onClick = { if (currentRoute != item.route) navController.navigate(item.route) { launchSingleTop = true } },
                icon = { Icon(item.icon, contentDescription = null, tint = if (currentRoute == item.route) activeColor else inactiveColor) },
                label = { Text(item.label) },
                colors = NavigationRailItemDefaults.colors(indicatorColor = Color.Transparent, selectedTextColor = activeColor)
            )
        }
    }
}

data class NavigationItem(val label: String, val route: String, val icon: androidx.compose.ui.graphics.vector.ImageVector)