package com.carplace.network.model

import kotlinx.serialization.Serializable

@Serializable
data class ChatCompletionResponse(
    val choices: List<Choice>
)
