package com.example.polestarinfo.databases

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.example.polestarinfo.model.Score
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ScoreViewModel(application: Application) : AndroidViewModel(application){
    val readAllScore: LiveData<List<Score>>
    private val repository: ScoreRepository

    init {
        val scoreDao = ScoreDatabase.getDatabase(application).scoreDao()
        repository = ScoreRepository(scoreDao)
        readAllScore = repository.readALLScore
    }

    fun insertScore(score: Score) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.insertScore(score)
        }
    }

    fun deleteScore(name: String) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteScore(name)
        }
    }
}