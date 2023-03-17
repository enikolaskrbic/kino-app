package com.app.kinogame.data.api.callback

interface IDtoModelMapper<T, K> {
    fun map(value: T): K
}
