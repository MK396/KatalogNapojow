package com.example.katalognapojow

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onAllNodesWithContentDescription
import androidx.compose.ui.test.onAllNodesWithText
import androidx.compose.ui.test.onFirst
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class AppUiTests {

    @get:Rule
    val composeTestRule = createAndroidComposeRule<MainActivity>()

    @Test
    fun homeScreen_displaysWelcomeTextAndCatalogButton() {
        composeTestRule.onNodeWithText("Wybierz idealny napój", substring = true).assertIsDisplayed()

        // Zamiast szukać jednego, upewniamy się, że istnieje przynajmniej jeden "Katalog"
        composeTestRule.onAllNodesWithText("Katalog").onFirst().assertIsDisplayed()
    }

    @Test
    fun navigation_navigateToCatalogAndBack() {
        // Pobieramy pierwszy napotkany tekst Katalog (rozwiązuje problem 2 węzłów)
        composeTestRule.onAllNodesWithText("Katalog").onFirst().performClick()

        // Oczekujemy, aż Compose zakończy proces nawigacji i zrekomponuje UI
        composeTestRule.waitForIdle()

        // Sprawdzenie, czy jesteśmy w katalogu
        composeTestRule.onNodeWithText("Napoje gazowane").assertIsDisplayed()

        // Powrót
        composeTestRule.onNodeWithContentDescription("Wstecz").performClick()
        composeTestRule.waitForIdle()

        // Sprawdzenie, czy wróciliśmy
        composeTestRule.onNodeWithText("Wybierz idealny napój", substring = true).assertIsDisplayed()
    }

    @Test
    fun themeToggle_clicksThemeButton() {
        val themeButton = composeTestRule.onNodeWithContentDescription("Zmień motyw")
        themeButton.assertIsDisplayed()
        themeButton.performClick()
        composeTestRule.waitForIdle()
    }

    @Test
    fun sparklingDrinks_opensDrinkAndShowsDialog() {
        // Przechodzimy do katalogu i czekamy
        composeTestRule.onAllNodesWithText("Katalog").onFirst().performClick()
        composeTestRule.waitForIdle()

        // Wchodzimy do "Napoje gazowane"
        composeTestRule.onNodeWithText("Napoje gazowane").performClick()
        composeTestRule.waitForIdle()

        // Klikamy obrazek
        composeTestRule.onNodeWithContentDescription("Coca Cola").performClick()

        // Oczekiwanie na pełne otwarcie i wyrenderowanie zawartości Dialogu
        composeTestRule.waitUntil(timeoutMillis = 5000) {
            composeTestRule
                .onAllNodesWithContentDescription("Powiększone zdjęcie Coca Cola")
                .fetchSemanticsNodes().isNotEmpty()
        }

        // Upewniamy się, że UI odpoczęło
        composeTestRule.waitForIdle()

        // Szukamy przycisku Zamknij (powinien być tylko jeden)
        val closeButton = composeTestRule.onNodeWithContentDescription("Zamknij")
        closeButton.assertIsDisplayed()
        closeButton.performClick()

        // Oczekiwanie, aż Dialog ZNIKNIE z ekranu
        composeTestRule.waitUntil(timeoutMillis = 5000) {
            composeTestRule
                .onAllNodesWithContentDescription("Zamknij")
                .fetchSemanticsNodes().isEmpty()
        }

        composeTestRule.waitForIdle()
        composeTestRule.onNodeWithText("Coca Cola").assertIsDisplayed()
    }

    @Test
    fun bottomBar_navigationWorks() {
        // Zabezpieczenie przed ewentualnymi duplikatami w przyszłości
        composeTestRule.onAllNodesWithText("O nas").onFirst().performClick()
        composeTestRule.waitForIdle()

        // Powrót na Start
        composeTestRule.onAllNodesWithText("Start").onFirst().performClick()
        composeTestRule.waitForIdle()
        composeTestRule.onNodeWithText("Wybierz idealny napój", substring = true).assertIsDisplayed()
    }
}