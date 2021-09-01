package com.example.polestarinfo.databases

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.polestarinfo.model.Score

@Dao
interface ScoreDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertScore(score: Score)

    @Query("DELETE FROM score_table where name = :name")
    suspend fun deleteScore(name: String)

    @Query("SELECT * FROM score_table")
    fun getScores(): LiveData<List<Score>>
}