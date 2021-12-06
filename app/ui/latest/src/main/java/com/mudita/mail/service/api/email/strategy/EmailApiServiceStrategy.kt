package com.mudita.mail.service.api.email.strategy

interface EmailApiServiceStrategy {

    suspend fun getEmail(accessToken: String): Result<String>
}
