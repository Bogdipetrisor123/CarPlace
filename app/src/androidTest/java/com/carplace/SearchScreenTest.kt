package com.carplace

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertTextEquals
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import com.carplace.ui.screens.SearchScreen
import com.carplace.ui.viewmodel.ChosenFilters
import com.carplace.ui.viewmodel.SearchUiState
import org.junit.Rule
import org.junit.Test

class SearchScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun searchFilters_whenDefaultState() {
        composeTestRule.setContent {
            SearchScreen(
                uiState = SearchUiState(),
                onSearchClick = { },
                onFilterClick = { _, _ -> },
                dismissDialog = { },
                onSelectFilter = { _ -> },
                onFilterValueChange = { _, _, _ -> }
            )
        }

        composeTestRule.onNodeWithText("Make").assertIsDisplayed()
        composeTestRule.onNodeWithText("Category").assertIsDisplayed()
        composeTestRule.onNodeWithText("Mileage").assertIsDisplayed()
        composeTestRule.onNodeWithText("First Registration").assertIsDisplayed()
        composeTestRule.onNodeWithText("Price").assertIsDisplayed()
        composeTestRule.onNodeWithText("Location").assertExists()
        composeTestRule.onNodeWithText("Fuel type").assertExists()
    }

    @Test
    fun modelFilter_whenMakeOptionIsEmpty_thenHideModelFilter() {
        composeTestRule.setContent {
            SearchScreen(
                uiState = SearchUiState(),
                onSearchClick = { },
                onFilterClick = { _, _ -> },
                dismissDialog = { },
                onSelectFilter = { _ -> },
                onFilterValueChange = { _, _, _ -> }
            )
        }
        composeTestRule.onNodeWithText("Model").assertDoesNotExist()
    }

    @Test
    fun modelFilter_whenMakeOptionHasOneSelectedValue_thenShowModelFilter() {
        composeTestRule.setContent {
            SearchScreen(
                uiState = SearchUiState(ChosenFilters(makes = listOf("BMW"))),
                onSearchClick = { },
                onFilterClick = { _, _ -> },
                dismissDialog = { },
                onSelectFilter = { _ -> },
                onFilterValueChange = { _, _, _ -> }
            )
        }
        composeTestRule.onNodeWithText("Model").assertIsDisplayed()
    }

    @Test
    fun modelFilter_whenMakeOptionHasMoreThanOneSelectedValues_thenHideModelFilter() {
        composeTestRule.setContent {
            SearchScreen(
                uiState = SearchUiState(ChosenFilters(makes = listOf("BMW", "Audi"))),
                onSearchClick = { },
                onFilterClick = { _, _ -> },
                dismissDialog = { },
                onSelectFilter = { _ -> },
                onFilterValueChange = { _, _, _ -> }
            )
        }
        composeTestRule.onNodeWithText("Model").assertDoesNotExist()
    }

    @Test
    fun categoryFilter_whenMoreOptionsAreSelected_thenDisplayThem() {
        composeTestRule.setContent {
            SearchScreen(
                uiState = SearchUiState(
                    ChosenFilters(
                        categories = listOf(
                            "Sedan",
                            "Cabriolet",
                            "Break",
                            "Coupe"
                        )
                    )
                ),
                onSearchClick = { },
                onFilterClick = { _, _ -> },
                dismissDialog = { },
                onSelectFilter = { _ -> },
                onFilterValueChange = { _, _, _ -> }
            )
        }
        composeTestRule.onNodeWithText("Sedan, Cabriolet, Break, Coupe").assertIsDisplayed()
    }

    @Test
    fun searchFilter_whenAllFiltersAreCompleted() {
        composeTestRule.setContent {
            SearchScreen(
                uiState = SearchUiState(
                    ChosenFilters(
                        makes = listOf("BMW"),
                        categories = listOf("Sedan"),
                        models = listOf("3 series"),
                        minMileage = 300,
                        maxMileage = 3000,
                        minFirstRegistration = 2015,
                        maxFirstRegistration = 2020,
                        minPrice = 15000,
                        maxPrice = 20000,
                        locations = listOf("Cluj"),
                        fuelTypes = listOf("Diesel")
                    )
                ),
                onSearchClick = { },
                onFilterClick = { _, _ -> },
                dismissDialog = { },
                onSelectFilter = { _ -> },
                onFilterValueChange = { _, _, _ -> }
            )
        }
        composeTestRule.onNodeWithText("BMW").assertIsDisplayed()
        composeTestRule.onNodeWithText("Sedan").assertIsDisplayed()
        composeTestRule.onNodeWithText("3 series").assertIsDisplayed()
        composeTestRule.onNodeWithText("300").assertIsDisplayed()
        composeTestRule.onNodeWithText("3000").assertIsDisplayed()
        composeTestRule.onNodeWithText("2015").assertIsDisplayed()
        composeTestRule.onNodeWithText("2020").assertIsDisplayed()
        composeTestRule.onNodeWithTag("Price min").assertTextEquals("15000")
        composeTestRule.onNodeWithTag("Price max").assertTextEquals("20000")
        composeTestRule.onNodeWithTag("Location", useUnmergedTree = true).assertTextEquals("Cluj")
        composeTestRule.onNodeWithTag("Fuel type", useUnmergedTree = true).assertTextEquals("Diesel")
    }
}