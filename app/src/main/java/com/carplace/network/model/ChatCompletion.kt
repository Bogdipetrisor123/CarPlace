package com.carplace.network.model

import kotlinx.serialization.Serializable

@Serializable
data class ChatCompletion(
    val model: String,
    val messages: List<Message>
)