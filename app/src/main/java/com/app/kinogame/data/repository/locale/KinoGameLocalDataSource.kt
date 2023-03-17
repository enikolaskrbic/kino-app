package com.app.kinogame.data.repository.locale

import com.app.kinogame.data.error.GlobalError
import com.app.kinogame.data.room.dao.KinoGameDao
import com.app.kinogame.data.room.model.KinoGameRoom
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

class KinoGameLocalDataSource @Inject constructor(private val kinoGameDao: KinoGameDao) {

    suspend fun saveUpcomingGames(games: List<KinoGameRoom>): State<Unit> {
        return try {
            SuccessState(kinoGameDao.save(games))
        } catch (e: Exception) {
            ErrorState(GlobalError.ExceptionError(e))
        }
    }

    suspend fun updateCurrentTime(currentTime: Long): State<Unit>{
        return try {
            SuccessState(kinoGameDao.updateCurrentTime(currentTime))
        } catch (e: Exception) {
            ErrorState(GlobalError.ExceptionError(e))
        }
    }

    fun getUpcomingGames(): Flow<State<List<KinoUpcomingUI>>> {
        return flow {
            emit(SuccessState(isLoading = true))
            emitAll(
                kinoGameDao.listenKinoUpcoming().transform {
                    emit(SuccessState<List<KinoUpcomingUI>>(it))
                }.catch { _ ->
                    emit(ErrorState<List<KinoUpcomingUI>>(GlobalError.DatabaseError))
                }
            )
        }.flowOn(Dispatchers.IO)
    }

    fun getActiveGame(): Flow<State<KinoUpcomingUI>> {
        return flow {
            emit(SuccessState(isLoading = true))
            emitAll(
                kinoGameDao.listenActiveGame().transform {
                    emit(SuccessState<KinoUpcomingUI>(it))
                }.catch { _ ->
                    emit(ErrorState<KinoUpcomingUI>(GlobalError.DatabaseError))
                }
            )
        }.flowOn(Dispatchers.IO)
    }

    suspend fun deleteOldGames(drawIds: List<Int>): State<Int>{
        return try {
            SuccessState(kinoGameDao.deleteOldGames(drawIds))
        } catch (e: Exception) {
            ErrorState(GlobalError.ExceptionError(e))
        }
    }


}