package com.app.kinogame.data.api.dto

import com.app.kinogame.data.api.callback.IDtoModelMapper
import com.app.kinogame.data.room.model.KinoGameRoom
import com.app.kinogame.data.room.model.WinningNumbersRoom
import java.util.Date

class KinoUpcomingResponse: ArrayList<KinoUpcomingGameResponse>(),IDtoModelMapper<KinoUpcomingResponse, List<KinoGameRoom>> {

    override fun map(value: KinoUpcomingResponse): List<KinoGameRoom> {
        return this.map { it.mapToRoom() }
    }
}

class KinoUpcomingGameResponse {
    val gameId: Int? = null
    val drawId: Int? = null
    val drawTime: Long? = null
    val status: String? = null
    val drawBreak: Int? = null
    val visualDraw: Int? = null
    val winningNumbers: WinningNumbersResponse? = null
}

class WinningNumbersResponse {
    val list: List<Int> = emptyList()
    val bonus: List<Int> = emptyList()

}


fun KinoUpcomingGameResponse.mapToRoom(): KinoGameRoom {
    return KinoGameRoom(
        gameId = gameId?:0,
        drawId = drawId?:0,
        drawTime = drawTime?:0,
        state = status?:"",
        drawBreak = drawBreak?:0,
        visualDraw = visualDraw?:0,
        currentTime = Date().time,
        winningNumbers = winningNumbers?.mapToRoom()
    )
}

fun WinningNumbersResponse.mapToRoom(): WinningNumbersRoom {
    return WinningNumbersRoom(list, bonus)
}