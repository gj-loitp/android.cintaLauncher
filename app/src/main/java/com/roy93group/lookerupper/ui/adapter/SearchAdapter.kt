package com.roy93group.lookerupper.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.roy93group.launcher.R
import com.roy93group.lookerupper.data.results.*
import com.roy93group.lookerupper.ui.viewHolders.*

/**
 * Updated by Loitp on 2022.12.18
 * Galaxy One company,
 * Vietnam
 * +840766040293
 * freuss47@gmail.com
 */
class SearchAdapter(
    val activity: AppCompatActivity,
    val recyclerView: RecyclerView,
    private val isOnCard: Boolean
) : RecyclerView.Adapter<SearchVH>() {

    companion object {
        const val RESULT_APP = 0
        const val RESULT_CONTACT = 2
        const val RESULT_ANSWER = 3
        const val RESULT_COMPACT = 4
    }

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
                itemView = LayoutInflater.from(parent.context)
                    .inflate(R.layout.view_smart_suggestion, parent, false),
                activity = activity
            )
            RESULT_COMPACT -> CompactSearchVH(
                itemView = LayoutInflater.from(parent.context)
                    .inflate(R.layout.view_search_result_compact, parent, false),
                activity = activity
            )
            RESULT_CONTACT -> ContactSearchVH(
                itemView = LayoutInflater.from(parent.context)
                    .inflate(R.layout.view_search_result_contact, parent, false)
            )
            RESULT_ANSWER -> AnswerSearchVH(
                itemView = LayoutInflater.from(parent.context)
                    .inflate(R.layout.view_search_result_answer, parent, false)
            )
            else -> throw Exception("Invalid view type")
        }
    }

    override fun onBindViewHolder(holder: SearchVH, i: Int) {
        holder.onBind(results[i])
    }

    override fun getItemCount(): Int = results.size

    fun update(results: List<SearchResult>) {
        this.results = results
        notifyDataSetChanged()
    }
}
