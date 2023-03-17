package com.app.kinogame.data.repository

import com.app.kinogame.data.api.dto.KinoResultRequest
import com.app.kinogame.data.api.dto.KinoUpcomingRequest
import com.app.kinogame.data.repository.locale.KinoGameLocalDataSource
import com.app.kinogame.data.repository.remote.KinoGameRemoteDataSource
import com.app.kinogame.data.room.model.KinoGameRoom
import com.app.kinogame.data.uimodel.KinoUpcomingUI
import com.app.kinogame.data.wrapper.State
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class KinoGameRepositoryImpl @Inject constructor(
    private val localDataSource: KinoGameLocalDataSource,
    private val remoteDataSource: KinoGameRemoteDataSource
) : KinoGameRepository {

    override suspend fun fetchUpcoming(request: KinoUpcomingRequest): State<List<KinoGameRoom>> {
        return remoteDataSource.fetchUpcoming(request)
    }

    override suspend fun fetchResults(request: KinoResultRequest): State<List<KinoGameRoom>> {
        return remoteDataSource.fetchResults(request)
    }

    override suspend fun saveUpcomingGames(games: List<KinoGameRoom>): State<Unit> {
        return localDataSource.saveUpcomingGames(games)
    }

    override suspend fun updateCurrentTime(currentTime: Long): State<Unit> {
        return localDataSource.updateCurrentTime(currentTime)
    }

    override fun getUpcomingGames(): Flow<State<List<KinoUpcomingUI>>> {
        return localDataSource.getUpcomingGames()
    }

    override suspend fun deleteOldGames(newDrawIds: List<Int>): State<Int> {
        return localDataSource.deleteOldGames(newDrawIds)
    }

    override fun getActiveGame(): Flow<State<KinoUpcomingUI>> {
        return localDataSource.getActiveGame()
    }


}