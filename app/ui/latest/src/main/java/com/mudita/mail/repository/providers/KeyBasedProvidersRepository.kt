package com.mudita.mail.repository.providers

import com.mudita.mail.repository.providers.model.ProviderTile
import com.mudita.mail.repository.providers.model.ProviderType
import com.mudita.mail.ui.util.stringKey.StringKey

class KeyBasedProvidersRepository : ProvidersRepository {

    override fun getProviders() =
        listOf(
            ProviderTile(
                StringKey.PROVIDER__GMAIL_NAME,
                StringKey.PROVIDER__GMAIL_DESCRIPTION,
                ProviderType.GMAIL
            ),
            ProviderTile(
                StringKey.PROVIDER__ICLOUD_NAME,
                StringKey.PROVIDER__ICLOUD_DESCRIPTION,
                ProviderType.ICLOUD
            ),
            ProviderTile(
                StringKey.PROVIDER__OUTLOOK_NAME,
                StringKey.PROVIDER__OUTLOOK_DESCRIPTION,
                ProviderType.OUTLOOK
            ),
            ProviderTile(
                StringKey.PROVIDER__SETUP_MANUALLY_NAME,
                StringKey.PROVIDER__SETUP_MANUALLY_DESCRIPTION,
                ProviderType.MANUAL
            )
        )
}
