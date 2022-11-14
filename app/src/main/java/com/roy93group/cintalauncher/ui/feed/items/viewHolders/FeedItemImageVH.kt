package com.roy93group.cintalauncher.ui.feed.items.viewHolders

import android.graphics.drawable.Drawable
import android.view.View
import android.widget.ImageView
import androidx.cardview.widget.CardView
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
import com.roy93group.cintalauncher.R
import com.roy93group.cintalauncher.data.feed.items.FeedItem
import com.roy93group.cintalauncher.data.feed.items.FeedItemWithBigImage
import com.roy93group.cintalauncher.providers.color.theme.ColorTheme
import io.posidon.android.conveniencelib.units.dp
import io.posidon.android.conveniencelib.units.toFloatPixels

open class FeedItemImageVH(itemView: View) : FeedItemVH(itemView) {
    val image: ImageView = itemView.findViewById(R.id.image)
    val card: CardView = itemView.findViewById(R.id.card)
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
            target.onResourceReady(resource, null)
            return true
        }
    }

    override fun onBind(item: FeedItem, color: Int) {
        super.onBind(item, color)
        item as FeedItemWithBigImage
        card.setCardBackgroundColor(ColorTheme.cardBG)
        Glide.with(itemView.context)
            .load(item.image)
            .apply(requestOptions)
            .listener(imageRequestListener)
            .into(image)
    }
}
