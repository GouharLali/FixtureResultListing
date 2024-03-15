package com.example.fixtureresultlisting.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface MatchDao {
    @Query("SELECT * FROM matches")
    suspend fun getAllMatches(): List<MatchEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMatches(matches: List<MatchEntity>)
}










