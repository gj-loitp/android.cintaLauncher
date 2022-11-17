package com.roy93group.launcher.ui.settings.feedChooser

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.roy93group.launcher.R
import com.roy93group.launcher.storage.Settings

/**
 * Updated by Loitp on 2022.12.17
 * Galaxy One company,
 * Vietnam
 * +840766040293
 * freuss47@gmail.com
 */
class FeedChooserAdapter(
    private val settings: Settings,
    private val feedUrls: ArrayList<String>
) : RecyclerView.Adapter<FeedChooserAdapter.ViewHolder>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var card: LinearLayout = itemView.findViewById(R.id.card)
        var txt: TextView = itemView.findViewById(R.id.txt)
    }

    override fun onBindViewHolder(holder: ViewHolder, i: Int) {
        val url = feedUrls[i]
        holder.txt.text = url

//        holder.card.backgroundTintList = ColorStateList.valueOf(Color.YELLOW)
//        holder.txt.setTextColor(Color.RED)
        holder.txt.setOnLongClickListener {
            FeedSourcesChooserActivity.sourceEditPopup(it, settings, feedUrls, this, i)
            true
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.view_feed_chooser_option, parent, false)
        )
    }

    override fun getItemCount() = feedUrls.size

    override fun getItemId(i: Int) = 0L
}
