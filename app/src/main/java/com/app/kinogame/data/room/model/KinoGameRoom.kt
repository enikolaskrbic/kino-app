package com.app.kinogame.data.room.model

import androidx.room.Embedded
import androidx.room.Entity

@Entity(tableName = "kino_game", primaryKeys = ["gameId", "drawId", "drawTime"])
data class KinoGameRoom(
    var gameId: Int = 0,
    var drawId: Int = 0,
    var drawTime: Long = 0,
    var state: String = "",
    var drawBreak: Int = 0,
    var visualDraw: Int = 0,
    var currentTime: Long = 0,
    @Embedded("wn")
    var winningNumbers: WinningNumbersRoom? = null
)

class WinningNumbersRoom(
    var list: List<Int> = emptyList(),
    var bonus: List<Int> = emptyList()
)