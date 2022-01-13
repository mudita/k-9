package com.fsck.k9.activity.authError

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import com.fsck.k9.Account
import com.fsck.k9.ui.R
import com.fsck.k9.ui.authError.AuthenticationErrorViewModel
import com.fsck.k9.ui.authError.UiState
import com.fsck.k9.ui.base.K9Activity
import kotlinx.coroutines.flow.collect
import org.koin.androidx.viewmodel.ext.android.viewModel

class AuthenticationError : K9Activity() {

    private val authenticationErrorViewModel: AuthenticationErrorViewModel by viewModel()

    private var accountUuid: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.authentication_error)

        accountUuid = intent.getStringExtra(ACCOUNT_UUID)

        lifecycleScope.launchWhenCreated {
            authenticationErrorViewModel.uiState.collect(::handleUiState)
        }

        authenticationErrorViewModel.updateAuthenticationErrorData(accountUuid)
        findViewById<Button>(R.id.authenticationErrorDismissBt).setOnClickListener { authenticationErrorViewModel.onClose() }
    }

    private fun handleUiState(uiState: UiState) {
        setupAuthenticateAgainError(uiState.shouldShowAuthenticateAgainError)
        setupInvalidAccountError(uiState.shouldShowAccountUuidError)
        setupAccountErrorDescription(uiState.userName)
    }

    private fun setupAuthenticateAgainError(isVisible: Boolean) {
        findViewById<Button>(R.id.authenticationErrorAuthenticateAgainBt).apply {
            this.isVisible = isVisible
            setOnClickListener { authenticationErrorViewModel.onAuthenticationErrorPrimaryActionTapped(accountUuid) }
        }
    }

    private fun setupInvalidAccountError(isVisible: Boolean) {
        findViewById<TextView>(R.id.authenticationErrorDescriptionTv).run {
            this.isVisible = isVisible
            text = getString(R.string.authentication_error_account_error_text)
        }
    }

    private fun setupAccountErrorDescription(userName: String) {
        findViewById<TextView>(R.id.authenticationErrorDescriptionTv).run {
            text = getString(R.string.authentication_error_description, userName)
            isVisible = true
        }
    }

    companion object {

        private const val ACCOUNT_UUID = "account_uuid"

        @JvmStatic
        fun authErrorIntent(context: Context?, account: Account) =
            Intent(context, AuthenticationError::class.java).apply {
                putExtra(ACCOUNT_UUID, account.uuid)
            }
    }
}