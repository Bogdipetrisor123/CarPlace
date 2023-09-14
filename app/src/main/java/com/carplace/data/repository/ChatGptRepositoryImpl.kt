package com.carplace.data.repository

import com.carplace.network.NetworkDataSource
import com.carplace.network.model.ChatCompletion
import com.carplace.network.model.ChatCompletionResponse
import com.carplace.network.model.Message
import retrofit2.Call
import javax.inject.Inject


class ChatGptRepositoryImpl @Inject constructor(
    private val networkDataSource: NetworkDataSource
): ChatGptRepository {

    override suspend fun sendMessageToChatGpt(message: String): ChatCompletionResponse {
        val chatCompletion = ChatCompletion(model = "gpt-4", messages = listOf(Message(role = "user", content = message)))
        return networkDataSource.sendMessageToChatGpt(chatCompletion)
    }
}