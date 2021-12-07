package com.mudita.mail.ui.usecase.signIn.viewModel

import com.mudita.mail.interactor.signIn.SignInInteractor
import com.mudita.mail.repository.providers.model.ProviderTile
import com.mudita.mail.repository.providers.model.ProviderType
import com.mudita.mail.ui.usecase.signIn.navigator.SignInNavigator
import com.mudita.mail.ui.util.stringKey.StringKey
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import kotlin.test.assertEquals
import kotlin.test.fail
import org.junit.Before
import org.junit.Test

class SignInViewModelTest {

    private lateinit var navigator: SignInNavigator
    private lateinit var interactor: SignInInteractor

    private val testProvider =
        ProviderTile(
            StringKey.PROVIDER__GMAIL_NAME,
            StringKey.PROVIDER__GMAIL_DESCRIPTION,
            ProviderType.GMAIL
        )

    @Before
    fun setup() {
        navigator = mockk()
        interactor = mockk(relaxed = true)
    }

    @Test
    fun `calling viewModels provider selection should result on call to navigator`() {
        every { navigator.moveToEmailScreen(any()) } returns Unit
        val providerTypes = ProviderType.values()
        val viewModel = SignInViewModel(interactor, navigator)

        providerTypes.forEach {
            viewModel.selectProvider(it)
        }

        verify(exactly = providerTypes.size) { navigator.moveToEmailScreen(any()) }
    }

    @Test
    fun `viewModel creation should result in call to interactor`() {
        every { interactor.getProviders() } returns emptyList()

        SignInViewModel(interactor, navigator)

        verify(exactly = 1) { interactor.getProviders() }
    }

    @Test
    fun `viewModel creation should result in state with test providers`() {
        val expectedList = listOf(testProvider)
        every { interactor.getProviders() } returns expectedList

        val viewModel = SignInViewModel(interactor, navigator)

        verify(exactly = 1) { interactor.getProviders() }
        assertEquals(expectedList, viewModel.uiState.value.providers)
    }

    @Test
    fun `viewModel's selection of outlook should result in navigator call with outlook provider type`() {
        val expectedProvider = ProviderType.OUTLOOK
        every { navigator.moveToEmailScreen(expectedProvider) } returns Unit

        SignInViewModel(interactor, navigator).selectProvider(expectedProvider)

        verify(exactly = 1) { navigator.moveToEmailScreen(expectedProvider) }
    }

    @Test
    fun `viewModel's selection of gmail should result in navigator call with gmail provider type`() {
        val expectedProvider = ProviderType.GMAIL
        every { navigator.moveToEmailScreen(expectedProvider) } returns Unit

        SignInViewModel(interactor, navigator).selectProvider(expectedProvider)

        verify(exactly = 1) { navigator.moveToEmailScreen(expectedProvider) }
    }
}
