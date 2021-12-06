package com.mudita.mail.ui.viewModel

import androidx.lifecycle.ViewModel
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.EmptyCoroutineContext
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.CoroutineStart
import kotlinx.coroutines.launch

abstract class BaseViewModel<UiState : BaseUiState> : ViewModel() {

    protected fun CoroutineScope.launchWithLoading(
        context: CoroutineContext = EmptyCoroutineContext,
        start: CoroutineStart = CoroutineStart.DEFAULT,
        block: suspend CoroutineScope.() -> Unit
    ) {
        updateLoadingState(true)
        launch(context, start, block)
        updateLoadingState(false)
    }

    protected fun <T> Result<T>.runBlockOrShowError(block: (T) -> Unit) =
        fold(block) { UiError(it.message).let(::updateErrorState) }

    protected suspend fun <T> Result<T>.suspendingRunBlockOrShowError(block: suspend (T) -> Unit) =
        fold({ block(it) }, { UiError(it.message).let(::updateErrorState) })

    protected fun updateErrorState(throwable: Throwable) {
        UiError(throwable.message).let(::updateErrorState)
    }

    abstract fun updateLoadingState(isLoading: Boolean)

    abstract fun updateErrorState(uiError: UiError)
}
