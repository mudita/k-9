package com.mudita.mail.ui.extension

import org.koin.core.parameter.ParametersHolder
import org.koin.core.parameter.parametersOf
import org.koin.core.scope.Scope

inline fun <reified T : Any> Scope.getWith(params: ParametersHolder): T =
    params.getOrNull() ?: get { parametersOf(*params.values.toTypedArray()) }
