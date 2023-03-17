package com.app.kinogame.data.repository

import com.app.kinogame.data.api.dto.KinoResultRequest
import com.app.kinogame.data.api.dto.KinoUpcomingRequest
import com.app.kinogame.data.room.model.KinoGameRoom
import com.app.kinogame.data.uimodel.KinoUpcomingUI
import com.app.kinogame.data.wrapper.State
import kotlinx.coroutines.flow.Flow

interface KinoGameRepository {
    suspend fun fetchUpcoming(request: KinoUpcomingRequest): State<List<KinoGameRoom>>
    suspend fun fetchResults(request: KinoResultRequest): State<List<KinoGameRoom>>
    suspend fun saveUpcomingGames(games: List<KinoGameRoom>): State<Unit>
    suspend fun updateCurrentTime(currentTime: Long): State<Unit>
    fun getUpcomingGames(): Flow<State<List<KinoUpcomingUI>>>
    suspend fun deleteOldGames(newDrawIds: List<Int>): State<Int>
    fun getActiveGame(): Flow<State<KinoUpcomingUI>>
}