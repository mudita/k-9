package com.mudita.mail.service.api.email

import com.mudita.mail.repository.providers.model.ProviderType

interface EmailApiClientService {

    suspend fun getEmail(
        providerType: ProviderType,
        token: String
    ): Result<String>
}
