package com.mudita.mail.interactor.signIn

import com.mudita.mail.repository.providers.ProvidersRepository

class SignInInteractorImpl(
    private val providersRepository: ProvidersRepository
) : SignInInteractor {

    override fun getProviders() = providersRepository.getProviders()
}
