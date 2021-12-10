package com.mudita.mail.interactor.delete

import com.mudita.mail.service.auth.session.AuthSessionService

class DeleteAccountInteractorImpl(
    private val authSessionService: AuthSessionService
) : DeleteAccountInteractor {

    override fun deleteAccount(username: String) = authSessionService.removeToken(username)
}
