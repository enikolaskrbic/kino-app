package com.app.kinogame.data.repository

import com.app.kinogame.data.repository.locale.GameResultsLocalDataSource
import com.app.kinogame.data.room.model.GameResultGroupRoom
import com.app.kinogame.data.uimodel.GameResultUI
import com.app.kinogame.data.wrapper.State
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GameResultRepositoryImpl @Inject constructor(private val gameResultsLocalDataSource: GameResultsLocalDataSource): GameResultRepository {
    override suspend fun saveResults(games: List<GameResultGroupRoom>): State<Unit> {
        return gameResultsLocalDataSource.saveResults(games)
    }

    override fun getResults(): Flow<State<List<GameResultUI>>> {
        return gameResultsLocalDataSource.getResults()
    }
}