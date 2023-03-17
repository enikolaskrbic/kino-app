package com.app.kinogame.data.api.callback

import com.app.kinogame.data.error.GlobalError
import com.app.kinogame.data.api.ErrorResponse
import com.app.kinogame.data.wrapper.ErrorState
import com.app.kinogame.data.wrapper.State
import com.app.kinogame.data.wrapper.SuccessState
import com.google.gson.Gson
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.Retrofit


class CoroutineAdapter<T : IDtoModelMapper<T, F>, F> constructor(private val response: Response<T>, private val retrofit: Retrofit) {
    operator fun invoke(): State<F> {
        if (response.isSuccessful) {
            val responseBody = response.body()
            return SuccessState(if (responseBody != null) response.body()?.map(responseBody) else null)
        }
        if (response.code() == 401) {
            return ErrorState(GlobalError.UnauthorizedError)
        }
        return try {
            val charStreamAsText = (response.errorBody() as ResponseBody).charStream().readText()

            val errorBody = response.errorBody() ?: return ErrorState(GlobalError.UnknownBackendError)
            val errorResponse = Gson().fromJson(charStreamAsText, ErrorResponse::class.java) as ErrorResponse

            ErrorState(GlobalError.ApiError(errorResponse))
        } catch (e: Exception) {
            ErrorState(GlobalError.ExceptionError(e))
        }
    }
}
