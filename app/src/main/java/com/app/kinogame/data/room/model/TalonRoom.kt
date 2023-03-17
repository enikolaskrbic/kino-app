package com.app.kinogame.data.room.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "talon")
data class TalonRoom (
    @PrimaryKey
    var number: Int,
    var selected: Boolean,
    var changedTime: Long = 0
)