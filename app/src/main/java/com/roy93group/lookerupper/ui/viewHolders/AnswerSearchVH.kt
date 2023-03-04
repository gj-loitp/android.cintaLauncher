package com.roy93group.lookerupper.ui.viewHolders

import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.roy93group.ext.getColorBackground
import com.roy93group.ext.getColorPrimary
import com.roy93group.launcher.R
import com.roy93group.lookerupper.data.results.InstantAnswerResult
import com.roy93group.lookerupper.data.results.SearchResult
import com.roy93group.lookerupper.ui.adapter.InfoBoxAdapter
import kotlinx.android.synthetic.main.view_search_result_answer.view.*

class AnswerSearchVH(
    val activity: AppCompatActivity,
    itemView: View
) : SearchVH(itemView) {

    private val infoBoxAdapter = InfoBoxAdapter()
    val colorPrimary = getColorPrimary()
    val colorBackground = getColorBackground()

    override fun onBind(
        result: SearchResult,
        isDisplayAppIcon: Boolean,
        isForceColorIcon: Boolean,
    ) {
        result as InstantAnswerResult

        itemView.title.apply {
            text = result.title
            setTextColor(colorPrimary)
        }
        itemView.description.apply {
            text = result.description
            setTextColor(colorPrimary)
        }
        itemView.card.setCardBackgroundColor(colorBackground)
        itemView.cvActionsContainer.setCardBackgroundColor(colorBackground)
        itemView.source.apply {
            text = itemView.context.getString(R.string.read_more_at_source, result.sourceName)
            setTextColor(colorPrimary)
//            setOnClickListener(result::open)
            setOnClickListener {
                result.open(activity = activity, view = it)
            }
        }
        itemView.vLine.setBackgroundColor(colorPrimary)
        itemView.search.apply {
            setOnClickListener(result::search)
            setTextColor(colorPrimary)
        }
        itemView.rvInfoBox.apply {
            layoutManager = LinearLayoutManager(
                /* context = */ context,
                /* orientation = */RecyclerView.VERTICAL,
                /* reverseLayout = */false
            )
            adapter = infoBoxAdapter
        }
        if (result.infoTable == null) {
            itemView.rvInfoBox.isVisible = false
        } else {
            itemView.rvInfoBox.isVisible = true
            infoBoxAdapter.updateEntries(result.infoTable)
        }
    }
}
