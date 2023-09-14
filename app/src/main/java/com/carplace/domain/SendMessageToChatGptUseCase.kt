package com.carplace.domain

import com.carplace.data.repository.ChatGptRepository
import com.carplace.network.model.ChatCompletionResponse
import com.carplace.result.Result
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class SendMessageToChatGptUseCase @Inject constructor(private val chatGptRepository: ChatGptRepository) {
    operator fun invoke(message: String): Flow<Result<ChatCompletionResponse>> {
        return flow {
            val result = chatGptRepository.sendMessageToChatGpt(message)
            emit(Result.Success(result))
        }
            .catch { error -> Result.Error(error) }
    }
}