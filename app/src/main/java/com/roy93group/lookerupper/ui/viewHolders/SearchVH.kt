package com.roy93group.lookerupper.ui.viewHolders

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.roy93group.lookerupper.data.results.SearchResult

abstract class SearchVH(itemView: View) : RecyclerView.ViewHolder(itemView) {
    abstract fun onBind(result: SearchResult)
}
