package com.app.kinogame.data.uimodel

data class GameResultUI(
    var id: String,
    var drawId: Int = 0,
    var gameId: Int = 0,
    var drawTime: Long = 0,
    var orderInGroup: Int = 0,
    var numbers: List<GameResultNumber>,
    var header: Boolean = false
)

data class GameResultNumber(
    var number: Int,
    var bonus: Boolean
)