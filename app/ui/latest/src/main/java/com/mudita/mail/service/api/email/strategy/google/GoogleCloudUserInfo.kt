package com.mudita.mail.service.api.email.strategy.google

import androidx.annotation.Keep
import kotlinx.serialization.Serializable

@Keep
@Serializable
data class GoogleCloudUserInfo(
    val email: String
)
