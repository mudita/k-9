package com.mudita.mail.ui

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.lifecycle.lifecycleScope
import com.fsck.k9.ui.base.K9Activity
import com.mudita.mail.relay.auth.AuthRelay
import com.mudita.mail.service.authConfig.AuthConfig
import net.openid.appauth.AuthorizationRequest
import net.openid.appauth.AuthorizationResponse
import net.openid.appauth.AuthorizationService
import net.openid.appauth.AuthorizationServiceConfiguration
import timber.log.Timber

abstract class BaseAuthActivity(private val authRelay: AuthRelay) : K9Activity() {

    private var result: ActivityResultLauncher<Intent>? = null

    init {
        lifecycleScope.launchWhenCreated {
            startAuthProcess(authRelay.requestInput())
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        result = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            if (it.resultCode == RESULT_OK) {
                val resp = AuthorizationResponse.fromIntent(it.data!!)

                val code = resp?.authorizationCode
                if (code != null) {
                    lifecycleScope.launchWhenCreated {
                        authRelay.offerResult(code)
                    }
                } else {
                    // Handle error
                }
            } else {

            }
        }
    }

    private fun startAuthProcess(authConfig: AuthConfig) {
        val serviceConfig = AuthorizationServiceConfiguration(
            // To config
            Uri.parse("https://accounts.google.com/o/oauth2/auth"),
            Uri.parse("https://oauth2.googleapis.com/token")
        )

        val authRequestBuilder = AuthorizationRequest.Builder(
            serviceConfig,
            authConfig.clientId,
            authConfig.responseType.value,
            Uri.parse(authConfig.redirectUrl)
        )
        authRequestBuilder.setScope(authConfig.scope)

        val authService = AuthorizationService(this)
        val authIntent = authService.getAuthorizationRequestIntent(authRequestBuilder.build())
        result?.launch(authIntent)
    }
}