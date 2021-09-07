package com.example.polestarinfo.databases

import androidx.lifecycle.LiveData
import com.example.polestarinfo.models.Score

class ScoreRepository(private val scoreDao: ScoreDao) {
    val readALLScore: LiveData<List<Score>> = scoreDao.getScores()

    suspend fun insertScore(score: Score){
        scoreDao.insertScore(score)
    }

    suspend fun deleteScore(name: String){
        scoreDao.deleteScore(name)
    }
}