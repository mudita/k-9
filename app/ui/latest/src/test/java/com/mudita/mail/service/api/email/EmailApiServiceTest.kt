package com.mudita.mail.service.api.email

import com.mudita.mail.MuditaRobolectricTest
import com.mudita.mail.service.api.client.ApiClientService
import io.mockk.mockk
import kotlin.test.fail
import org.junit.Before
import org.junit.Test

class EmailApiServiceTest : MuditaRobolectricTest() {

    private lateinit var apiClientService: ApiClientService

    @Before
    fun setup() {
        apiClientService = mockk()
    }

    @Test
    fun `call with token should result in response wrapped in result`() {
        fail()
    }

    @Test
    fun `call with empty token should result in response wrapped in failure`() {
        fail()
    }

    @Test
    fun `call with timeout should result in response wrapped in failure`() {
        fail()
    }
}