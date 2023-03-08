package com.mudita.mail.ui.navigation

object SignInDestination : Destination("signIn")

object EmailDestination : Parametrized<String>("email", "providerType")

object AddAccountDestination : Destination("addAccount")

sealed class Destination(
    protected open val route: String,
) {

    open fun toRoute() = route
}

sealed class Parametrized<T : Any>(
    override val route: String,
    val key: String,
) : Destination(route) {

    final override fun toRoute() = "$route/{$key}"

    fun toRoute(param: T) = "$route/$param"
}
