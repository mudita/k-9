package com.mudita.mail.repository.auth.config

data class AuthConfig(
    val clientName: String,
    val clientId: String,
    val redirectUrl: String,
    val scope: String,
    val responseType: ResponseType,
    val authEndpoint: String,
    val tokenEndpoint: String
)
