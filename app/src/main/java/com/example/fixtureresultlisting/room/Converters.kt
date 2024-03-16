package com.example.fixtureresultlisting.room

import androidx.room.TypeConverter
import com.example.fixtureresultlisting.data.Match
import com.google.gson.Gson

object Converters {
    @TypeConverter
    @JvmStatic
    fun fromMatch(match: Match): String {
        return Gson().toJson(match)
    }

    @TypeConverter
    @JvmStatic
    fun toMatch(json: String): Match {
        return Gson().fromJson(json, Match::class.java)
    }

}
