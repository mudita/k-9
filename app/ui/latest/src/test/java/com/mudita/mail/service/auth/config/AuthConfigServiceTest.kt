package com.mudita.mail.service.auth.config

import com.mudita.mail.MuditaRobolectricTest
import kotlin.test.fail
import org.junit.Test

class AuthConfigServiceTest : MuditaRobolectricTest() {

    @Test
    fun `call with gmail provider type should result in gmail auth config wrapped in success result`() {
        fail()
    }

    @Test
    fun `call with not support by ouath provider type should result in failure return type`() {
        fail()
    }
}