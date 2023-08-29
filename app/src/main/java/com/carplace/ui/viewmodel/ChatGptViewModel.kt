package com.carplace.ui.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.carplace.data.repository.ChatGptRepository
import com.carplace.result.handle
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class ChatGptViewModel @Inject constructor(
    private val chatGptRepository: ChatGptRepository
) : ViewModel() {

    var uiState by mutableStateOf(ChatGptUiState())
        private set

    fun setText(text: String) {
        uiState = uiState.copy(userText = text)
    }

    fun sendMessageToApi() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val call = chatGptRepository.getChatGptAnswer(uiState.userText)
                uiState = uiState.copy(userText = "", isLoading = true)
                val response = call.execute()

                if (response.isSuccessful) {
                    val choice = response.body()?.choices?.get(0)
                    viewModelScope.launch(Dispatchers.Main) {
                        choice?.text?.let {
                            uiState = uiState.copy(userText = "", chatResponse = it, isLoading = false)
                        }
                    }
                }
            } catch (ex: Exception){
                uiState = uiState.copy(chatResponse = "ERROR", isLoading = false)
                ex.printStackTrace()
            }
        }
    }
}

data class ChatGptUiState(
    val userText: String = "",
    val chatResponse: String = "",
    val isLoading: Boolean = false
)
