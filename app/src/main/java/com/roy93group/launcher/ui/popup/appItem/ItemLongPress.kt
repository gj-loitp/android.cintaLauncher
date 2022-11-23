package com.roy93group.launcher.ui.popup.appItem

import android.annotation.SuppressLint
import android.content.ClipData
import android.content.ClipDescription
import android.content.Context
import android.content.pm.LauncherApps
import android.content.pm.ShortcutInfo
import android.view.LayoutInflater
import android.view.View
import android.widget.PopupWindow
import androidx.appcompat.widget.ListPopupWindow
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.roy93group.launcher.R
import com.roy93group.launcher.data.items.App
import com.roy93group.launcher.data.items.LauncherItem
import com.roy93group.launcher.data.items.showProperties
import com.roy93group.launcher.ui.popup.PopupUtils
import io.posidon.android.conveniencelib.vibrate

/**
 * Updated by Loitp on 2022.12.17
 * Galaxy One company,
 * Vietnam
 * +840766040293
 * freuss47@gmail.com
 */
object ItemLongPress {

    var currentPopup: PopupWindow? = null

    @SuppressLint("InflateParams")
    private fun makePopupWindow(
        context: Context,
        item: LauncherItem,
        textColor: Int,
        extraPopupWindow: PopupWindow?,
        onInfo: (View) -> Unit
    ): PopupWindow {
        val card =
            LayoutInflater.from(context).inflate(R.layout.view_long_press_item_popup, null)
        if (item is App) {
            val shortcuts =
                item.getStaticShortcuts(context.getSystemService(LauncherApps::class.java))
            if (shortcuts.isNotEmpty()) {
                val recyclerView = card.findViewById<RecyclerView>(R.id.recyclerView)
                recyclerView.isNestedScrollingEnabled = false
                recyclerView.layoutManager = LinearLayoutManager(context)
                recyclerView.adapter = ShortcutAdapter(shortcuts = shortcuts, txtColor = textColor)
            }
        }
        val window =
            PopupWindow(
                /* contentView = */ card,
                /* width = */ListPopupWindow.WRAP_CONTENT,
                /* height = */ListPopupWindow.WRAP_CONTENT,
                /* focusable = */true
            )
        window.setOnDismissListener {
            extraPopupWindow?.dismiss()
            currentPopup = null
        }

        val llPropertiesItem = card.findViewById<View>(R.id.llPropertiesItem)
//        val tvPropertiesText = llPropertiesItem.findViewById<TextView>(R.id.tvPropertiesText)
//        val ivPropertiesIcon = llPropertiesItem.findViewById<ImageView>(R.id.ivPropertiesIcon)

//        tvPropertiesText.setTextColor(textColor)
//        ivPropertiesIcon.imageTintList = ColorStateList.valueOf(textColor)
//        card.findViewById<CardView>(R.id.card).setCardBackgroundColor(backgroundColor)

        llPropertiesItem.setOnClickListener {
            window.dismiss()
            onInfo(it)
        }

        currentPopup = window

        return window
    }

    @SuppressLint("InflateParams")
    private fun makeExtraPopupWindow(
        context: Context,
        shortcuts: List<ShortcutInfo>,
        textColor: Int
    ): PopupWindow {
        val content =
            LayoutInflater.from(context).inflate(R.layout.view_long_press_item_popup_extra, null)
        if (shortcuts.isNotEmpty()) {
            val recyclerView = content.findViewById<RecyclerView>(R.id.recyclerView)
            recyclerView.isNestedScrollingEnabled = false
            recyclerView.layoutManager = LinearLayoutManager(context)
            recyclerView.adapter = ShortcutAdapter(shortcuts, textColor)
        }
        val window =
            PopupWindow(
                /* contentView = */ content,
                /* width = */ListPopupWindow.WRAP_CONTENT,
                /* height = */ListPopupWindow.WRAP_CONTENT,
                /* focusable = */true
            )

//        content.findViewById<CardView>(R.id.card).setCardBackgroundColor(backgroundColor)

        window.isFocusable = false

        return window
    }

    fun onItemLongPress(
        view: View,
        textColor: Int,
        item: LauncherItem,
        navbarHeight: Int,
    ) {
        currentPopup?.dismiss()
        val context = view.context
        context.vibrate(14)
        val (x, y, gravity) = PopupUtils.getPopupLocationFromView(
            view = view,
            navbarHeight = navbarHeight
        )
        val dynamicShortcuts =
            (item as? App)?.getDynamicShortcuts(context.getSystemService(LauncherApps::class.java))
                ?.let {
                    it.subList(0, it.size.coerceAtMost(5))
                }
        val hasDynamicShortcuts = !dynamicShortcuts.isNullOrEmpty()
        val extraPopupWindow = if (hasDynamicShortcuts) makeExtraPopupWindow(
            context = context,
            shortcuts = dynamicShortcuts!!,
            textColor = textColor
        ) else null
        val popupWindow =
            makePopupWindow(
                context = context,
                item = item,
                textColor = textColor,
                extraPopupWindow = extraPopupWindow
            ) {
                item.showProperties(view)
            }
        popupWindow.isFocusable = false
        popupWindow.showAtLocation(
            /* parent = */ view,
            /* gravity = */ gravity,
            /* x = */ x,
            /* y = */ y + (view.resources.getDimension(R.dimen.margin_padding_medium) * 2).toInt()
        )

        if (hasDynamicShortcuts) popupWindow.contentView.post {
            extraPopupWindow?.showAtLocation(
                /* parent = */ view,
                /* gravity = */
                gravity,
                /* x = */
                x,
                /* y = */
                y + popupWindow.contentView.height + (view.resources.getDimension(R.dimen.margin_padding_medium) * 4).toInt()
            )
        }

        val shadow = View.DragShadowBuilder(view)
        val clipData = ClipData(
            /* label = */ item.label,
            /* mimeTypes = */ arrayOf(ClipDescription.MIMETYPE_TEXT_PLAIN),
            /* item = */ ClipData.Item(item.toString())
        )

        view.startDragAndDrop(
            /* data = */ clipData,
            /* shadowBuilder = */ shadow,
            /* myLocalState = */ view,
            /* flags = */ View.DRAG_FLAG_OPAQUE or View.DRAG_FLAG_GLOBAL
        )
    }
}
