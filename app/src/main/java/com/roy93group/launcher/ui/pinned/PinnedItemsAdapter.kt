package com.roy93group.launcher.ui.pinned

import android.annotation.SuppressLint
import android.app.Activity
import android.content.ClipData
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.roy93group.launcher.LauncherContext
import com.roy93group.launcher.R
import com.roy93group.launcher.data.items.LauncherItem
import com.roy93group.launcher.ui.pinned.viewHolders.DropTargetViewHolder
import com.roy93group.launcher.ui.pinned.viewHolders.PinnedViewHolder
import com.roy93group.launcher.ui.pinned.viewHolders.bindDropTargetViewHolder
import com.roy93group.launcher.ui.pinned.viewHolders.bindPinnedViewHolder
import io.posidon.android.conveniencelib.getNavigationBarHeight

/**
 * Updated by Loitp on 2022.12.16
 * Galaxy One company,
 * Vietnam
 * +840766040293
 * freuss47@gmail.com
 */
class PinnedItemsAdapter(
    launcherActivity: Activity,
    private val launcherContext: LauncherContext,
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val navbarHeight = launcherActivity.getNavigationBarHeight()
    private var dropTargetIndex = -1
    private var items: MutableList<LauncherItem> = ArrayList()

    override fun getItemCount(): Int = iToAdapterPosition(items.size)

    override fun getItemViewType(i: Int): Int {
        return if (dropTargetIndex == i) 1 else 0
    }

    private fun adapterPositionToI(position: Int): Int {
        return when {
            dropTargetIndex == -1 -> position
            dropTargetIndex <= position -> position - 1
            else -> position
        }
    }

    private fun iToAdapterPosition(i: Int): Int {
        return when {
            dropTargetIndex == -1 -> i
            dropTargetIndex <= i -> i + 1
            else -> i
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): RecyclerView.ViewHolder {
        return when (viewType) {
            1 -> DropTargetViewHolder(
                LayoutInflater.from(parent.context)
                    .inflate(R.layout.view_pinned_drop_target, parent, false) as ImageView
            )
            else -> PinnedViewHolder(
                LayoutInflater.from(parent.context)
                    .inflate(R.layout.view_pinned_item, parent, false) as ImageView
            )
        }
    }

    private fun updatePins(v: View) {
        launcherContext.appManager.setPinned(context = v.context, pinned = ArrayList(items))
    }

    override fun onBindViewHolder(
        holder: RecyclerView.ViewHolder,
        ii: Int
    ) {
        if (ii == dropTargetIndex) {
            holder as DropTargetViewHolder
            bindDropTargetViewHolder(holder)
            return
        }
        val item = items[adapterPositionToI(ii)]
        holder as PinnedViewHolder
        bindPinnedViewHolder(
            holder = holder,
            item = item,
        )
    }

    @SuppressLint("NotifyDataSetChanged")
    fun updateItems(items: List<LauncherItem>) {
        this.items = items.toMutableList()
        notifyDataSetChanged()
    }

    fun showDropTarget(i: Int) {
        if (i != dropTargetIndex) {
            when {
                i == -1 -> {
                    val old = dropTargetIndex
                    dropTargetIndex = -1
                    notifyItemRemoved(old)
                }
                dropTargetIndex == -1 -> {
                    dropTargetIndex = i
                    notifyItemInserted(i)
                }
                else -> {
                    val old = dropTargetIndex
                    dropTargetIndex = i
                    notifyItemMoved(old, i)
                }
            }
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    fun onDrop(
        v: View,
        i: Int,
        clipData: ClipData
    ) {
        if (i != dropTargetIndex) throw IllegalStateException("PinnedItemsAdapter -> i = $i, dropTargetIndex = $dropTargetIndex")
        val item =
            launcherContext.appManager.parseLauncherItem(clipData.getItemAt(0).text.toString())
        item?.let {
            items.add(i, it)
        }
        dropTargetIndex = -1
        notifyDataSetChanged()
        updatePins(v)
    }
}
