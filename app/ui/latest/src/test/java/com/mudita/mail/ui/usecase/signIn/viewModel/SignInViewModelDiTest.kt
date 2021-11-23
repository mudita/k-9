package com.mudita.mail.ui.usecase.signIn.viewModel

import com.fsck.k9.ui.base.navigation.ToSetupAccountNavigator
import com.mudita.mail.MuditaRobolectricTest
import com.mudita.mail.repository.providers.model.ProviderType
import io.mockk.every
import io.mockk.verify
import kotlin.test.assertNotNull
import org.junit.Test

class SignInViewModelDiTest : MuditaRobolectricTest() {

    @Test
    fun `test viewModel creation from di`() {
        assertNotNull(getKoin().get<SignInViewModel>())
    }

    @Test
    fun `test external navigator mocked call`() {
        val toSetupAccountNavigator = getKoin().get<ToSetupAccountNavigator>()
        every { toSetupAccountNavigator.moveToSetupAccount(any()) } returns Unit

        val viewModel = getKoin().get<SignInViewModel>()

        viewModel.selectProvider(ProviderType.GMAIL)

        verify(exactly = 1) { toSetupAccountNavigator.moveToSetupAccount(any()) }
    }
}
