package com.example.polestarinfo.databases

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.polestarinfo.model.Score


@Database(entities = [Score::class], version = 1)
abstract class ScoreDatabase : RoomDatabase() {
    abstract fun scoreDao(): ScoreDao

    companion object{
        @Volatile
        private var INSTANCE: ScoreDatabase? = null

        fun getDatabase(context: Context):ScoreDatabase{
            val tempInstance = INSTANCE
            if(tempInstance != null){
                return tempInstance
            }
            synchronized(this){
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    ScoreDatabase::class.java,
                    "score_database"
                ).build()
                INSTANCE = instance
                return instance
            }
        }
    }
}