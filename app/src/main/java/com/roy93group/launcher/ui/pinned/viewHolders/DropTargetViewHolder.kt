package com.roy93group.launcher.ui.pinned.viewHolders

import android.content.res.ColorStateList
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.loitpcore.core.utilities.LAppResource
import com.roy93group.launcher.R

/**
 * Updated by Loitp on 2022.12.17
 * Galaxy One company,
 * Vietnam
 * +840766040293
 * freuss47@gmail.com
 */
class DropTargetViewHolder(
    val icon: ImageView,
) : RecyclerView.ViewHolder(icon)

fun bindDropTargetViewHolder(
    holder: DropTargetViewHolder,
) {
    holder.icon.imageTintList = ColorStateList.valueOf(LAppResource.getColor(R.color.blueViolet))
}
