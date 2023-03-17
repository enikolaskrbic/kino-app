package com.app.kinogame.data.api.dto

import com.app.kinogame.data.api.callback.IDtoModelMapper
import com.app.kinogame.data.room.model.KinoGameRoom
import com.app.kinogame.data.room.model.WinningNumbersRoom
import java.util.Date

class GameResultResponse: IDtoModelMapper<GameResultResponse, List<KinoGameRoom>> {
    val content: ArrayList<KinoUpcomingGameResponse> = ArrayList()
    override fun map(value: GameResultResponse): List<KinoGameRoom> {
        return value.content.map { it.mapToRoom() }
    }
}