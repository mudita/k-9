package com.mudita.mail.service.api.email

interface EmailApiClientService {

    suspend fun getEmail(token: String): String
}
