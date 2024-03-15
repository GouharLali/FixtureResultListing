package com.example.fixtureresultlisting.room

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(entities = [MatchEntity::class], version = 1)
@TypeConverters(Converters::class)
abstract class MatchDatabase : RoomDatabase() {
    abstract fun matchDao(): MatchDao
}


