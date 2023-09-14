package com.carplace.data.repository

import com.carplace.network.model.ChatCompletionResponse

interface ChatGptRepository {
    suspend fun sendMessageToChatGpt(message: String): ChatCompletionResponse
}