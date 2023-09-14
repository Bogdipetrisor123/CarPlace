package com.carplace.ui.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.carplace.data.repository.AuthRepository
import com.carplace.result.Result
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthenticationViewModel @Inject constructor(private val repository: AuthRepository) :
    ViewModel() {

    var uiState by mutableStateOf(AuthenticationUiState())
        private set

    fun signUp() {
        updateAuthenticationState(state = AuthenticationState.Loading)
        viewModelScope.launch {
            repository.signUp(uiState.email, uiState.password).collectLatest { result ->
                when (result) {
                    is Result.Success -> {
                        updateAuthenticationState(state = AuthenticationState.Default)
                        uiState = uiState.copy(email = "", password = "")
                    }

                    is Result.Error -> {
                        updateAuthenticationState(state = AuthenticationState.Error)
                    }
                }
            }
        }
    }

    fun login() {
        viewModelScope.launch {
            updateAuthenticationState(state = AuthenticationState.Loading)
            repository.login(uiState.email, uiState.password).collectLatest { result ->
                when (result) {
                    is Result.Success -> {
                        println("$$$$$ Success")
                        updateAuthenticationState(state = AuthenticationState.Success)
                    }

                    is Result.Error -> {
                        println("$$$$$ ERROR")
                        updateAuthenticationState(state = AuthenticationState.Error)
                    }
                }
            }
        }
    }

    fun signOut() {
        if (getCurrentUser() != null) {
            repository.signOut()
        }
    }

    fun getCurrentUser() = repository.getCurrentUser()

    fun updateEmail(email: String) {
        uiState = uiState.copy(email = email)
    }

    fun updatePassword(password: String) {
        uiState = uiState.copy(password = password)
    }

    fun updateShowLoginFlag(showLogin: Boolean) {
        uiState = uiState.copy(showLogin = showLogin, email = "", password = "")
    }

    private fun updateAuthenticationState(state: AuthenticationState) {
        uiState = uiState.copy(authenticationState = state)
    }
}

data class AuthenticationUiState(
    val email: String = "",
    val password: String = "",
    val showLogin: Boolean = true,
    val authenticationState: AuthenticationState = AuthenticationState.Default
)

sealed interface AuthenticationState {
    data object Default : AuthenticationState

    data object Loading : AuthenticationState

    data object Success : AuthenticationState

    data object Error : AuthenticationState
}