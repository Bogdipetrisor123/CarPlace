package com.carplace.data.repository

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST

interface ChatGptApi {

    @Headers(
        "Content-Type: application/json",
        "Authorization: Bearer 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"
    )
    @POST("/v1/completions")
    fun sendMessageToChatGpt(
        @Body requestBody: GptRequest
    ): Call<GptResponse>
}