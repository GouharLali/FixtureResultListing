package com.example.fixtureresultlisting.data

data class Contestant(
    val code: String,
    val crest: Crest,
    val externalId: String,
    val id: Int,
    val legacyId: String,
    val name: String,
    val officialName: String,
    val position: String,
    val primarySponsor: Any,
    val secondarySponsors: Any,
    val shortName: String,
    val taxonomy_term: Any
)