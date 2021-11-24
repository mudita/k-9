package com.mudita.mail.ui.di

import com.mudita.mail.repository.providers.KeyBasedProvidersRepository
import com.mudita.mail.repository.providers.ProvidersRepository
import org.koin.dsl.module

val repositoryModule = module {

    factory<ProvidersRepository> { KeyBasedProvidersRepository() }
}
