package com.mudita.mail.util

fun <T> T?.successOrFailure(throwable: Throwable) =
    if (this != null) {
        Result.success(this)
    } else {
        Result.failure(throwable)
    }
