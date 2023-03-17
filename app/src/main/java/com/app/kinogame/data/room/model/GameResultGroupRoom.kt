package com.app.kinogame.data.room.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "game_result")

data class GameResultGroupRoom (
    @PrimaryKey
    var id: String = "",
    var drawId: Int = 0,
    var gameId: Int = 0,
    var drawTime: Long = 0,
    var orderInGroup: Int = 0,
    var numbers: List<NumberRoom>,
    var header: Boolean = false
)

data class NumberRoom(
    var number: Int,
    var bonus: Boolean
)