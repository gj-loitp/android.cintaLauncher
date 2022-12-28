package com.roy93group.lookerupper.ui.viewHolders

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.roy93group.lookerupper.data.results.SearchResult

/**
 * Updated by Loitp on 2022.12.18
 * Galaxy One company,
 * Vietnam
 * +840766040293
 * freuss47@gmail.com
 */
abstract class SearchVH(itemView: View) : RecyclerView.ViewHolder(itemView) {
    abstract fun onBind(
        result: SearchResult,
        isDisplayAppIcon: Boolean,
        isForceColorIcon: Boolean
    )
}
