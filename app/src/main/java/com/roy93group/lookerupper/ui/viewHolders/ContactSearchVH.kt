package com.roy93group.lookerupper.ui.viewHolders

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.roy93group.app.C
import com.roy93group.launcher.R
import com.roy93group.lookerupper.data.results.ContactResult
import com.roy93group.lookerupper.data.results.SearchResult

/**
 * Updated by Loitp on 2022.12.18
 * Galaxy One company,
 * Vietnam
 * +840766040293
 * freuss47@gmail.com
 */
class ContactSearchVH(
    itemView: View
) : SearchVH(itemView) {

    val icon: ImageView = itemView.findViewById(R.id.icon)
    val text: TextView = itemView.findViewById(R.id.text)

    override fun onBind(result: SearchResult) {
        result as ContactResult

        Glide.with(itemView)
            .load(result.iconUri)
            .placeholder(
                ContextCompat.getDrawable(
                    itemView.context,
                    R.drawable.placeholder_contact
                )?.apply {
                    setTint(C.COLOR_PRIMARY_2)
                })
            .apply(RequestOptions.circleCropTransform())
            .into(icon)
        text.text = result.title
        itemView.setOnClickListener(result::open)
    }
}
