package com.example.fixtureresultlisting

import androidx.room.Room
import android.app.Application
import android.util.Log
import com.example.fixtureresultlisting.room.MatchDatabase

class MyApp : Application() {
    companion object {
        lateinit var database: MatchDatabase
            private set
    }

    override fun onCreate() {
        super.onCreate()
        database = Room.databaseBuilder(
            applicationContext,
            MatchDatabase::class.java, "match-database"
        ).build()
        Log.d("MyApp", "Room database initialized successfully")
    }
}


