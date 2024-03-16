package com.example.fixtureresultlisting

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.fixtureresultlisting.data.Match
import com.example.fixtureresultlisting.databinding.EmptyStateBinding
import com.example.fixtureresultlisting.databinding.FixtureCardItemLayoutBinding
import com.example.fixtureresultlisting.databinding.FixtureHeaderItemLayoutBinding
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class FixtureAdapter(
    private val context: Context,
    private val matches: MutableList<Match> = mutableListOf()
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    companion object {
        private const val TAG = "FixtureAdapter"
        private const val VIEW_TYPE_HEADER = 0
        private const val VIEW_TYPE_ITEM = 1
        private const val VIEW_TYPE_EMPTY_MESSAGE = 2
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
        return if (matches.isEmpty()) {
            1
        } else {
            matches.size + 1
        }
    }

    override fun getItemViewType(position: Int): Int {
        return if (position == 0 && matches.isEmpty()) {
            VIEW_TYPE_EMPTY_MESSAGE
        } else if (position == 0) {
            VIEW_TYPE_HEADER
        } else {
            VIEW_TYPE_ITEM
        }
    }

    // Create new view (invoked by the layout manager)
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        Log.d(TAG, "onCreateViewHolder()")
        return when (viewType) {
            VIEW_TYPE_EMPTY_MESSAGE -> {
                val binding = EmptyStateBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
                EmptyViewHolder(binding)
            }

            VIEW_TYPE_HEADER -> {
                val binding = FixtureHeaderItemLayoutBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
                HeaderViewHolder(binding)
            }

            VIEW_TYPE_ITEM -> {
                val binding = FixtureCardItemLayoutBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
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
            // no data should
        }
    }

    // ViewHolder for the header view
    inner class EmptyViewHolder(private val binding: EmptyStateBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind() {
            binding.nomatches.text = itemView.context.getString(R.string.no_matches_found)
        }
    }

    // ViewHolder for the header view
    inner class HeaderViewHolder(private val binding: FixtureHeaderItemLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind() {
            Log.d(TAG, "HeaderViewHolder bind()")
            binding.textView.text = itemView.context.getString(R.string.fixture_text)
        }
    }


    // ViewHolder for the item view
    inner class ItemViewHolder(private val binding: FixtureCardItemLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(match: Match) {
            val matchInfo = match.matchInfo
            val liveData = match.liveData

            // Convert Unix timestamp to date
            val timeStamp = matchInfo.date
            val date = Date(timeStamp * 1000L) // Convert seconds to milliseconds
            val sdf = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
            val formattedDate = sdf.format(date)

            // Bind data to views
            binding.dateYear.text = formattedDate

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



//
//if (liveData.matchStatus == "Played") {
//    "Print hello"
//}
//
//if (liveData.matchStatus == "Fixture") {
//    binding.report.visibility = View.GONE
//}