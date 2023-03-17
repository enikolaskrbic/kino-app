package com.app.kinogame.data.api

import com.app.kinogame.data.api.dto.GameResultResponse
import com.app.kinogame.data.api.dto.KinoUpcomingResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path


interface KinoApi {
    @GET("draws/v3.0/{gameId}/upcoming/{count}")
    suspend fun getUpcomingGames(
        @Path("gameId") gameId: Int,
        @Path("count") count: Int,
    ): Response<KinoUpcomingResponse>

    @GET("draws/v3.0/{gameId}/draw-date/{fromDate}/{toDate}")
    suspend fun getResultsGames(
        @Path("gameId") gameId: Int,
        @Path("fromDate") fromDate: String,
        @Path("toDate") toDate: String
    ): Response<GameResultResponse>
}
