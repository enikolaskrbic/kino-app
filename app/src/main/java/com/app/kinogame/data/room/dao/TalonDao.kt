package com.app.kinogame.data.room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.app.kinogame.data.room.model.TalonRoom
import com.app.kinogame.data.uimodel.TalonUI
import kotlinx.coroutines.flow.Flow

@Dao
interface TalonDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun save(numbers: List<TalonRoom>)

    @Query("UPDATE talon SET selected = 0 where number != -1")
    suspend fun resetTalon()

    @Query("UPDATE talon SET selected = :selected, changedTime = :time where number = :number")
    suspend fun updateTalon(number: Int, selected: Boolean, time: Long)

    @Query("SELECT number,selected FROM talon")
    fun listenTalon(): Flow<List<TalonUI>>

    @Query("SELECT * FROM talon where selected = 1")
    suspend fun getSelected(): List<TalonRoom>

}