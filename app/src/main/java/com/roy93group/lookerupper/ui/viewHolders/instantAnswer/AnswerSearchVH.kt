package com.roy93group.lookerupper.ui.viewHolders.instantAnswer

import android.graphics.drawable.BitmapDrawable
import android.view.View
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.roy93group.cintalauncher.R
import com.roy93group.cintalauncher.providers.color.theme.ColorTheme
import com.roy93group.cintalauncher.ui.acrylicBlur
import com.roy93group.cintalauncher.ui.view.SeeThoughView
import com.roy93group.lookerupper.data.results.InstantAnswerResult
import com.roy93group.lookerupper.data.results.SearchResult
import com.roy93group.lookerupper.ui.viewHolders.SearchVH

class AnswerSearchVH(
    itemView: View
) : SearchVH(itemView) {

    val card: CardView = itemView.findViewById(R.id.card)
    val title: TextView = card.findViewById(R.id.title)
    val description: TextView = card.findViewById(R.id.description)

    private val infoBoxAdapter = InfoBoxAdapter()
    private val infoBox: RecyclerView = itemView.findViewById<RecyclerView>(R.id.info_box).apply {
        layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
        adapter = infoBoxAdapter
    }

    private val actionsContainer: CardView = itemView.findViewById(R.id.actions_container)
    private val sourceAction: TextView = actionsContainer.findViewById(R.id.source)
    private val searchAction: TextView = actionsContainer.findViewById(R.id.search)
    private val actionSeparator: View = actionsContainer.findViewById(R.id.separator)

    private val blurBG: SeeThoughView = itemView.findViewById(R.id.blur_bg)

    override fun onBind(result: SearchResult) {
        result as InstantAnswerResult

        blurBG.drawable = BitmapDrawable(itemView.resources, acrylicBlur?.smoothBlur)

        card.setCardBackgroundColor(ColorTheme.cardBG)
        title.setTextColor(ColorTheme.cardTitle)
        description.setTextColor(ColorTheme.cardDescription)

        title.text = result.title
        description.text = result.description
        sourceAction.text =
            itemView.context.getString(R.string.read_more_at_source, result.sourceName)

        val bg = ColorTheme.buttonColor
        val fg = ColorTheme.titleColorForBG(bg)
        val hint = ColorTheme.hintColorForBG(bg)
        actionsContainer.setCardBackgroundColor(bg)
        sourceAction.setTextColor(fg)
        searchAction.setTextColor(fg)
        actionSeparator.setBackgroundColor(hint)

        sourceAction.setOnClickListener(result::open)
        searchAction.setOnClickListener(result::search)

        if (result.infoTable == null) {
            infoBox.isVisible = false
        } else {
            infoBox.isVisible = true
            infoBoxAdapter.updateEntries(result.infoTable)
        }
    }
}
