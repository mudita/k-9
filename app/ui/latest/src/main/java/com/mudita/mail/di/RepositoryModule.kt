package com.mudita.mail.di

import com.fsck.k9.mail.oauth.OAuth2TokenProvider
import com.mudita.mail.provider.OAuth2TokenProviderImpl
import com.mudita.mail.repository.auth.config.AuthConfigRepository
import com.mudita.mail.repository.auth.config.PredefinedAuthConfigRepository
import com.mudita.mail.repository.auth.session.AuthSessionRepository
import com.mudita.mail.repository.auth.session.SharedPrefsAuthSessionRepository
import com.mudita.mail.repository.providers.KeyBasedProvidersRepository
import com.mudita.mail.repository.providers.ProvidersRepository
import org.koin.dsl.module

val repositoryModule = module {

    factory<ProvidersRepository> { KeyBasedProvidersRepository() }

    factory<AuthConfigRepository> { PredefinedAuthConfigRepository(get()) }

    factory<AuthSessionRepository> { SharedPrefsAuthSessionRepository(get()) }

    factory<OAuth2TokenProvider> { OAuth2TokenProviderImpl(get()) }
}
