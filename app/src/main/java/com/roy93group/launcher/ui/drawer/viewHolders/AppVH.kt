package com.roy93group.launcher.ui.drawer.viewHolders

import android.graphics.drawable.Drawable
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.core.view.isVisible
import androidx.palette.graphics.Palette
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.load.resource.bitmap.DownsampleStrategy
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.Target
import com.roy93group.launcher.R
import com.roy93group.launcher.data.items.App
import com.roy93group.launcher.data.items.LauncherItem
import com.roy93group.launcher.providers.feed.suggestions.SuggestionsManager
import com.roy93group.launcher.ui.drawer.AppDrawerAdapter
import com.roy93group.launcher.ui.drawer.AppDrawerAdapter.Companion.APP_ITEM
import com.roy93group.launcher.ui.feed.items.viewHolders.applyIfNotNull
import io.posidon.android.conveniencelib.drawable.toBitmap

/**
 * Updated by Loitp on 2022.12.16
 * Galaxy One company,
 * Vietnam
 * +840766040293
 * freuss47@gmail.com
 */
class AppViewHolder(
    val cardView: CardView
) : RecyclerView.ViewHolder(cardView) {
    val ivIcon: ImageView = itemView.findViewById(R.id.ivIcon)
    val tvIconText: TextView = itemView.findViewById(R.id.tvIconText)
    val ivIconSmall: ImageView = itemView.findViewById(R.id.ivIconSmall)
    val tvLineTitle: TextView = itemView.findViewById(R.id.tvLineTitle)
    val tvLineDescription: TextView = itemView.findViewById(R.id.tvLineDescription)
    val ivBackground: ImageView = itemView.findViewById(R.id.ivBackground)
    val requestOptions = RequestOptions().downsample(DownsampleStrategy.AT_MOST)

    class ImageRequestListener(
        val holder: AppViewHolder,
        val color: Int,
    ) : RequestListener<Drawable> {
        override fun onLoadFailed(
            e: GlideException?,
            model: Any?,
            target: Target<Drawable>?,
            isFirstResource: Boolean
        ) = false

        override fun onResourceReady(
            resource: Drawable,
            model: Any?,
            target: Target<Drawable>,
            dataSource: DataSource?,
            isFirstResource: Boolean
        ): Boolean {
            val palette = Palette.from(resource.toBitmap(width = 32, height = 32)).generate()
            val backgroundColor =
                palette.getDominantColor(color)

            holder.cardView.setCardBackgroundColor(backgroundColor)

            target.onResourceReady(/* resource = */ resource, /* transition = */ null)
            return true
        }
    }
}

class AppItem(val item: App) : AppDrawerAdapter.DrawerItem {
    override fun getItemViewType() = APP_ITEM
    override val label: String
        get() = item.label
}

fun bindAppViewHolder(
    holder: AppViewHolder,
    item: LauncherItem,
    isDimmed: Boolean,
) {
    val backgroundColor = item.getColor()
    holder.cardView.setCardBackgroundColor(backgroundColor)
    holder.cardView.alpha = if (isDimmed) .3f else 1f
    holder.tvIconText.text = item.label

    val banner = (item as? App)?.getBanner()
    if (banner?.text == null && banner?.title == null) {
        holder.ivIconSmall.isVisible = false
        holder.ivIcon.isVisible = true
        holder.ivIcon.setImageDrawable(item.icon)
    } else {
        holder.ivIconSmall.isVisible = true
        holder.ivIcon.isVisible = false
        holder.ivIconSmall.setImageDrawable(item.icon)
    }
    applyIfNotNull(view = holder.tvLineTitle, value = banner?.title, block = TextView::setText)
    applyIfNotNull(view = holder.tvLineDescription, value = banner?.text, block = TextView::setText)
    if (banner?.background == null) {
        holder.ivBackground.isVisible = false
    } else {
        holder.ivBackground.isVisible = true
        holder.ivBackground.setImageDrawable(null)
        holder.ivBackground.alpha = banner.bgOpacity
        Glide.with(holder.itemView.context)
            .load(banner.background)
            .apply(holder.requestOptions)
            .listener(AppViewHolder.ImageRequestListener(holder, item.getColor()))
            .into(holder.ivBackground)
    }

    holder.itemView.setOnClickListener {
        SuggestionsManager.onItemOpened(it.context, item)
        item.open(context = it.context.applicationContext, view = it)
    }
}
