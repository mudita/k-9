package com.mudita.mail.service.api.email.strategy

class EmailApiContext {

    private lateinit var strategy: EmailApiServiceStrategy

    fun setStrategy(strategy: EmailApiServiceStrategy) {
        this.strategy = strategy
    }

    suspend fun executeStrategy(accessToken: String) =
        strategy.getEmail(accessToken)
}
