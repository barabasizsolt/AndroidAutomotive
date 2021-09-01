package com.example.polestarinfo.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "score_table", primaryKeys = ["score", "name"])
data class Score(
    val score: Long,
    val name: String
)
