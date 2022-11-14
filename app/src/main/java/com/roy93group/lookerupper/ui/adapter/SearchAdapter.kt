package com.roy93group.lookerupper.ui.adapter

import android.annotation.SuppressLint
import android.app.Activity
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.roy93group.cintalauncher.R
import com.roy93group.lookerupper.data.results.*
import com.roy93group.lookerupper.ui.viewHolders.AppSearchVH
import com.roy93group.lookerupper.ui.viewHolders.CompactSearchVH
import com.roy93group.lookerupper.ui.viewHolders.ContactSearchVH
import com.roy93group.lookerupper.ui.viewHolders.SearchVH
import com.roy93group.lookerupper.ui.viewHolders.AnswerSearchVH

class SearchAdapter(
    val activity: Activity,
    val recyclerView: RecyclerView,
    private val isOnCard: Boolean
) : RecyclerView.Adapter<SearchVH>() {

    private var results = emptyList<SearchResult>()

    override fun getItemViewType(i: Int): Int {
        return when (results[i]) {
            is AppResult -> RESULT_APP
            is CompactResult -> RESULT_COMPACT
            is ContactResult -> RESULT_CONTACT
            is InstantAnswerResult -> RESULT_ANSWER
            else -> throw Exception("Invalid search result")
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchVH {
        return when (viewType) {
            RESULT_APP -> AppSearchVH(
                LayoutInflater.from(parent.context)
                    .inflate(R.layout.smart_suggestion, parent, false), activity
            )
            RESULT_COMPACT -> CompactSearchVH(
                LayoutInflater.from(parent.context)
                    .inflate(R.layout.search_result_compact, parent, false), activity, isOnCard
            )
            RESULT_CONTACT -> ContactSearchVH(
                LayoutInflater.from(parent.context)
                    .inflate(R.layout.search_result_contact, parent, false), isOnCard
            )
            RESULT_ANSWER -> AnswerSearchVH(
                LayoutInflater.from(parent.context)
                    .inflate(R.layout.search_result_answer, parent, false)
            )
            else -> throw Exception("Invalid view type")
        }
    }

    override fun onBindViewHolder(holder: SearchVH, i: Int) {
        holder.onBind(results[i])
    }

    override fun getItemCount(): Int = results.size

    @SuppressLint("NotifyDataSetChanged")
    fun update(results: List<SearchResult>) {
        this.results = results
        notifyDataSetChanged()
    }

    companion object {
        const val RESULT_APP = 0
        const val RESULT_CONTACT = 2
        const val RESULT_ANSWER = 3
        const val RESULT_COMPACT = 4
    }
}
