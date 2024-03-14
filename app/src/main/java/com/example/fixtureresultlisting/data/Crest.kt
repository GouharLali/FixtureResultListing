package com.example.fixture.data

import com.google.gson.annotations.SerializedName

data class Crest(
    @SerializedName("uri_1x") val uri1x: String,
    @SerializedName("uri_1x-dark") val uri1xDark: String,
    @SerializedName("uri_2x") val uri2x: String,
    @SerializedName("uri_2x-dark") val uri2xDark: String,
    @SerializedName("uri_3x") val uri3x: String,
    @SerializedName("uri_3x-dark") val uri3xDark: String
)
