package com.example.fixtureresultlisting

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.fixtureresultlisting.data.Fixture
import com.example.fixtureresultlisting.room.MatchEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {

    private lateinit var fixtureAdapter: FixtureAdapter
    private lateinit var recyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fixture_list_screen)

        // Initialize RecyclerView and Adapter
        recyclerView = findViewById(R.id.recyclerview)
        fixtureAdapter = FixtureAdapter(this)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = fixtureAdapter

        // Call function to fetch fixtures
        getFixtures()
    }

    private fun getFixtures() {
        val fixtureService = RetrofitClient.getFixtureService()
        fixtureService.getFixtures().enqueue(object : Callback<Fixture> {
            override fun onResponse(call: Call<Fixture>, response: Response<Fixture>) {
                if (response.isSuccessful) {
                    handleSuccessfulResponse(response.body())
                    // Fetch data from Room database after successful network call
                    fetchMatchesFromDatabase()
                } else {
                    handleErrorResponse("Failed to fetch fixtures: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<Fixture>, t: Throwable) {
                handleErrorResponse("Failed to fetch fixtures: ${t.message}")
            }
        })
    }

    // Coroutine function to fetch matches from the Room database
// Inside fetchMatchesFromDatabase function
    private fun fetchMatchesFromDatabase() {
        lifecycleScope.launch(Dispatchers.IO) {
            val matches = MyApp.database.matchDao().getAllMatches()
            if (matches.isNotEmpty()) {
                launch(Dispatchers.Main) {
                    fixtureAdapter.setData(matches.map { it.match })
                }
            } else {
                launch(Dispatchers.Main) {
                    showToast("No matches found in database")
                }
            }
        }
    }

    private fun handleSuccessfulResponse(fixture: Fixture?) {
        if (fixture != null) {
            val matches = fixture.match.map { MatchEntity(match = it) }
            lifecycleScope.launch(Dispatchers.IO) {
                // Access the matchDao
                val matchDao = MyApp.database.matchDao()

                // Clear existing data from the database
                matchDao.clearMatches()

                // Insert new matches into the database
                matchDao.insertMatches(matches)

                Log.d("MainActivity", "Data cleared and new data inserted into Room database successfully")
            }
        } else {
            showToast("No fixtures found")
        }
    }





    private fun handleErrorResponse(message: String) {
        showToast(message)
    }

    private fun showToast(message: String) {
        Toast.makeText(this@MainActivity, message, Toast.LENGTH_SHORT).show()
    }



}
