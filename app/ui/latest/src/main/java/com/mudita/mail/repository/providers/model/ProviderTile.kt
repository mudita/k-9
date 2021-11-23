package com.mudita.mail.repository.providers.model

import com.mudita.mail.ui.util.stringKey.StringKey

data class ProviderTile(
    val nameKey: StringKey,
    val descriptionKey: StringKey,
    val type: ProviderType
)
