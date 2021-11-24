package com.mudita.mail.ui.util

import android.content.res.Resources
import com.mudita.mail.R
import com.mudita.mail.repository.providers.model.ProviderType
import kotlin.test.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.RuntimeEnvironment

@RunWith(RobolectricTestRunner::class)
class ImageResolverTest {

    private lateinit var resources: Resources
    private lateinit var resolver: ProviderImageResolver

    @Before
    fun setup() {
        resources = RuntimeEnvironment.getApplication().resources
        resolver = providerImageResolver()
    }

    @Test
    fun `all provider types have corresponding drawable res id`() {
        val types = ProviderType.values()

        types.forEach {
            val imageRes = resolver.resolveResource(it)
            assertEquals(DRAWABLE_RES_NAME, resources.getResourceTypeName(imageRes))
        }
    }

    @Test
    fun `gmail icon resolved`() {
        val icon = R.drawable.ic_gmail

        assertEquals(icon, resolver.resolveResource(ProviderType.GMAIL))
    }

    @Test
    fun `icloud icon resolved`() {
        val icon = R.drawable.ic_icloud

        assertEquals(icon, resolver.resolveResource(ProviderType.ICLOUD))
    }

    @Test
    fun `outlook icon resolved`() {
        val icon = R.drawable.ic_outlook

        assertEquals(icon, resolver.resolveResource(ProviderType.OUTLOOK))
    }

    @Test
    fun `manual setup icon resolved`() {
        val icon = R.drawable.ic_email

        assertEquals(icon, resolver.resolveResource(ProviderType.MANUAL))
    }

    companion object {

        private const val DRAWABLE_RES_NAME = "drawable"
    }
}
