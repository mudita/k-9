package com.mudita.mail.service.api.email

import com.mudita.mail.repository.providers.model.ProviderType
import com.mudita.mail.service.api.client.ApiClientService
import com.mudita.mail.service.api.email.strategy.EmailApiContext
import com.mudita.mail.service.api.email.strategy.google.GoogleCloudEmailApiServiceStrategy
import com.mudita.mail.service.api.email.strategy.microsoft.MicrosoftEmailApiServiceStrategy

class EmailApiClientServiceImpl(
    private val emailApiContext: EmailApiContext,
    private val apiClientService: ApiClientService
) : EmailApiClientService {

    override suspend fun getEmail(
        providerType: ProviderType,
        token: String
    ): Result<String> {
        when (providerType) {
            ProviderType.GMAIL -> emailApiContext.setStrategy(GoogleCloudEmailApiServiceStrategy(apiClientService))
            ProviderType.OUTLOOK -> emailApiContext.setStrategy(MicrosoftEmailApiServiceStrategy(apiClientService))
            ProviderType.ICLOUD,
            ProviderType.MANUAL -> throw IllegalArgumentException()
        }
        return emailApiContext.executeStrategy(token)
    }
}
