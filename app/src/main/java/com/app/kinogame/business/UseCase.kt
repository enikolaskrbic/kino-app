package com.app.kinogame.business

import com.app.kinogame.data.wrapper.State
import kotlinx.coroutines.flow.Flow

abstract class UseCase<in Value, Type> {

    abstract suspend fun invoke(value: Value): Type
}

abstract class UseCaseAsync<in Value, Type> {

    abstract suspend fun invoke(value: Value): State<Type>
}

abstract class UseCaseFlow<in Value, Type> {
    abstract suspend fun invoke(value: Value): Flow<Type>
}
