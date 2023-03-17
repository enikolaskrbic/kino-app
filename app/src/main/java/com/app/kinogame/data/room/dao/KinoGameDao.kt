package com.app.kinogame.data.room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.app.kinogame.data.room.model.KinoGameRoom
import com.app.kinogame.data.uimodel.KinoUpcomingUI
import kotlinx.coroutines.flow.Flow

@Dao
interface KinoGameDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun save(games: List<KinoGameRoom>)

    @Query("UPDATE kino_game SET currentTime = :currentTime where state = 'active' OR state = 'future'")
    suspend fun updateCurrentTime(currentTime: Long)

    @Query("SELECT drawId,drawTime,state,currentTime FROM kino_game WHERE (state = 'active' OR state = 'future') AND drawTime >= currentTime ORDER BY drawTime")
    fun listenKinoUpcoming(): Flow<List<KinoUpcomingUI>>

    @Query("SELECT drawId,drawTime,state,currentTime FROM kino_game WHERE state = 'active' ORDER BY drawTime desc LIMIT 1")
    fun listenActiveGame(): Flow<KinoUpcomingUI?>

    @Query("DELETE FROM kino_game WHERE drawId not in (:drawIds)")
    suspend fun deleteOldGames(drawIds: List<Int>): Int
}