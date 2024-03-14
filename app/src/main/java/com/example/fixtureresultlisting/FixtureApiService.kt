package com.example.fixtureresultlisting

import com.example.fixture.data.Fixture
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

interface FixtureApiService {
    @GET("fixtures")
    fun getFixtures(): Call<Fixture>

    interface FixtureCallback {
        fun onSuccess(fixtures: List<Fixture>)
        fun onFailure(error: String)
    }
}

object RetrofitClient {
    val BASE_URL = "https://3e0093ee-a397-4a87-bc26-ef97480c5292.mock.pstmn.io/"

    private val gson = GsonBuilder()
        .setLenient()
        .create()

    private val httpClient = OkHttpClient.Builder()
        .build()

    private val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create(gson))
        .client(httpClient)
        .build()

    private val fixtureApiService = retrofit.create(FixtureApiService::class.java)

    fun getFixtureService(): FixtureApiService {
        return fixtureApiService
    }
}








