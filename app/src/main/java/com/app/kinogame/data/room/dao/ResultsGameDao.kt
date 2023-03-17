package com.app.kinogame.data.room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.app.kinogame.data.room.model.GameResultGroupRoom
import com.app.kinogame.data.room.model.KinoGameRoom
import com.app.kinogame.data.uimodel.GameResultUI
import com.app.kinogame.data.uimodel.KinoUpcomingUI
import kotlinx.coroutines.flow.Flow

@Dao
interface ResultsGameDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun save(games: List<GameResultGroupRoom>)

    @Query("SELECT * FROM game_result ORDER BY drawTime desc, orderInGroup asc")
    fun listenResults(): Flow<List<GameResultUI>>

}