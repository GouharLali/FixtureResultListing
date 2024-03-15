package com.example.fixtureresultlisting.room

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.fixture.data.Match

@Entity(tableName = "matches")
data class MatchEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val match: Match
)

