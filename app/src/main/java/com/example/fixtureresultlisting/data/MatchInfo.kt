package com.example.fixture.data

data class MatchInfo(
    val contestant: List<Contestant>,
    val date: Int,
    val description: String,
    val externalId: String,
    val externalLastUpdated: Int,
    val externalTicketEventId: Any,
    val firstCallToAction: FirstCallToAction,
    val id: Int,
    val lastUpdated: Int,
    val legacyId: String,
    val matchPreview: MatchPreview,
    val matchReport: MatchReport,
    val relatedTags: List<Any>,
    val secondCallToAction: Any,
    val tbc: Boolean,
    val venue: Venue
)