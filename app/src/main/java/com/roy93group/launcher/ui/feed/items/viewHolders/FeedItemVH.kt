package com.roy93group.launcher.ui.feed.items.viewHolders

import android.annotation.SuppressLint
import android.graphics.drawable.GradientDrawable
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.core.view.isVisible
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.roy93group.app.C
import com.roy93group.launcher.R
import com.roy93group.launcher.data.feed.items.FeedItem
import com.roy93group.launcher.data.feed.items.formatTimeAgo
import com.roy93group.launcher.ui.feed.ActionsAdapter
import com.roy93group.launcher.ui.view.SwipeLayout
import com.roy93group.launcher.ui.view.recycler.DividerItemDecorator
import io.posidon.android.conveniencelib.units.dp
import io.posidon.android.conveniencelib.units.toPixels
import java.time.Instant

/**
 * Updated by Loitp on 2022.12.16
 * Galaxy One company,
 * Vietnam
 * +840766040293
 * freuss47@gmail.com
 */
open class FeedItemVH(itemView: View) : FeedViewHolder(SwipeLayout(itemView)) {
    private val swipeLayout = this.itemView as SwipeLayout
    val container: View = itemView.findViewById(R.id.container)
    val source: TextView = itemView.findViewById(R.id.source)
    private val tvTime: TextView = itemView.findViewById(R.id.tvTime)
    val title: TextView = itemView.findViewById(R.id.title)
    val description: TextView = itemView.findViewById(R.id.description)
    val icon: ImageView = itemView.findViewById(R.id.icon)
    private val cvActionsContainer: CardView = itemView.findViewById(R.id.cvActionsContainer)

    private val separatorDrawable = GradientDrawable().apply {
        shape = GradientDrawable.RECTANGLE
        setSize(
            /* width = */ 1.dp.toPixels(itemView),
            /* height = */ 0
        )
    }

    @SuppressLint("ClickableViewAccessibility")
    private val rvActions: RecyclerView =
        cvActionsContainer.findViewById<RecyclerView>(R.id.rvActions).apply {
            layoutManager = LinearLayoutManager(
                /* context = */ context,
                /* orientation = */RecyclerView.HORIZONTAL,
                /* reverseLayout = */false
            )
            addItemDecoration(
                DividerItemDecorator(
                    context = itemView.context,
                    orientation = DividerItemDecoration.HORIZONTAL,
                    divider = separatorDrawable
                )
            )
            setOnTouchListener { v, _ ->
                v.parent.requestDisallowInterceptTouchEvent(true)
                false
            }
        }

    override fun onBind(
        item: FeedItem,
    ) {
        swipeLayout.reset()
        title.text = item.title

        applyIfNotNull(view = description, value = item.description, block = TextView::setText)
        applyIfNotNull(view = icon, value = item.sourceIcon, block = ImageView::setImageDrawable)
        applyIfNotNull(view = source, value = item.source, block = TextView::setText)

        title.isVisible = item.title.isNotEmpty()
        description.isVisible = !item.description.isNullOrEmpty()

        itemView.setOnClickListener(item::onTap)
        if (item.actions.isEmpty()) {
            cvActionsContainer.isVisible = false
        } else {
            cvActionsContainer.isVisible = true
            rvActions.adapter = ActionsAdapter(item.actions)
        }
        if (item.instant == Instant.MAX) {
            tvTime.isVisible = false
        } else {
            tvTime.isVisible = true
            tvTime.text = item.formatTimeAgo(itemView.resources)
        }
        swipeLayout.onSwipeAway = item::onDismiss
        swipeLayout.isSwipeAble = item.isDismissible
        swipeLayout.setSwipeColor(C.COLOR_PRIMARY_2)
        swipeLayout.setIconColor(C.COLOR_PRIMARY)
    }
}
