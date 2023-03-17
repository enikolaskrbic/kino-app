package com.app.kinogame.data.room

import androidx.room.TypeConverter
import com.app.kinogame.data.room.model.NumberRoom
import com.app.kinogame.data.uimodel.GameResultNumber
import com.google.gson.Gson
import java.util.Date

class Converters {

    @TypeConverter
    fun fromTimestamp(value: Long?): Date? {
        return if (value == null) null else Date(value)
    }

    @TypeConverter
    fun dateToTimestamp(date: Date?): Long? {
        return date?.time
    }

    @TypeConverter
    fun intToJson(value: List<Int>): String? {
        if (value == null) return null
        return Gson().toJson(value)
    }

    @TypeConverter
    fun jsonToInt(value: String?): List<Int>? {
        if (value == null) return null
        val objects = Gson().fromJson(value, Array<Int>::class.java) as Array<Int>
        return objects.toList()
    }

    @TypeConverter
    fun numbersToJson(value: List<NumberRoom>): String? {
        if (value == null) return null
        return Gson().toJson(value)
    }

    @TypeConverter
    fun jsonToNumbers(value: String?): List<NumberRoom>? {
        if (value == null) return null
        val objects = Gson().fromJson(value, Array<NumberRoom>::class.java) as Array<NumberRoom>
        return objects.toList()
    }

    @TypeConverter
    fun jsonToGameResultNumber(value: String?): List<GameResultNumber>? {
        if (value == null) return null
        val objects = Gson().fromJson(value, Array<GameResultNumber>::class.java) as Array<GameResultNumber>
        return objects.toList()
    }

}
