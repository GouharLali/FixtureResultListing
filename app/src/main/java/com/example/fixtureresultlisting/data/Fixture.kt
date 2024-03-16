package com.example.fixtureresultlisting.data

data class Fixture(
    val current: Int,
    val currentExternalId: String,
    val currentLegacyId: String,
    val match: List<Match>
)