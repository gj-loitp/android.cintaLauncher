package com.roy93group.launcher.ui.feed.items.viewHolders

import android.content.res.ColorStateList
import android.graphics.*
import android.view.HapticFeedbackConstants
import android.view.View
import android.widget.ImageView
import androidx.core.graphics.ColorUtils
import androidx.core.graphics.applyCanvas
import androidx.core.graphics.toXfermode
import com.roy93group.launcher.R
import com.roy93group.launcher.data.feed.items.FeedItem
import com.roy93group.launcher.data.feed.items.FeedItemWithMedia
import com.roy93group.launcher.providers.color.theme.ColorTheme

class FeedItemMediaVH(
    itemView: View
) : FeedItemVH(itemView) {

    private val cover: ImageView = itemView.findViewById(R.id.image)
    private val previous: ImageView = itemView.findViewById(R.id.buttonPrevious)
    private val play: ImageView = itemView.findViewById(R.id.buttonPlay)
    private val next: ImageView = itemView.findViewById(R.id.btNext)

    override fun onBind(item: FeedItem, color: Int) {
        super.onBind(item, color)

        item as FeedItemWithMedia

        val c = item.image as? Bitmap
        if (c == null)
            cover.setImageDrawable(null)
        else {
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
                    /* color1 = */ item.color,
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
            cover.setImageBitmap(bitmap)
        }


        cover.setOnClickListener(item::onTap)
        title.setOnClickListener(item::onTap)
        description.setOnClickListener(item::onTap)

        val titleColor = ColorUtils.blendARGB(
            /* color1 = */ ColorTheme.adjustColorForContrast(ColorTheme.uiBG, item.color),
            /* color2 = */ ColorTheme.uiTitle,
            /* ratio = */ .7f
        )
        val titleTintList = ColorStateList.valueOf(titleColor)

        previous.imageTintList = titleTintList
        play.imageTintList = titleTintList
        next.imageTintList = titleTintList

        play.setImageResource(if (item.isPlaying()) R.drawable.ic_pause else R.drawable.ic_play)

        previous.setOnClickListener {
            it.performHapticFeedback(HapticFeedbackConstants.LONG_PRESS)
            item.previous(it)
        }
        next.setOnClickListener {
            it.performHapticFeedback(HapticFeedbackConstants.LONG_PRESS)
            item.next(it)
        }
        play.setOnClickListener {
            it as ImageView
            it.performHapticFeedback(HapticFeedbackConstants.LONG_PRESS)
            item.togglePause(it)
        }
    }
}
