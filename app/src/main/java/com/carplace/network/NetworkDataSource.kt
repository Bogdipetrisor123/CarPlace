package com.carplace.network

import com.carplace.network.model.ChatCompletion
import com.carplace.network.model.ChatCompletionResponse


interface NetworkDataSource {
    suspend fun sendMessageToChatGpt(chat: ChatCompletion): ChatCompletionResponse
}