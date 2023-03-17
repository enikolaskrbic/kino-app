package com.app.kinogame.data.repository

import com.app.kinogame.data.room.model.GameResultGroupRoom
import com.app.kinogame.data.uimodel.GameResultUI
import com.app.kinogame.data.wrapper.State
import kotlinx.coroutines.flow.Flow

interface GameResultRepository {
    suspend fun saveResults(games: List<GameResultGroupRoom>): State<Unit>
    fun getResults(): Flow<State<List<GameResultUI>>>
}