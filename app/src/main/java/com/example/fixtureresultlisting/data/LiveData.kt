package com.example.fixture.data

data class LiveData(
    val away_score: Int,
    val home_score: Int,
    val isLive: Boolean,
    val matchStatus: String,
    val match_length_min: Int,
    val match_winner: String,
    val scoreEntries: ScoreEntries
)