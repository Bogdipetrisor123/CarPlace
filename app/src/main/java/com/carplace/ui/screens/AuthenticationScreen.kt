package com.carplace.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.carplace.ui.viewmodel.AuthenticationState
import com.carplace.ui.viewmodel.AuthenticationUiState
import com.carplace.ui.viewmodel.AuthenticationViewModel

@Composable
fun AuthScreen(viewModel: AuthenticationViewModel = hiltViewModel(), goToHomeScreen: () -> Unit) {
    val uiState = viewModel.uiState

    AuthScreenUi(
        uiState = uiState,
        updateEmail = viewModel::updateEmail,
        updatePassword = viewModel::updatePassword,
        doSignUp = viewModel::signUp,
        doLogin = { viewModel.login() },
        onSignUpTextButtonClicked = { viewModel.updateShowLoginFlag(false) },
        onLoginTextButtonClicked = { viewModel.updateShowLoginFlag(true) },
        onContinueWithoutAccountClicked = goToHomeScreen
    )
    LaunchedEffect(key1 = uiState.authenticationState) {
        if (uiState.authenticationState is AuthenticationState.Success) {
            goToHomeScreen.invoke()
        }
    }
}

@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun AuthScreenUi(
    uiState: AuthenticationUiState,
    updateEmail: (String) -> Unit,
    updatePassword: (String) -> Unit,
    doSignUp: () -> Unit,
    doLogin: () -> Unit,
    onSignUpTextButtonClicked: () -> Unit,
    onLoginTextButtonClicked: () -> Unit,
    onContinueWithoutAccountClicked: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(start = 16.dp, end = 16.dp, top = 128.dp),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = if (uiState.showLogin) {
                "Login"
            } else {
                "Sign up"
            },
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 16.dp).testTag("Auth screen page title")
        )

        OutlinedTextField(
            value = uiState.email,
            onValueChange = updateEmail,
            label = { Text(text = "Email") },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Email,
                imeAction = ImeAction.Next
            ),
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp).testTag("Email Input")
        )

        OutlinedTextField(
            value = uiState.password,
            onValueChange = updatePassword,
            label = { Text(text = "Password") },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Password,
                imeAction = ImeAction.Done
            ),
            visualTransformation = PasswordVisualTransformation(),
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp).testTag("Password Input")
        )

        Button(
            onClick = if (uiState.showLogin) {
                doLogin
            } else {
                doSignUp
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            if (uiState.authenticationState is AuthenticationState.Loading) {
                CircularProgressIndicator(color = Color.White, modifier = Modifier.size(24.dp))
            } else {
                Text(modifier = Modifier.testTag("Auth screen button"),
                    text = if (uiState.showLogin) {
                        "Login"
                    } else {
                        "Sign Up"
                    }
                )
            }
        }
        if (uiState.showLogin) {
            Text(text = "Sign Up", textAlign = TextAlign.Center,
                modifier = Modifier.clickable { onSignUpTextButtonClicked.invoke() }.testTag("Switch to sign up screen button"))
        } else {
            Text(text = "Login", textAlign = TextAlign.Center,
                modifier = Modifier.clickable { onLoginTextButtonClicked.invoke() }.testTag("Switch to login screen button"))
        }
        Text(text = "Continue with no account", textAlign = TextAlign.Center,
            modifier = Modifier.clickable { onContinueWithoutAccountClicked.invoke() }.testTag("test"))
    }
}