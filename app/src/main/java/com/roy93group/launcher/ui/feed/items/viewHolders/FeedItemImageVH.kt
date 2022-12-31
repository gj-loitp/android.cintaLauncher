package com.roy93group.launcher.ui.feed.items.viewHolders

import android.graphics.drawable.Drawable
import android.view.View
import android.widget.ImageView
import androidx.core.view.isVisible
import androidx.core.view.updateLayoutParams
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.load.resource.bitmap.DownsampleStrategy
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.Target
import com.bumptech.glide.request.target.ViewTarget
import com.roy93group.launcher.data.feed.items.FeedItem
import com.roy93group.launcher.data.feed.items.FeedItemWithBigImage
import io.posidon.android.conveniencelib.units.dp
import io.posidon.android.conveniencelib.units.toFloatPixels
import kotlinx.android.synthetic.main.view_feed_item_image.view.*

/**
 * Updated by Loitp on 2022.12.16
 * Galaxy One company,
 * Vietnam
 * +840766040293
 * freuss47@gmail.com
 */
open class FeedItemImageVH(
    itemView: View,
) : FeedItemVH(itemView) {
    private val requestOptions = RequestOptions()
        .diskCacheStrategy(DiskCacheStrategy.ALL)
        .downsample(DownsampleStrategy.AT_MOST)
        .sizeMultiplier(0.6f)

    private val imageRequestListener = object : RequestListener<Drawable> {
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
            target as ViewTarget<*, *>
            val imageView = target.view as ImageView
            imageView.updateLayoutParams {
                this.width = (this.height * resource.intrinsicWidth / resource.intrinsicHeight)
                val max = 156.dp.toFloatPixels(imageView)
                if (width > max) {
                    val m = max / width
                    width = (width * m).toInt()
                    height = (height * m).toInt()
                }
            }
            target.onResourceReady(/* resource = */ resource, /* transition = */ null)
            return true
        }
    }

    override fun onBind(
        feedItem: FeedItem,
        isDisplayAppIcon: Boolean,
        isForceColorIcon: Boolean,
    ) {
        super.onBind(feedItem, isDisplayAppIcon, isForceColorIcon)
        feedItem as FeedItemWithBigImage
        if (isDisplayAppIcon) {
            itemView.card.isVisible = true
            Glide.with(itemView.context)
                .load(feedItem.image)
                .apply(requestOptions)
                .listener(imageRequestListener)
                .into(itemView.image)
        } else {
            itemView.card.isVisible = false
        }
    }
}
