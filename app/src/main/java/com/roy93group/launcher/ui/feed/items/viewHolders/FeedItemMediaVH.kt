package com.roy93group.launcher.ui.feed.items.viewHolders

import android.graphics.*
import android.view.HapticFeedbackConstants
import android.view.View
import android.widget.ImageView
import androidx.core.graphics.applyCanvas
import androidx.core.graphics.toXfermode
import com.roy93group.launcher.R
import com.roy93group.launcher.data.feed.items.FeedItem
import com.roy93group.launcher.data.feed.items.FeedItemWithMedia
import kotlinx.android.synthetic.main.view_feed_item_media.view.*

/**
 * Updated by Loitp on 2022.12.17
 * Galaxy One company,
 * Vietnam
 * +840766040293
 * freuss47@gmail.com
 */
class FeedItemMediaVH(
    itemView: View,
) : FeedItemVH(itemView) {

    override fun onBind(
        feedItem: FeedItem,
        isDisplayAppIcon: Boolean,
        isForceColorIcon: Boolean,
    ) {
        super.onBind(feedItem, isDisplayAppIcon, isForceColorIcon)

        feedItem as FeedItemWithMedia

        val c = feedItem.image as? Bitmap
        if (c == null) {
            itemView.image.setImageDrawable(null)
        } else {
            val paint = Paint().apply {
                shader = LinearGradient(
                    /* x0 = */ c.width.toFloat() / 2f,
                    /* y0 = */ 0f,
                    /* x1 = */ c.width.toFloat(),
                    /* y1 = */ 0f,
                    /* colors = */ intArrayOf(
                        0,
                        0x33000000,
                        0x88000000.toInt(),
                        0xdd000000.toInt(),
                        0xff000000.toInt()
                    ),
                    /* positions = */ floatArrayOf(
                        0f, .25f, .5f, .75f, 1f
                    ),
                    /* tile = */ Shader.TileMode.CLAMP
                )
                xfermode = PorterDuff.Mode.DST_IN.toXfermode()
            }
            val paint2 = Paint().apply {
                shader = LinearGradient(
                    /* x0 = */ 0f,
                    /* y0 = */ 0f,
                    /* x1 = */ c.width.toFloat() * 1.5f,
                    /* y1 = */ 0f,
                    /* color0 = */ 0,
                    /* color1 = */ feedItem.color,
                    /* tile = */ Shader.TileMode.CLAMP
                )
                alpha = 100
                xfermode = PorterDuff.Mode.DST_OVER.toXfermode()
            }
            val w = c.width * 1.5f
            val bitmap = Bitmap.createBitmap(w.toInt(), c.height, c.config).applyCanvas {
                val x = (width - c.width).toFloat()
                drawBitmap(/* bitmap = */ c, /* left = */ x, /* top = */ 0f, /* paint = */ paint2)
                drawRect(x, 0f, c.width.toFloat(), height.toFloat(), paint)
                drawRect(0f, 0f, width.toFloat(), height.toFloat(), paint2)
            }
            itemView.image.setImageBitmap(bitmap)
        }

        itemView.image.setOnClickListener(feedItem::onTap)
        itemView.title.setOnClickListener(feedItem::onTap)
        itemView.description.setOnClickListener(feedItem::onTap)

        itemView.buttonPrevious.apply {
            setColorFilter(colorPrimary)
            setOnClickListener {
                it.performHapticFeedback(HapticFeedbackConstants.LONG_PRESS)
                feedItem.previous(it)
            }
        }

        itemView.btNext.apply {
            setColorFilter(colorPrimary)
            setOnClickListener {
                it.performHapticFeedback(HapticFeedbackConstants.LONG_PRESS)
                feedItem.next(it)
            }
        }

        itemView.buttonPlay.apply {
            setImageResource(
                if (feedItem.isPlaying())
                    R.drawable.ic_pause
                else
                    R.drawable.ic_play
            )
            setColorFilter(colorPrimary)
            setOnClickListener {
                it as ImageView
                it.performHapticFeedback(HapticFeedbackConstants.LONG_PRESS)
                feedItem.togglePause(it)
            }
        }

    }
}
