package com.carplace.network.retrofit

import com.carplace.network.NetworkDataSource
import com.carplace.network.model.ChatCompletion
import com.carplace.network.model.ChatCompletionResponse
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST
import javax.inject.Inject
import javax.inject.Singleton

private const val BEARER_TOKEN = "Bearer"
private const val BASE_URL = "https://api.openai.com/"

private interface RetrofitNetworkApi {

    @POST("/v1/chat/completions")
    suspend fun sendMessageToChatGpt(
        @Header("Authorization") authorization: String,
        @Body requestBody: ChatCompletion
    ): ChatCompletionResponse
}

@Singleton
class RetrofitNetwork @Inject constructor(
    networkJson: Json,
    okhttpCallFactory: okhttp3.Call.Factory
) : NetworkDataSource {
    private val networkApi = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .callFactory(okhttpCallFactory)
        .addConverterFactory(
            networkJson.asConverterFactory("application/json".toMediaType())
        )
        .build()
        .create(RetrofitNetworkApi::class.java)

    override suspend fun sendMessageToChatGpt(chat: ChatCompletion): ChatCompletionResponse {
        return networkApi.sendMessageToChatGpt(BEARER_TOKEN, chat)
    }
}