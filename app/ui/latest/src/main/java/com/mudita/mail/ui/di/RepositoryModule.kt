package com.mudita.mail.ui.di

import com.mudita.mail.repository.providers.ProvidersRepository
import com.mudita.mail.repository.providers.ResBasedProvidersRepository
import org.koin.dsl.module

val repositoryModule = module {

    factory<ProvidersRepository> { ResBasedProvidersRepository(get()) }
}
