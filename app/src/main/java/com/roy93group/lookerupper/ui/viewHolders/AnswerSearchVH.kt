package com.roy93group.lookerupper.ui.viewHolders

import android.view.View
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.card.MaterialCardView
import com.roy93group.app.C
import com.roy93group.launcher.R
import com.roy93group.lookerupper.data.results.InstantAnswerResult
import com.roy93group.lookerupper.data.results.SearchResult
import com.roy93group.lookerupper.ui.adapter.InfoBoxAdapter

class AnswerSearchVH(
    itemView: View
) : SearchVH(itemView) {

    val card: MaterialCardView = itemView.findViewById<MaterialCardView>(R.id.card).apply {
        strokeColor = C.COLOR_0
    }
    val title: TextView = card.findViewById<TextView>(R.id.title).apply {
        setTextColor(C.COLOR_0)
    }
    val description: TextView = card.findViewById<TextView>(R.id.description).apply {
        setTextColor(C.COLOR_0)
    }

    private val infoBoxAdapter = InfoBoxAdapter()

    private val rvInfoBox: RecyclerView =
        itemView.findViewById<RecyclerView>(R.id.rvInfoBox).apply {
            layoutManager = LinearLayoutManager(
                /* context = */ context,
                /* orientation = */RecyclerView.VERTICAL,
                /* reverseLayout = */false
            )
            adapter = infoBoxAdapter
        }

    private val cvActionsContainer: MaterialCardView =
        itemView.findViewById<MaterialCardView>(R.id.cvActionsContainer).apply {
            setCardBackgroundColor(C.COLOR_0)
        }
    private val sourceAction: TextView = cvActionsContainer.findViewById(R.id.source)
    private val searchAction: TextView = cvActionsContainer.findViewById(R.id.search)

    override fun onBind(result: SearchResult) {
        result as InstantAnswerResult

        title.text = result.title
        description.text = result.description
        sourceAction.text =
            itemView.context.getString(R.string.read_more_at_source, result.sourceName)

        sourceAction.setOnClickListener(result::open)
        searchAction.setOnClickListener(result::search)

        if (result.infoTable == null) {
            rvInfoBox.isVisible = false
        } else {
            rvInfoBox.isVisible = true
            infoBoxAdapter.updateEntries(result.infoTable)
        }
    }
}
