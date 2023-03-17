package com.app.kinogame.data.repository.remote

import com.app.kinogame.data.api.KinoApi
import com.app.kinogame.data.api.callback.CoroutineAdapter
import com.app.kinogame.data.api.dto.KinoResultRequest
import com.app.kinogame.data.api.dto.KinoUpcomingRequest
import com.app.kinogame.data.error.GlobalError
import com.app.kinogame.data.room.model.KinoGameRoom
import com.app.kinogame.data.wrapper.ErrorState
import com.app.kinogame.data.wrapper.State
import retrofit2.Retrofit
import javax.inject.Inject

class KinoGameRemoteDataSource @Inject constructor(private val kinoApi: KinoApi, private val retrofit: Retrofit){

    suspend fun fetchUpcoming(request: KinoUpcomingRequest): State<List<KinoGameRoom>> {
        return try {
            CoroutineAdapter(kinoApi.getUpcomingGames(request.gameId, request.count), retrofit)()
        } catch (e: Exception) {
            ErrorState(GlobalError.ExceptionError(e))
        }
    }

    suspend fun fetchResults(request: KinoResultRequest): State<List<KinoGameRoom>> {
        return try {
            CoroutineAdapter(kinoApi.getResultsGames(request.gameId, request.fromDate, request.toDate), retrofit)()
        } catch (e: Exception) {
            ErrorState(GlobalError.ExceptionError(e))
        }
    }
}