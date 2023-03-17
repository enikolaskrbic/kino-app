package com.app.kinogame.data.api

import android.content.Context
import okhttp3.Interceptor
import okhttp3.Response
import okhttp3.ResponseBody.Companion.toResponseBody

class KinoInterceptor constructor(private val context: Context) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        var request = chain.request()
        request = request.newBuilder().addHeader("Content-Type", "application/json")
            .addHeader("Accept-Language", "en")
            .build()

        val response = chain.proceed(request)

        val responseBodyString = response.body?.string()

        return response.newBuilder().body(responseBodyString?.toResponseBody(response.body?.contentType())).build()
    }

}
