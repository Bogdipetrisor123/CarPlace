package com.carplace.ui.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.carplace.domain.SendMessageToChatGptUseCase
import com.carplace.result.handle
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class ChatGptViewModel @Inject constructor(
    private val sendMessageToChatGptUseCase: SendMessageToChatGptUseCase
) : ViewModel() {

    var uiState by mutableStateOf(ChatGptUiState())
        private set

    fun setText(text: String) {
        uiState = uiState.copy(userText = text)
    }

    fun sendMessageToApi() {
        uiState = uiState.copy(isLoading = true)
        viewModelScope.launch {
            sendMessageToChatGptUseCase(uiState.userText).collectLatest { result ->
                result.handle(onSuccess = { data ->
                    uiState = uiState.copy(
                        userText = "",
                        chatResponse = data.data.choices[0].message.content,
                        isLoading = false
                    )
                }, onError = {
                    uiState = uiState.copy(chatResponse = "ERROR", isLoading = false)
                })
            }
        }
    }
}

data class ChatGptUiState(
    val userText: String = "",
    val chatResponse: String = "",
    val isLoading: Boolean = false
)
