package com.app.kinogame.data.uimodel

data class KinoUpcomingUI(
    val drawId: Int,
    val drawTime: Long,
    var state: String,
    val currentTime: Long
){
    fun diff(): Long {
        if(currentTime > drawTime) return 0
        return drawTime - currentTime
    }

    fun isSoonStart(): Boolean {
        return drawTime - currentTime < 30000
    }
}
