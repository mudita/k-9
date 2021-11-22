package com.mudita.mail.ui.util.stringKey

import android.content.Context
import android.content.res.Resources
import java.util.Locale
import org.koin.dsl.module

interface StringKeyResolver {

    fun getStringOrEmpty(key: StringKey): String
}

private class StringResolverImpl(private val context: Context) : StringKeyResolver {

    override fun getStringOrEmpty(key: StringKey) =
        try {
            context.mapToString(key)
        } catch (e: Resources.NotFoundException) {
            EMPTY_STRING
        }

    private fun Context.mapToString(
        key: StringKey,
        locale: Locale = Locale.getDefault()
    ): String =
        resources.getIdentifier(key.name.lowercase(locale), STRING_TYPE, packageName)
            .let(::getString)

    companion object {

        private const val STRING_TYPE = "string"
        private const val EMPTY_STRING = ""
    }
}

val stringResolverModule = module {

    factory<StringKeyResolver> {
        StringResolverImpl(get())
    }
}