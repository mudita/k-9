package com.fsck.k9.ui.authError

import androidx.lifecycle.ViewModel
import com.fsck.k9.Preferences
import com.fsck.k9.account.AccountRemover
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update

data class UiState(
    val shouldShowAuthenticateAgainError: Boolean = false,
    val shouldShowAccountUuidError: Boolean = false,
    val userName: String = ""
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

        _uiState.update {
            it.copy(
                shouldShowAuthenticateAgainError = true,
                userName = displayName
            )
        }
    }

    fun onAuthenticationErrorPrimaryActionTapped(accountUuid: String?) {
        if (accountUuid == null) {
            _uiState.update { it.copy(shouldShowAccountUuidError = true) }
            return
        }
        accountRemover.removeAccount(accountUuid)
        navigator.moveToSignInScreen()
    }

    fun onClose() = navigator.moveToMessageList()
}
