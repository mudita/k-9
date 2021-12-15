package com.mudita.mail.service.api.email.strategy.microsoft

import androidx.annotation.Keep
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Keep
@Serializable
data class MicrosoftUserInfo(
    @SerialName("EmailAddress")
    val email: String
)
