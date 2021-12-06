package com.mudita.mail.service.auth

import com.mudita.mail.MuditaRobolectricTest
import kotlin.test.fail
import org.junit.Test

class AuthServiceTest : MuditaRobolectricTest() {

    @Test
    fun `getting auth intent with gmail config should result in google oauth intent`() {
        fail()
    }

    @Test
    fun `getting auth intent with outlook config should result in outlook oauth intent`() {
        fail()
    }

    @Test
    fun `building request data with invalid input data should result in failure return value`() {
        fail()
    }

    @Test
    fun `processing gmail auth response data should result in success result with gmail accounts email`() {
        fail()
    }

    @Test
    fun `processing outlook auth response data should result in success result with outlook accounts email`() {
        fail()
    }

    @Test
    fun `processing gmail auth response data should result in auth response saved to storage and accessible further`() {
        fail()
    }

    @Test
    fun `processing outlook auth response data should result in auth response saved to storage and accessible further`() {
        fail()
    }

    @Test
    fun `processing response intent with request exception should result in failure return with exception`() {
        fail()
    }

    @Test
    fun `processing response with code for token response error should result in failure return with exception`() {
        fail()
    }
}
