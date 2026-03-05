package com.gw.photoshare.domain.usecase

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext

abstract class CoroutineUseCase<INPUT, OUTPUT>(
    private val dispatcher: CoroutineDispatcher
) {
    suspend operator fun invoke(input: INPUT): Result<OUTPUT> {
        return withContext(dispatcher) {
            action(input)
        }
    }

    abstract suspend fun action(input: INPUT): Result<OUTPUT>
}

abstract class FlowUseCase<INPUT, OUTPUT> {
    abstract operator fun invoke(input: INPUT): Flow<Result<OUTPUT>>
}