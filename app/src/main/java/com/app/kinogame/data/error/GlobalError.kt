package com.app.kinogame.data.error

import com.app.kinogame.data.api.ErrorResponse

sealed class GlobalError {
    data class ExceptionError(val e: Exception) : GlobalError()
    object UnauthorizedError : GlobalError()
    data class ApiError(val errorResponse: ErrorResponse) : GlobalError()
    object UnknownBackendError : GlobalError()
    object DatabaseError : GlobalError()

}