package com.app.kinogame.data.repository.locale

import com.app.kinogame.data.error.GlobalError
import com.app.kinogame.data.room.dao.TalonDao
import com.app.kinogame.data.room.model.TalonRoom
import com.app.kinogame.data.uimodel.TalonUI
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
import java.util.Date
import javax.inject.Inject

class TalonLocalDataSource @Inject constructor(private val talonDao: TalonDao) {

    suspend fun save(numbers: List<TalonRoom>): State<Unit> {
        return try {
            SuccessState(talonDao.save(numbers))
        } catch (e: Exception) {
            ErrorState(GlobalError.ExceptionError(e))
        }
    }

    suspend fun resetTalon(): State<Unit>{
        return try {
            SuccessState(talonDao.resetTalon())
        } catch (e: Exception) {
            ErrorState(GlobalError.ExceptionError(e))
        }
    }

    suspend fun updateTalon(number: Int, selected: Boolean) {
        try {
            SuccessState(talonDao.updateTalon(number, selected, if(selected) Date().time else 0))
        } catch (e: Exception) {
            ErrorState(GlobalError.ExceptionError(e))
        }
    }

    fun listenTalon(): Flow<State<List<TalonUI>>> {
        return flow {
            emit(SuccessState(isLoading = true))
            emitAll(
                talonDao.listenTalon().transform {
                    emit(SuccessState<List<TalonUI>>(it))
                }.catch { _ ->
                    emit(ErrorState<List<TalonUI>>(GlobalError.DatabaseError))
                }
            )
        }.flowOn(Dispatchers.IO)
    }


    suspend fun getSelected(): List<TalonRoom> {
        return talonDao.getSelected()
    }

}