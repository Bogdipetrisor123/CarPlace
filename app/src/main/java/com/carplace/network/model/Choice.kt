package com.carplace.network.model

import kotlinx.serialization.Serializable

@Serializable
data class Choice(
    val message: Message
)
