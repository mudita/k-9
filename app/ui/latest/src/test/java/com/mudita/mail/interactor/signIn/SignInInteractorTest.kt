package com.mudita.mail.interactor.signIn

import com.mudita.mail.repository.providers.ProvidersRepository
import com.mudita.mail.repository.providers.model.ProviderTile
import com.mudita.mail.repository.providers.model.ProviderType
import com.mudita.mail.ui.util.stringKey.StringKey
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import kotlin.test.assertEquals
import org.junit.Before
import org.junit.Test

class SignInInteractorTest {

    private lateinit var repository: ProvidersRepository

    private val testProvider =
        ProviderTile(
            StringKey.PROVIDER__GMAIL_NAME,
            StringKey.PROVIDER__GMAIL_DESCRIPTION,
            ProviderType.GMAIL
        )

    @Before
    fun setup() {
        repository = mockk()
    }

    @Test
    fun `signInInteractor should return test providers supplied to repository`() {
        val expectedList = listOf(testProvider)
        every { repository.getProviders() } returns expectedList

        val interactor: SignInInteractor = SignInInteractorImpl(repository)
        val providers = interactor.getProviders()

        verify(exactly = 1) { repository.getProviders() }
        assertEquals(expectedList, providers)
    }
}
