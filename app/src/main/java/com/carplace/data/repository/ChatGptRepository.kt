package com.carplace.data.repository

import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import com.carplace.result.Result
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow


class ChatGptRepository @Inject constructor(
    private val chatGptApi: ChatGptApi
) {
//    fun getChatGptAnswer(message: String): Flow<Result<String>> {
//        val gptRequest = GptRequest(
//            prompt = message,
//            max_tokens = 4000,
//            model = "text-davinci-003"
//        )
//        return flow {
//            val call = chatGptApi.sendMessageToChatGpt(gptRequest)
//            val response = call.execute()
//            if (response.isSuccessful) {
//                val choice = response.body()?.choices?.get(0)
//                emit(Result.Success(choice?.text.orEmpty()))
//            }
//        }
//            .catch { error -> Result.Error(error) }
//    }
//}

    fun getChatGptAnswer(message: String) = chatGptApi.sendMessageToChatGpt(
        GptRequest(
            prompt = message,
            max_tokens = 4000,
            model = "text-davinci-003"
        )
    )
}
data class GptRequest(
    val model: String,
    val prompt: String,
    val max_tokens: Int,
)

data class GptResponse(
    val choices: List<Choice>
)

data class Choice(
    val text: String
)