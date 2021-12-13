package com.mudita.mail.service.api.email

import kotlinx.serialization.Serializable

@Serializable
data class UserInfo(
    val email: String
)
