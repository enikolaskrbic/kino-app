package com.app.kinogame.data.room

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.app.kinogame.data.room.dao.KinoGameDao
import com.app.kinogame.data.room.dao.ResultsGameDao
import com.app.kinogame.data.room.dao.TalonDao
import com.app.kinogame.data.room.model.GameResultGroupRoom
import com.app.kinogame.data.room.model.KinoGameRoom
import com.app.kinogame.data.room.model.TalonRoom

@Database(
    entities = [
        KinoGameRoom::class,
        TalonRoom::class,
        GameResultGroupRoom::class,
    ],
    version = 3,
    exportSchema = false
)
@TypeConverters(Converters::class)
abstract class KinoDatabase : RoomDatabase() {
    abstract fun kinoGameDao(): KinoGameDao
    abstract fun talonDao(): TalonDao
    abstract fun resultDao(): ResultsGameDao
}
