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
    val shouldShowEditServerSettings: Boolean = false,
    val shouldShowAccountUuidError: Boolean = false,
    val userName: String = "",
    val isOAuth: Boolean = false
)

class AuthenticationErrorViewModel(
    private val accountRemover: AccountRemover,
    private val navigator: AuthorizationErrorNavigator,
    private val authenticationErrorNotificationController: NotificationController,
    private val preferences: Preferences
) : ViewModel() {

    private val _uiState = MutableStateFlow(UiState())
    val uiState: StateFlow<UiState> = _uiState

    private fun getIncomingAuthType(accountUuid: String) =
        preferences.getAccount(accountUuid)?.incomingServerSettings?.authenticationType

    fun onAccountSupplied(accountUuid: String?) {
        if (accountUuid == null) {
            _uiState.update { it.copy(shouldShowAccountUuidError = true) }
            return
        }

        val displayName = preferences.getAccount(accountUuid)?.displayName ?: return
        val authType = getIncomingAuthType(accountUuid)

        if (authType == AuthType.XOAUTH2) {
            _uiState.update {
                it.copy(
                    shouldShowReauthorize = true,
                    userName = displayName,
                    isOAuth = true
                )
            }
        } else {
            _uiState.update {
                it.copy(shouldShowEditServerSettings = true, userName = displayName, isOAuth = false)
            }
        }
    }

    fun onOauthErrorAction(accountUuid: String?) {
        if (accountUuid == null) {
            _uiState.update { it.copy(shouldShowAccountUuidError = true) }
            return
        }
        val authType = getIncomingAuthType(accountUuid)
        if (authType == AuthType.XOAUTH2) {
            accountRemover.removeAccount(accountUuid)
            navigator.moveToSignInScreen()
        }
    }

    fun onServerErrorAction(accountUuid: String?, incoming: Boolean) {
        if (accountUuid == null) {
            _uiState.update { it.copy(shouldShowAccountUuidError = true) }
            return
        }
        val account = preferences.getAccount(accountUuid) ?: return
        val authType = getIncomingAuthType(accountUuid)
        if (authType == AuthType.XOAUTH2) {
            return
        }
        if (incoming) {
            navigator.moveToEditIncomingServerSettings(account)
        } else {
            navigator.moveToEditOutgoingServerSettings(account)
        }
        authenticationErrorNotificationController.clearAuthenticationErrorNotification(account, incoming)
    }

    fun onClose() = navigator.moveToMessageList()
}