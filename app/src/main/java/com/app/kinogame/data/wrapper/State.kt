package com.app.kinogame.data.wrapper

import com.app.kinogame.data.error.GlobalError


sealed class State<T>
data class SuccessState<T>(val data: T? = null, val isLoading: Boolean = false) : State<T>()
data class ErrorState<T>(val error: GlobalError) : State<T>()
