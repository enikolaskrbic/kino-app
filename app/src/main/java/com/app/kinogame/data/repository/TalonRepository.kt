package com.app.kinogame.data.repository

import com.app.kinogame.data.api.dto.KinoUpcomingRequest
import com.app.kinogame.data.room.model.KinoGameRoom
import com.app.kinogame.data.room.model.TalonRoom
import com.app.kinogame.data.uimodel.KinoUpcomingUI
import com.app.kinogame.data.uimodel.TalonUI
import com.app.kinogame.data.wrapper.State
import kotlinx.coroutines.flow.Flow

interface TalonRepository {
    suspend fun save(numbers: List<TalonRoom>): State<Unit>
    suspend fun resetTalon(): State<Unit>
    suspend fun updateTalon(number: Int, selected: Boolean)
    fun listenTalon(): Flow<State<List<TalonUI>>>
    suspend fun getSelected(): List<TalonRoom>
}