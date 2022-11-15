package com.roy93group.cintalauncher.ui.drawer.viewHolders

import android.app.Activity
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.core.graphics.ColorUtils
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
import com.roy93group.cintalauncher.R
import com.roy93group.cintalauncher.data.items.App
import com.roy93group.cintalauncher.data.items.LauncherItem
import com.roy93group.cintalauncher.providers.color.theme.ColorTheme
import com.roy93group.cintalauncher.providers.feed.suggestions.SuggestionsManager
import com.roy93group.cintalauncher.ui.acrylicBlur
import com.roy93group.cintalauncher.ui.drawer.AppDrawer.Companion.WIDTH_TO_HEIGHT
import com.roy93group.cintalauncher.ui.drawer.AppDrawerAdapter
import com.roy93group.cintalauncher.ui.drawer.AppDrawerAdapter.Companion.APP_ITEM
import com.roy93group.cintalauncher.ui.feed.items.viewHolders.applyIfNotNull
import com.roy93group.cintalauncher.ui.popup.appItem.ItemLongPress
import com.roy93group.cintalauncher.ui.view.HorizontalAspectRatioLayout
import com.roy93group.cintalauncher.ui.view.SeeThoughView
import io.posidon.android.conveniencelib.drawable.toBitmap
import io.posidon.android.conveniencelib.getNavigationBarHeight

class AppViewHolder(
    val card: CardView
) : RecyclerView.ViewHolder(card) {
    val icon: ImageView = itemView.findViewById(R.id.icon_image)
    val label: TextView = itemView.findViewById(R.id.icon_text)
    val iconSmall: ImageView = itemView.findViewById(R.id.icon_image_small)
    val spacer: View = itemView.findViewById(R.id.spacer)
    val lineTitle: TextView = itemView.findViewById(R.id.line_title)
    val lineDescription: TextView = itemView.findViewById(R.id.line_description)
    val imageView: ImageView = itemView.findViewById(R.id.background_image)
    val blurBG: SeeThoughView = itemView.findViewById(R.id.blur_bg)

    @Suppress("unused")
    val aspect: HorizontalAspectRatioLayout =
        itemView.findViewById<HorizontalAspectRatioLayout>(R.id.aspect).apply {
            widthToHeight = WIDTH_TO_HEIGHT
        }

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
                ColorTheme.tintWithColor(
                    base = ColorTheme.cardBG,
                    color = palette.getDominantColor(color)
                )
            val actuallyBackgroundColor =
                ColorUtils.blendARGB(
                    /* color1 = */ backgroundColor,
                    /* color2 = */color,
                    /* ratio = */holder.imageView.alpha
                )

            holder.card.setCardBackgroundColor(backgroundColor)
            holder.label.setTextColor(ColorTheme.titleColorForBG(actuallyBackgroundColor))
            holder.lineTitle.setTextColor(ColorTheme.titleColorForBG(actuallyBackgroundColor))
            holder.lineDescription.setTextColor(ColorTheme.textColorForBG(actuallyBackgroundColor))

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
    activity: Activity,
) {
    holder.blurBG.drawable = BitmapDrawable(
        /* res = */ holder.itemView.resources,
        /* bitmap = */acrylicBlur?.insaneBlur
    )

    val backgroundColor = ColorTheme.tintWithColor(ColorTheme.cardBG, item.getColor())
    holder.card.setCardBackgroundColor(backgroundColor)
    holder.card.alpha = if (isDimmed) .3f else 1f
    holder.label.text = item.label
    holder.label.setTextColor(ColorTheme.titleColorForBG(backgroundColor))
    holder.lineTitle.setTextColor(ColorTheme.titleColorForBG(backgroundColor))
    holder.lineDescription.setTextColor(ColorTheme.textColorForBG(backgroundColor))

    val banner = (item as? App)?.getBanner()
    if (banner?.text == null && banner?.title == null) {
        holder.iconSmall.isVisible = false
        holder.spacer.isVisible = true
        holder.icon.isVisible = true
        holder.icon.setImageDrawable(item.icon)
    } else {
        holder.iconSmall.isVisible = true
        holder.spacer.isVisible = false
        holder.icon.isVisible = false
        holder.iconSmall.setImageDrawable(item.icon)
    }
    applyIfNotNull(holder.lineTitle, banner?.title, TextView::setText)
    applyIfNotNull(holder.lineDescription, banner?.text, TextView::setText)
    if (banner?.background == null) {
        holder.imageView.isVisible = false
    } else {
        holder.imageView.isVisible = true
        holder.imageView.setImageDrawable(null)
        holder.imageView.alpha = banner.bgOpacity
        Glide.with(holder.itemView.context)
            .load(banner.background)
            .apply(holder.requestOptions)
            .listener(AppViewHolder.ImageRequestListener(holder, item.getColor()))
            .into(holder.imageView)
    }

    holder.itemView.setOnClickListener {
        SuggestionsManager.onItemOpened(it.context, item)
        item.open(context = it.context.applicationContext, view = it)
    }
    holder.itemView.setOnLongClickListener {
        ItemLongPress.onItemLongPress(
            view = it,
            backgroundColor = backgroundColor,
            textColor = ColorTheme.titleColorForBG(backgroundColor),
            item = item,
            navbarHeight = activity.getNavigationBarHeight(),
        )
        true
    }
}
