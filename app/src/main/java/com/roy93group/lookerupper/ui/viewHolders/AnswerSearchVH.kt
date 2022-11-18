package com.roy93group.lookerupper.ui.viewHolders

import android.view.View
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.roy93group.launcher.R
import com.roy93group.lookerupper.data.results.InstantAnswerResult
import com.roy93group.lookerupper.data.results.SearchResult
import com.roy93group.lookerupper.ui.adapter.InfoBoxAdapter

class AnswerSearchVH(
    itemView: View
) : SearchVH(itemView) {

    val card: CardView = itemView.findViewById(R.id.card)
    val title: TextView = card.findViewById(R.id.title)
    val description: TextView = card.findViewById(R.id.description)

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

    private val cvActionsContainer: CardView = itemView.findViewById(R.id.cvActionsContainer)
    private val sourceAction: TextView = cvActionsContainer.findViewById(R.id.source)
    private val searchAction: TextView = cvActionsContainer.findViewById(R.id.search)
    private val actionSeparator: View = cvActionsContainer.findViewById(R.id.separator)

    override fun onBind(result: SearchResult) {
        result as InstantAnswerResult

//        card.setCardBackgroundColor(Color.RED)
//        title.setTextColor(Color.RED)
//        description.setTextColor(Color.RED)

        title.text = result.title
        description.text = result.description
        sourceAction.text =
            itemView.context.getString(R.string.read_more_at_source, result.sourceName)

//        val bg = Color.RED
//        val fg = ColorTheme.titleColorForBG(bg)
//        val hint = ColorTheme.hintColorForBG(bg)
//        cvActionsContainer.setCardBackgroundColor(bg)
//        sourceAction.setTextColor(fg)
//        searchAction.setTextColor(fg)
//        actionSeparator.setBackgroundColor(hint)

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
