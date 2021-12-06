package com.mudita.mail.service.api.email.strategy.google

import kotlinx.serialization.Serializable

@Serializable
data class GoogleCloudUserInfo(
    val email: String
)
