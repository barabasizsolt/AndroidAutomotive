package com.example.polestarinfo.models

import androidx.room.Entity

@Entity(tableName = "score_table", primaryKeys = ["score", "name"])
data class Score(
    val score: Long,
    val name: String,
    val primalityScore: Long,
    val factorialScore: Long,
    val sortingScore: Long,
    val matrixScore: Long,
)
