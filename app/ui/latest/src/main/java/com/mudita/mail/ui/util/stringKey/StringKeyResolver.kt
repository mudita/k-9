package com.mudita.mail.ui.util.stringKey

import android.content.Context
import androidx.annotation.IdRes
import androidx.annotation.StringRes
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource

interface StringKeyResolver {

    @IdRes
    fun getStringResId(key: StringKey): Int
}

private class StringKeyResolverImpl(private val context: Context) : StringKeyResolver {

    @StringRes
    override fun getStringResId(
        key: StringKey
    ) = context.run {
        resources.getIdentifier(key.name.lowercase(), STRING_TYPE, packageName)
    }

    companion object {
        private const val STRING_TYPE = "string"
    }
}

private fun stringKeyResolver(context: Context): StringKeyResolver = StringKeyResolverImpl(context)

@Composable
fun resolveStringKey(stringKey: StringKey) =
    LocalContext.current.let(::stringKeyResolver).getStringResId(stringKey).let { stringResource(id = it) }
