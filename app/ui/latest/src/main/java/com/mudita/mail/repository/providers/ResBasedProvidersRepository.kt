package com.mudita.mail.repository.providers

import com.mudita.mail.repository.providers.model.ProviderTile
import com.mudita.mail.repository.providers.model.ProviderType
import com.mudita.mail.ui.util.stringKey.StringKey
import com.mudita.mail.ui.util.stringKey.StringKeyResolver

class ResBasedProvidersRepository(
    private val stringResolver: StringKeyResolver
) : ProvidersRepository {

    override fun getProviders() =
        stringResolver.run {
            listOf(
                ProviderTile(
                    getStringOrEmpty(StringKey.PROVIDER__GMAIL_NAME),
                    getStringOrEmpty(StringKey.PROVIDER__GMAIL_DESCRIPTION),
                    ProviderType.GMAIL
                ),
                ProviderTile(
                    getStringOrEmpty(StringKey.PROVIDER__ICLOUD_NAME),
                    getStringOrEmpty(StringKey.PROVIDER__ICLOUD_DESCRIPTION),
                    ProviderType.ICLOUD
                ),
                ProviderTile(
                    getStringOrEmpty(StringKey.PROVIDER__OUTLOOK_NAME),
                    getStringOrEmpty(StringKey.PROVIDER__OUTLOOK_DESCRIPTION),
                    ProviderType.OUTLOOK
                ),
                ProviderTile(
                    getStringOrEmpty(StringKey.PROVIDER__SETUP_MANUALLY_NAME),
                    getStringOrEmpty(StringKey.PROVIDER__SETUP_MANUALLY_DESCRIPTION),
                    ProviderType.MANUAL
                )
            )
        }
}
