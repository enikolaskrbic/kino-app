package com.app.kinogame.data.repository.locale

import com.app.kinogame.data.error.GlobalError
import com.app.kinogame.data.room.dao.KinoGameDao
import com.app.kinogame.data.room.dao.ResultsGameDao
import com.app.kinogame.data.room.model.GameResultGroupRoom
import com.app.kinogame.data.room.model.KinoGameRoom
import com.app.kinogame.data.uimodel.GameResultUI
import com.app.kinogame.data.uimodel.KinoUpcomingUI
import com.app.kinogame.data.wrapper.ErrorState
import com.app.kinogame.data.wrapper.State
import com.app.kinogame.data.wrapper.SuccessState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.transform
import javax.inject.Inject

class GameResultsLocalDataSource @Inject constructor(private val resultsGameDao: ResultsGameDao) {

    suspend fun saveResults(games: List<GameResultGroupRoom>): State<Unit> {
        return try {
            SuccessState(resultsGameDao.save(games))
        } catch (e: Exception) {
            ErrorState(GlobalError.ExceptionError(e))
        }
    }

    fun getResults(): Flow<State<List<GameResultUI>>> {
        return flow {
            emit(SuccessState(isLoading = true))
            emitAll(
                resultsGameDao.listenResults().transform {
                    emit(SuccessState<List<GameResultUI>>(it))
                }.catch { _ ->
                    emit(ErrorState<List<GameResultUI>>(GlobalError.DatabaseError))
                }
            )
        }.flowOn(Dispatchers.IO)
    }


}