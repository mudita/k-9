package com.fsck.k9.account

import com.fsck.k9.Account
import com.fsck.k9.Core
import com.fsck.k9.LocalKeyStoreManager
import com.fsck.k9.Preferences
import com.fsck.k9.backend.BackendManager
import com.fsck.k9.controller.MessagingController
import com.fsck.k9.mailstore.LocalStoreProvider
import com.mudita.mail.interactor.delete.DeleteAccountInteractor
import timber.log.Timber

/**
 * Removes an account and all associated data.
 */
class AccountRemover(
    private val localStoreProvider: LocalStoreProvider,
    private val messagingController: MessagingController,
    private val backendManager: BackendManager,
    private val localKeyStoreManager: LocalKeyStoreManager,
    private val preferences: Preferences,
    private val deleteAccountInteractor: DeleteAccountInteractor
) {

    fun removeAccount(accountUuid: String) {
        val account = preferences.getAccount(accountUuid)
        if (account == null) {
            Timber.w("Can't remove account with UUID %s because it doesn't exist.", accountUuid)
            return
        }

        val accountName = account.description
        Timber.v("Removing account '%s'…", accountName)

        removeLocalStore(account)
        messagingController.deleteAccount(account)
        removeBackend(account)

        deleteAccountInteractor.deleteAccount(account.email)

        preferences.deleteAccount(account)

        localKeyStoreManager.deleteCertificates(account)
        Core.setServicesEnabled()

        Timber.v("Finished removing account '%s'.", accountName)
    }

    private fun removeLocalStore(account: Account) {
        try {
            val localStore = localStoreProvider.getInstance(account)
            localStore.delete()
        } catch (e: Exception) {
            Timber.w(e, "Error removing message database for account '%s'", account.description)

            // Ignore, this may lead to localStores on sd-cards that are currently not inserted to be left
        }

        localStoreProvider.removeInstance(account)
    }

    private fun removeBackend(account: Account) {
        try {
            backendManager.removeBackend(account)
        } catch (e: Exception) {
            Timber.e(e, "Failed to reset remote store for account %s", account.uuid)
        }
    }
}
