package com.app.kinogame.data.api

import com.google.gson.annotations.SerializedName

data class ErrorResponse(
    @SerializedName("error")
    val error: String?,
    @SerializedName("message")
    val message: String?,

) {
    fun isNull(): Boolean {
        return error == null && message == null
    }

}
