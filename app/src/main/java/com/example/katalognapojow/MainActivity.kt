package com.example.katalognapojow

import android.content.res.Configuration
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.navigation.compose.*
import com.example.katalognapojow.ui.screens.*
import com.example.katalognapojow.ui.theme.KatalogNapojowTheme
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.katalognapojow.ui.theme.Orange
import android.content.Context
import android.media.AudioManager

class MainActivity : ComponentActivity() {

    // Definicja tagu
    private val TAG = "Z8_LOGS"

    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Odczyt i logowanie parametrów
        val config: Configuration = resources.configuration
        logDeviceConfiguration(config)
        checkMediaStatus()
        logAudioParameters()

        enableEdgeToEdge()
        setContent {
            var isDarkTheme by rememberSaveable { mutableStateOf(false) }

            KatalogNapojowTheme(darkTheme = isDarkTheme, dynamicColor = false) {
                val navController = rememberNavController()
                val navBackStackEntry by navController.currentBackStackEntryAsState()
                val currentRoute = navBackStackEntry?.destination?.route

                // Pobranie aktualnej konfiguracji
                val configuration = LocalConfiguration.current
                val isLandscape = configuration.orientation == Configuration.ORIENTATION_LANDSCAPE


                Scaffold(
                    topBar = {
                        TopAppBar(
                            // Adaptacja wysokości paska do orientacji
                            modifier = if (isLandscape) Modifier.height(48.dp) else Modifier,
                            title = {  },
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
    private fun logDeviceConfiguration(config: Configuration) {
        // 1. Odczyt rozdzielczości ekranu (w dp)
        Log.i(TAG, "Rozdzielczość: ${config.screenWidthDp}x${config.screenHeightDp} dp")

        // 2. Odczyt orientacji ekranu
        val orientationText =
            if (config.orientation == Configuration.ORIENTATION_LANDSCAPE) "Pozioma" else "Pionowa"
        Log.i(TAG, "Orientacja: $orientationText")

        // 3. Odczyt gęstości pikseli (DPI)
        Log.i(TAG, "Gęstość (DPI): ${config.densityDpi}")

        // 4. Odczyt obsługi HDR
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            val isHdrSupported =
                (config.colorMode and Configuration.COLOR_MODE_HDR_MASK) == Configuration.COLOR_MODE_HDR_YES
            Log.i(TAG, "Obsługa HDR: ${if (isHdrSupported) "TAK" else "NIE"}")
        } else {
            // Dla starszych urządzeń
            Log.i(TAG, "Wesja systemu nieobsługuje")
        }
    }
    private fun checkMediaStatus() {
        val audioManager = getSystemService(AUDIO_SERVICE) as AudioManager

        // Sprawdzenie czy aktywny jest jakikolwiek strumień audio
        val isAudioActive = audioManager.isMusicActive
        Log.i(TAG, "Czy odtwarzany jest dźwięk: ${if (isAudioActive) "TAK" else "NIE"}")
    }
    private fun logAudioParameters() {
        val audioManager = getSystemService(Context.AUDIO_SERVICE) as AudioManager

        val sampleRate = audioManager.getProperty(AudioManager.PROPERTY_OUTPUT_SAMPLE_RATE)
        val framesPerBuffer = audioManager.getProperty(AudioManager.PROPERTY_OUTPUT_FRAMES_PER_BUFFER)

        Log.i(TAG, "Parametry audio - Sample Rate: $sampleRate Hz")
        Log.i(TAG, "Parametry audio - Frames per buffer: $framesPerBuffer")
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