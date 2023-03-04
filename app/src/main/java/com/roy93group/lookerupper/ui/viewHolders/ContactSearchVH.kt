package com.roy93group.lookerupper.ui.viewHolders

import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.roy93group.ext.getColorPrimary
import com.roy93group.launcher.R
import com.roy93group.lookerupper.data.results.ContactResult
import com.roy93group.lookerupper.data.results.SearchResult
import kotlinx.android.synthetic.main.view_search_result_contact.view.*

/**
 * Updated by Loitp on 2022.12.18
 * Galaxy One company,
 * Vietnam
 * +840766040293
 * freuss47@gmail.com
 */
class ContactSearchVH(
    val activity: AppCompatActivity, itemView: View
) : SearchVH(itemView) {
    private val colorPrimary = getColorPrimary()
//    private val colorBackground = C.getColorBackground()

    override fun onBind(
        result: SearchResult,
        isDisplayAppIcon: Boolean,
        isForceColorIcon: Boolean,
    ) {
        result as ContactResult

        itemView.icon.apply {
            if (isDisplayAppIcon) {
                isVisible = true
                Glide.with(itemView).load(result.iconUri).placeholder(ContextCompat.getDrawable(
                        itemView.context, R.drawable.placeholder_contact
                    )?.apply {
                        setTint(colorPrimary)
                    }).apply(RequestOptions.circleCropTransform()).into(this)
            } else {
                isVisible = false
            }
        }
        itemView.text.apply {
            text = result.title
            setTextColor(colorPrimary)
        }

//        itemView.setOnClickListener(result::open)
        itemView.setOnClickListener {
            result.open(activity = activity, view = it)
        }
    }
}
