package com.example.fixtureresultlisting

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.fixture.data.Match

class FixtureAdapter(
    private val context: Context,
    private val matches: MutableList<Match> = mutableListOf()
): RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    companion object {
        private const val TAG = "FixtureAdapter"
        private const val VIEW_TYPE_HEADER = 0
        private const val VIEW_TYPE_ITEM = 1
    }

    // Function to set data to the adapter
    fun setData(matches: List<Match>) {
        Log.d(TAG, "setData()")
        // Clear existing data and add new data
        this.matches.clear()
        this.matches.addAll(matches)
        notifyDataSetChanged()
    }

    // Return the total number of items in the list
    override fun getItemCount(): Int {
        return matches.size + 1
    }

    override fun getItemViewType(position: Int): Int {
        return if (position == 0) {
            VIEW_TYPE_HEADER
        } else {
            VIEW_TYPE_ITEM
        }
    }

    // Create new view (invoked by the layout manager)
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        Log.d(TAG, "onCreateViewHolder()")
        return when (viewType) {
            VIEW_TYPE_HEADER -> {
                val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.fixture_header_item_layout, parent, false)
                HeaderViewHolder(view)
            }

            VIEW_TYPE_ITEM -> {
                val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.fixture_card_item_layout, parent, false)
                ItemViewHolder(view)
            }

            else -> throw IllegalArgumentException("Invalid view type")
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        Log.d(TAG, "onBindViewHolder() position: $position")
        when (holder) {
            is HeaderViewHolder -> {
                holder.bind()
            }

            is ItemViewHolder -> {
                val match = matches[position - 1] // Adjust position for header
                holder.bind(match)
            }
        }
    }

    // ViewHolder for the header view
    inner class HeaderViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind() {
            Log.d(TAG, "HeaderViewHolder bind()")
            val headerTextView = itemView.findViewById<TextView>(R.id.textView)
            headerTextView.text = "Fixture"
        }
    }

    // ViewHolder for the item view
    inner class ItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(match: Match) {
            Log.d(TAG, "ItemViewHolder bind()")
            val matchInfo = match.matchInfo // Assuming there's only one match in the list
            val liveData = match.liveData

            // Bind data to views
            itemView.findViewById<TextView>(R.id.date_year).text = matchInfo.date.toString()
            itemView.findViewById<TextView>(R.id.stadium_name).text = matchInfo.venue.longName
            itemView.findViewById<TextView>(R.id.match_status).text = liveData.matchStatus
            itemView.findViewById<TextView>(R.id.home_team).text = matchInfo.contestant.firstOrNull()?.name ?: ""

            val scoreEntries = liveData.scoreEntries
            itemView.findViewById<TextView>(R.id.home_penalty_score).text = scoreEntries.total.home_score.toString() ?: ""
            itemView.findViewById<TextView>(R.id.away_penalty_score).text = scoreEntries.total.away_score.toString() ?: ""

            itemView.findViewById<TextView>(R.id.home_score).text = liveData.home_score.toString() ?: ""
            itemView.findViewById<TextView>(R.id.away_team).text = matchInfo.contestant.getOrNull(1)?.name ?: ""
            itemView.findViewById<TextView>(R.id.away_score).text = liveData.away_score.toString() ?: ""


            // Load image using Glide
            Glide.with(itemView.context)
                .load(matchInfo.contestant.firstOrNull()?.crest?.uri1x)
                .into(itemView.findViewById(R.id.logo_image))
        }

    }
}





