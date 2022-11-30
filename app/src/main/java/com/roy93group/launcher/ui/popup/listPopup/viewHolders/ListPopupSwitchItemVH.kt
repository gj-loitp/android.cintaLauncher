package com.roy93group.launcher.ui.popup.listPopup.viewHolders

import android.content.Context
import android.graphics.drawable.Drawable
import android.graphics.drawable.GradientDrawable
import android.graphics.drawable.LayerDrawable
import android.graphics.drawable.StateListDrawable
import android.util.StateSet
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.widget.SwitchCompat
import com.roy93group.app.C
import com.roy93group.launcher.R
import com.roy93group.launcher.ui.feed.items.viewHolders.applyIfNotNull
import com.roy93group.launcher.ui.popup.listPopup.ListPopupItem
import io.posidon.android.conveniencelib.units.dp
import io.posidon.android.conveniencelib.units.toPixels

/**
 * Updated by Loitp on 2022.12.17
 * Galaxy One company,
 * Vietnam
 * +840766040293
 * freuss47@gmail.com
 */
class ListPopupSwitchItemVH(itemView: View) : ListPopupVH(itemView) {

    val icon: ImageView = itemView.findViewById(R.id.icon)
    val text: TextView = itemView.findViewById(R.id.text)
    val description: TextView = itemView.findViewById(R.id.description)
    private val switch: SwitchCompat = itemView.findViewById(R.id.toggle)

    override fun onBind(item: ListPopupItem) {
        text.text = item.text
        description.text = item.description

        itemView.setOnClickListener {
            switch.toggle()
        }

        switch.trackDrawable = generateTrackDrawable()
        switch.thumbDrawable = generateThumbDrawable(itemView.context)

        applyIfNotNull(view = description, value = item.description) { view, value ->
            view.text = value
        }
        applyIfNotNull(view = icon, value = item.icon) { view, value ->
            view.setImageDrawable(value)
        }
        switch.isChecked = (item.value as? Boolean) ?: false
        switch.setOnCheckedChangeListener(item.onToggle)
    }

    private fun generateTrackDrawable(): Drawable {
        val out = StateListDrawable()
        out.addState(
            intArrayOf(android.R.attr.state_checked),
            generateBG(C.COLOR_PRIMARY)
        )
        out.addState(
            StateSet.WILD_CARD,
            generateBG(C.COLOR_PRIMARY)
        )
        return out
    }

    private fun generateThumbDrawable(context: Context): Drawable {
        val out = StateListDrawable()
        out.addState(
            intArrayOf(android.R.attr.state_checked),
            generateCircle(context, C.COLOR_PRIMARY_2)
        )
        out.addState(
            StateSet.WILD_CARD,
            generateCircle(context, C.COLOR_PRIMARY_2)
        )
        return out
    }

    private fun generateCircle(context: Context, color: Int): Drawable {
        val r = 18.dp.toPixels(context)
        val inset = 4.dp.toPixels(context)
        return LayerDrawable(
            arrayOf(
                GradientDrawable().apply {
                    shape = GradientDrawable.OVAL
                    setColor(color)
                    setSize(r, r)
                    setStroke(1, 0x33000000)
                },
            )
        ).apply {
            setLayerInset(0, inset, inset, inset, inset)
        }
    }

    private fun generateBG(color: Int): Drawable {
        return GradientDrawable().apply {
            cornerRadius = Float.MAX_VALUE
            setColor(color)
            setStroke(1, 0x88000000.toInt())
        }
    }
}
