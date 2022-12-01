package com.roy93group.launcher.ui.popup.appItem

import android.annotation.SuppressLint
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
}
