package com.example.fixtureresultlisting.room

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.example.fixtureresultlisting.data.Match

@Entity(tableName = "matches")
@TypeConverters(Converters::class)
data class MatchEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val match: Match
)

