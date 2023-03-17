package com.app.kinogame.data.repository

import com.app.kinogame.data.repository.locale.TalonLocalDataSource
import com.app.kinogame.data.room.model.TalonRoom
import com.app.kinogame.data.uimodel.TalonUI
import com.app.kinogame.data.wrapper.State
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class TalonRepositoryImpl @Inject constructor(private val talonLocalDataSource: TalonLocalDataSource): TalonRepository {
    override suspend fun save(numbers: List<TalonRoom>): State<Unit> {
        return talonLocalDataSource.save(numbers)
    }

    override suspend fun resetTalon(): State<Unit> {
        return talonLocalDataSource.resetTalon()
    }

    override suspend fun updateTalon(number: Int, selected: Boolean) {
        return talonLocalDataSource.updateTalon(number, selected)
    }

    override fun listenTalon(): Flow<State<List<TalonUI>>> {
        return talonLocalDataSource.listenTalon()
    }

    override suspend fun getSelected(): List<TalonRoom> {
        return talonLocalDataSource.getSelected()
    }
}