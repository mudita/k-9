package com.mudita.mail.interactor.signIn

import com.mudita.mail.repository.providers.model.ProviderTile

interface SignInInteractor {

    fun getProviders() : List<ProviderTile>
}