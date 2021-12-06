package com.mudita.mail.service.api.email.strategy.microsoft

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MicrosoftUserInfo(
    @SerialName("EmailAddress")
    val email: String
)