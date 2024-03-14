package com.example.fixtureresultlisting

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.fixture.data.Match
import com.example.fixtureresultlisting.databinding.FixtureCardItemLayoutBinding
import com.example.fixtureresultlisting.databinding.FixtureHeaderItemLayoutBinding

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
                val binding = FixtureHeaderItemLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                HeaderViewHolder(binding)
            }

            VIEW_TYPE_ITEM -> {
                val binding = FixtureCardItemLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                ItemViewHolder(binding)
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
    inner class HeaderViewHolder(private val binding: FixtureHeaderItemLayoutBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind() {
            Log.d(TAG, "HeaderViewHolder bind()")
            binding.textView.text = itemView.context.getString(R.string.fixture_text)
        }
    }


    // ViewHolder for the item view
    inner class ItemViewHolder(private val binding: FixtureCardItemLayoutBinding) : RecyclerView.ViewHolder(binding.root) {
        init {
            binding.matchcentre.setOnClickListener {
                val context = binding.root.context
                val intent = Intent(context, NoMatchesFoundActivity::class.java) // Replace with your activity
                context.startActivity(intent)
            }
        }
        fun bind(match: Match) {
            val matchInfo = match.matchInfo
            val liveData = match.liveData

            // Bind data to views
            binding.dateYear.text = matchInfo.date.toString()
            binding.stadiumName.text = matchInfo.venue.longName
            binding.matchStatus.text = liveData.matchStatus
            binding.homeTeam.text = matchInfo.contestant.firstOrNull()?.name ?: ""

            val total = liveData.scoreEntries?.total // Add null check here
            binding.homePenaltyScore.text = total?.home_score?.toString() ?: ""
            binding.awayPenaltyScore.text = total?.away_score?.toString() ?: ""

            binding.homeScore.text = liveData.home_score?.toString() ?: ""
            binding.awayTeam.text = matchInfo.contestant.getOrNull(1)?.name ?: ""
            binding.awayScore.text = liveData.away_score?.toString() ?: ""

            // Load image using Glide
            Glide.with(binding.root.context)
                .load(matchInfo.contestant.firstOrNull()?.crest?.uri1x)
                .into(binding.logoImage)
        }

    }
}




