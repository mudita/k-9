package com.mudita.mail.ui.util

import androidx.annotation.DrawableRes
import androidx.compose.runtime.Composable
import com.mudita.mail.R
import com.mudita.mail.repository.providers.model.ProviderType

interface ProviderImageResolver {

    @DrawableRes
    fun resolveResource(providerType: ProviderType): Int
}

private class ProviderImageResolverImpl : ProviderImageResolver {

    @DrawableRes
    override fun resolveResource(providerType: ProviderType) =
        when (providerType) {
            ProviderType.GMAIL -> R.drawable.ic_gmail
            ProviderType.ICLOUD -> R.drawable.ic_icloud
            ProviderType.OUTLOOK -> R.drawable.ic_outlook
            ProviderType.MANUAL -> R.drawable.ic_email
        }
}

fun providerImageResolver(): ProviderImageResolver = ProviderImageResolverImpl()

@Composable
@DrawableRes
fun resolverProviderImageRes(providerType: ProviderType) =
    providerImageResolver().resolveResource(providerType)
