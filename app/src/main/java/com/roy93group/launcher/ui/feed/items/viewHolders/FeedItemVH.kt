package com.roy93group.launcher.ui.feed.items.viewHolders

import android.annotation.SuppressLint
import android.content.res.ColorStateList
import android.graphics.drawable.GradientDrawable
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.core.view.isVisible
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.roy93group.launcher.R
import com.roy93group.launcher.data.feed.items.FeedItem
import com.roy93group.launcher.data.feed.items.formatTimeAgo
import com.roy93group.launcher.providers.color.theme.ColorTheme
import com.roy93group.launcher.ui.feed.items.ActionsAdapter
import com.roy93group.launcher.ui.view.SwipeLayout
import com.roy93group.launcher.ui.view.recycler.DividerItemDecorator
import io.posidon.android.conveniencelib.units.dp
import io.posidon.android.conveniencelib.units.toPixels
import java.time.Instant

open class FeedItemVH(itemView: View) : FeedViewHolder(SwipeLayout(itemView)) {
    private val swipeLayout = this.itemView as SwipeLayout
    val container: View = itemView.findViewById(R.id.container)
    val source: TextView = itemView.findViewById(R.id.source)
    val separator: View = itemView.findViewById(R.id.separator)
    val time: TextView = itemView.findViewById(R.id.time)
    val title: TextView = itemView.findViewById(R.id.title)
    val description: TextView = itemView.findViewById(R.id.description)
    val icon: ImageView = itemView.findViewById(R.id.icon)
    private val actionsContainer: CardView = itemView.findViewById(R.id.actions_container)
    private val separatorDrawable = GradientDrawable().apply {
        shape = GradientDrawable.RECTANGLE
        setSize(1.dp.toPixels(itemView), 0)
    }

    @SuppressLint("ClickableViewAccessibility")
    private val actions: RecyclerView =
        actionsContainer.findViewById<RecyclerView>(R.id.actions_recycler).apply {
            layoutManager = LinearLayoutManager(context, RecyclerView.HORIZONTAL, false)
            addItemDecoration(
                DividerItemDecorator(
                    itemView.context,
                    DividerItemDecoration.HORIZONTAL,
                    separatorDrawable
                )
            )
            setOnTouchListener { v, _ ->
                v.parent.requestDisallowInterceptTouchEvent(true)
                false
            }
        }

    override fun onBind(
        item: FeedItem,
        color: Int
    ) {
        swipeLayout.reset()
        title.text = item.title
        applyIfNotNull(view = description, value = item.description, block = TextView::setText)
        applyIfNotNull(view = icon, value = item.sourceIcon, block = ImageView::setImageDrawable)
        applyIfNotNull(view = source, value = item.source, block = TextView::setText)
        itemView.setOnClickListener(item::onTap)
        icon.imageTintList = if (item.shouldTintIcon) ColorStateList.valueOf(color) else null
        if (item.actions.isEmpty()) {
            actionsContainer.isVisible = false
        } else {
            actionsContainer.isVisible = true
            val bg = ColorTheme.buttonColor
            actionsContainer.setCardBackgroundColor(bg)
            val fg = ColorTheme.titleColorForBG(bg)
            actions.adapter = ActionsAdapter(item.actions, fg)
            separatorDrawable.setColor(ColorTheme.hintColorForBG(bg))
        }
        if (item.instant == Instant.MAX) {
            time.isVisible = false
        } else {
            time.isVisible = true
            time.text = item.formatTimeAgo(itemView.resources)
        }
        swipeLayout.onSwipeAway = item::onDismiss
        swipeLayout.isSwipeAble = item.isDismissible
        source.setTextColor(color)
        title.setTextColor(ColorTheme.uiTitle)
        description.setTextColor(ColorTheme.uiDescription)
        time.setTextColor(ColorTheme.uiDescription)
        val bg = (ColorTheme.uiBG and 0xff000000.toInt()) or (ColorTheme.accentColor and 0x00ffffff)
        swipeLayout.setSwipeColor(bg)
        swipeLayout.setIconColor(ColorTheme.titleColorForBG(bg))
        separator.setBackgroundColor(ColorTheme.uiHint and 0x00ffffff or 0x24ffffff)
    }
}
