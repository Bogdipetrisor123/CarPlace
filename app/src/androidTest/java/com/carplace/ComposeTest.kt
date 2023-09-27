package com.carplace

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.test.assertTextEquals
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.carplace.ui.screens.AuthScreenUi
import com.carplace.ui.theme.CarPlaceTheme
import com.carplace.ui.viewmodel.AuthenticationState
import com.carplace.ui.viewmodel.AuthenticationUiState
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class ComposeTest {
    //abc@gmail.com
    //Test12e
    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun loginTest() {
        composeTestRule.setContent {
            var fakeUi by remember {
                mutableStateOf(
                    AuthenticationUiState(
                        email = "",
                        password = "",
                        showLogin = true,
                        authenticationState = AuthenticationState.Default
                    )
                )
            }
            CarPlaceTheme {
                AuthScreenUi(uiState = fakeUi,
                    updateEmail = {},
                    updatePassword = {},
                    doSignUp = {},
                    doLogin = {},
                    onSignUpTextButtonClicked = {
                        fakeUi = fakeUi.copy(showLogin = false)
                    },
                    onLoginTextButtonClicked = { },
                    onContinueWithoutAccountClicked = {})
            }
        }
        composeTestRule.onNodeWithTag("Switch to sign up screen button").performClick()
        composeTestRule.onNodeWithTag("Auth screen page title").assertTextEquals("Sign up")
        composeTestRule.onNodeWithTag("Auth screen button", useUnmergedTree = true).assertTextEquals("Sign Up")
    }

    @Test
    fun loginEmailInputTest() {
        composeTestRule.setContent {
            var fakeUi by remember {
                mutableStateOf(
                    AuthenticationUiState(
                        email = "",
                        password = "",
                        showLogin = true,
                        authenticationState = AuthenticationState.Default
                    )
                )
            }
            CarPlaceTheme {
                AuthScreenUi(uiState = fakeUi,
                    updateEmail = { fakeUi = fakeUi.copy(email = "test@gmail.com")},
                    updatePassword = { fakeUi = fakeUi.copy(password = "Test12e") },
                    doSignUp = {},
                    doLogin = {},
                    onSignUpTextButtonClicked = {
                        fakeUi = fakeUi.copy(showLogin = false)
                    },
                    onLoginTextButtonClicked = { },
                    onContinueWithoutAccountClicked = {})
            }
        }

        composeTestRule.onNodeWithTag("Email Input").performTextInput("test@gmail.com")
        composeTestRule.onNodeWithTag("Password Input").performTextInput("Test12e")

        composeTestRule.onNodeWithTag("Email Input", useUnmergedTree = true).assertTextEquals("test@gmail.com")
        composeTestRule.onNodeWithTag("Password Input", useUnmergedTree = true).assertTextEquals("•••••••")
    }
}