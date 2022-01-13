package com.fsck.k9.ui.authError

import androidx.lifecycle.ViewModel
import com.fsck.k9.Preferences
import com.fsck.k9.account.AccountRemover
import com.fsck.k9.mail.AuthType
import com.fsck.k9.notification.NotificationController
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update

data class UiState(
    val shouldShowReauthorize: Boolean = false,
    val shouldShowAccountUuidError: Boolean = false,
    val userName: String = "",
    val isOAuth: Boolean = false
)

class AuthenticationErrorViewModel(
    private val accountRemover: AccountRemover,
    private val navigator: AuthorizationErrorNavigator,
    private val preferences: Preferences
) : ViewModel() {

    private val _uiState = MutableStateFlow(UiState())
    val uiState: StateFlow<UiState> = _uiState

    fun updateAuthenticationErrorData(accountUuid: String?) {
        if (accountUuid == null) {
            _uiState.update { it.copy(shouldShowAccountUuidError = true) }
            return
        }

        val displayName = preferences.getAccount(accountUuid)?.displayName ?: return
        val isOAuth = getIncomingAuthType(accountUuid) == AuthType.XOAUTH2

        _uiState.update {
            it.copy(
                shouldShowReauthorize = true,
                userName = displayName,
                isOAuth = isOAuth
            )
        }
    }

    private fun getIncomingAuthType(accountUuid: String) =
        preferences.getAccount(accountUuid)?.incomingServerSettings?.authenticationType

    fun onOauthErrorAction(accountUuid: String?) {
        if (accountUuid == null) {
            _uiState.update { it.copy(shouldShowAccountUuidError = true) }
            return
        }
        accountRemover.removeAccount(accountUuid)
        navigator.moveToSignInScreen()
    }

    fun onClose() = navigator.moveToMessageList()
}