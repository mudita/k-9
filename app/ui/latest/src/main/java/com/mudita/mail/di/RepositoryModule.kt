package com.mudita.mail.di

import com.mudita.mail.repository.providers.KeyBasedProvidersRepository
import com.mudita.mail.repository.providers.ProvidersRepository
import com.mudita.mail.repository.providers.authConfig.AuthConfigRepository
import com.mudita.mail.repository.providers.authConfig.PredefinedAuthConfigRepository
import org.koin.dsl.module

val repositoryModule = module {

    factory<ProvidersRepository> { KeyBasedProvidersRepository() }

    factory<AuthConfigRepository> { PredefinedAuthConfigRepository(get()) }
}
