package com.roy93group.lookerupper.ui.viewHolders

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.roy93group.launcher.R
import com.roy93group.launcher.providers.color.theme.ColorTheme
import com.roy93group.lookerupper.data.results.ContactResult
import com.roy93group.lookerupper.data.results.SearchResult

class ContactSearchVH(
    itemView: View,
    private val isOnCard: Boolean
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
                    setTint(if (isOnCard) ColorTheme.cardHint else ColorTheme.uiHint)
                })
            .apply(RequestOptions.circleCropTransform())
            .into(icon)
        text.text = result.title
        text.setTextColor(if (isOnCard) ColorTheme.cardTitle else ColorTheme.uiTitle)
        itemView.setOnClickListener(result::open)
    }
}
