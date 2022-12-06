package com.roy93group.launcher.ui.feedProfiles

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.card.MaterialCardView
import com.roy93group.app.C
import com.roy93group.launcher.R

/**
 * Updated by Loitp on 2022.12.17
 * Galaxy One company,
 * Vietnam
 * +840766040293
 * freuss47@gmail.com
 */
class FeedFilterVH(
    itemView: View
) : RecyclerView.ViewHolder(itemView) {
    val text: TextView = itemView.findViewById(R.id.text)
    val icon: ImageView = itemView.findViewById(R.id.icon)
    val card: MaterialCardView = itemView.findViewById<MaterialCardView>(R.id.card).apply {
        setCardBackgroundColor(C.COLOR_PRIMARY_2)
    }
}
