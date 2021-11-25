package com.mudita.mail.relay.auth

import com.mudita.mail.service.authConfig.AuthConfig
import kotlinx.coroutines.channels.Channel

interface AuthRelay : Relay<AuthConfig, String>

interface AuthRequester<T> {

    suspend fun requestConfig(): AuthConfig

    suspend fun offerResult(): T
}

interface Supplier<InputType, ResultType> {

    suspend fun requestInput(): InputType

    suspend fun offerResult(result: ResultType)
}

interface Requester<I, O> {

    suspend fun requestResult(): O

    suspend fun offerInput(input: I)
}

interface Relay<InputType, ResultType> : Requester<InputType, ResultType>, Supplier<InputType, ResultType>

abstract class ChannelRelay<InputType, OutputType>(
    private val inputChannel: Channel<InputType> = Channel(capacity = 1),
    private val resultChannel: Channel<OutputType> = Channel(capacity = 1)
) : Relay<InputType, OutputType> {

    override suspend fun requestResult() = resultChannel.receive()

    override suspend fun offerInput(input: InputType) = inputChannel.send(input)

    override suspend fun requestInput() = inputChannel.receive()

    override suspend fun offerResult(result: OutputType) = resultChannel.send(result)
}

class AuthRelayImpl : ChannelRelay<AuthConfig, String>(), AuthRelay