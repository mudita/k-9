package com.mudita.mail.repository.providers

import kotlin.test.assertEquals
import kotlin.test.assertTrue
import org.junit.Before
import org.junit.Test
import org.koin.test.AutoCloseKoinTest

class KeyBasedProvidersRepositoryTest : AutoCloseKoinTest() {

    private lateinit var repository: ProvidersRepository

    @Before
    fun setup() {
        repository = KeyBasedProvidersRepository()
    }

    @Test
    fun `repository should provide four providers`() {
        val numberOfProviders = 4

        val providers = repository.getProviders()

        assertEquals(numberOfProviders, providers.size)
    }

    @Test
    fun `providers from repository should differentiate in type`() {
        val providers = repository.getProviders()

        assertTrue { providers.size == providers.distinctBy { it.type }.size }
    }
}