package com.mudita.mail.ui.util.stringKey

import kotlin.test.assertTrue
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.RuntimeEnvironment

@RunWith(RobolectricTestRunner::class)
class StringKeyResolverTest {

    @Test
    fun `all string keys are resolved`() {
        val resolver = stringKeyResolver(RuntimeEnvironment.getApplication().applicationContext)
        val keys = StringKey.values()

        keys.forEach {
            // thrown Resources.NotFoundException() will fail the test
            resolver.getStringResId(it)
        }

        assertTrue { true }
    }
}