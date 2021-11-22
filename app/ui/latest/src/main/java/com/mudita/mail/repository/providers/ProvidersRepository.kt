package com.mudita.mail.repository.providers

import com.mudita.mail.repository.providers.model.ProviderTile

interface ProvidersRepository {

    fun getProviders(): List<ProviderTile>
}