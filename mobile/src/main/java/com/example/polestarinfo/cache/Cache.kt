package com.example.polestarinfo.cache

import com.example.polestarinfo.models.Score

object Cache {
    private var scores = mutableListOf<Score>()

    fun setCache(mScores: List<Score>){
        scores = mScores as MutableList<Score>
    }

    fun getCache() = scores
}