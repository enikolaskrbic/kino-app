package com.app.kinogame.data.wrapper

import com.app.kinogame.data.error.GlobalError


data class StateHandler<out T>(open val state: State<out T>) {

    companion object {
        fun <T> success(data: T?): StateHandler<T> {
            return StateHandler(SuccessState(data))
        }

        fun <T> loading(): StateHandler<T> {
            return StateHandler(SuccessState(isLoading = true))
        }

        fun <T> error(error: GlobalError): StateHandler<T> {
            return StateHandler(ErrorState(error))
        }
    }

    // Allow external read but not write
    var hasBeenHandled = false
        private set

    fun getContentIfNotHandled(): StateHandler<T?>? {
        return if (hasBeenHandled) {
            null
        } else {
            hasBeenHandled = true
            StateHandler(state)
        }
    }

    fun peekContent(): StateHandler<T?> = StateHandler(state)
}
