package com.fsck.k9.ui.setup.ui.theme

import androidx.compose.material.MaterialTheme
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable

private val LightColors = lightColors(
    primary = WhitePure,
    primaryVariant = GreyLight,
    onPrimary = GreyDark,
    secondary = BlackPure,
    secondaryVariant = WhitePure,
    onSecondary = GreyDark,
    error = PaleRed
)

@Composable
fun MuditaTheme(
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colors = LightColors,
        typography = MuditaTypography,
        content = content
    )
}
