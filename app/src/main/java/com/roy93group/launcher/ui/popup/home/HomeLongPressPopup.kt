package com.roy93group.launcher.ui.popup.home

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.widget.PopupWindow
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.ListPopupWindow
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.roy93group.launcher.R
import com.roy93group.launcher.storage.Settings
import com.roy93group.launcher.ui.popup.PopupUtils
import com.roy93group.launcher.ui.popup.listPopup.ListPopupAdapter
import com.roy93group.launcher.ui.popup.listPopup.ListPopupItem
import com.roy93group.launcher.ui.settings.feedChooser.FeedSourcesChooserActivity
import com.roy93group.launcher.ui.settings.iconPackPicker.IconPackPickerActivity
import io.posidon.android.conveniencelib.Device
import java.util.concurrent.locks.ReentrantLock
import kotlin.concurrent.thread
import kotlin.concurrent.withLock

object HomeLongPressPopup {

    @SuppressLint("InflateParams")
    fun show(
        parent: View,
        touchX: Float,
        touchY: Float,
        navbarHeight: Int,
        settings: Settings,
    ) {
        val content = LayoutInflater.from(parent.context).inflate(R.layout.view_list_popup, null)
        val window =
            PopupWindow(content, ListPopupWindow.WRAP_CONTENT, ListPopupWindow.WRAP_CONTENT, true)
        PopupUtils.setCurrent(window)

//        content.findViewById<SeeThoughView>(R.id.blurBg).run {
//            drawable = acrylicBlur?.fullBlur?.let { BitmapDrawable(parent.resources, it) }
//        }

        val cardView = content.findViewById<CardView>(R.id.card)
        cardView.setCardBackgroundColor(Color.RED)
        content.findViewById<RecyclerView>(R.id.recycler).apply {
            layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
            adapter = ListPopupAdapter().apply {
                val updateLock = ReentrantLock()
                fun update() {
                    updateItems(createMainAdapter(parent.context, settings) {
                        thread(name = "Reloading color theme", isDaemon = true) {
                            updateLock.withLock {
//                                reloadColorTheme()
                                cardView.post {
                                    cardView.setCardBackgroundColor(Color.RED)
                                    update()
                                }
                            }
                        }
                    })
                }
                update()
            }
        }

        val gravity = Gravity.CENTER
        val x = touchX.toInt() - Device.screenWidth(parent.context) / 2
        val y = touchY.toInt() - Device.screenHeight(parent.context) / 2 + navbarHeight
        window.showAtLocation(parent, gravity, x, y)
    }

    private fun createMainAdapter(
        context: Context,
        settings: Settings,
        reloadColorTheme: () -> Unit,
    ): List<ListPopupItem> {
        return listOf(
            ListPopupItem(
                text = context.getString(R.string.color_theme_gen),
                description = context.resources.getStringArray(R.array.color_theme_gens)[Color.RED],
                icon = ContextCompat.getDrawable(context, R.drawable.ic_color_dropper),
            ) {
                AlertDialog.Builder(context)
                    .setSingleChoiceItems(R.array.color_theme_gens, Color.RED) { d, i ->
                        settings.edit(context) {
                            reloadColorTheme()
                        }
                        d.dismiss()
                    }
                    .show()
            },
            ListPopupItem(
                text = context.getString(R.string.color_theme_day_night),
                description = context.resources.getStringArray(R.array.color_theme_day_night)[Color.RED],
                icon = ContextCompat.getDrawable(context, R.drawable.ic_lightness),
            ) {
                AlertDialog.Builder(context)
                    .setSingleChoiceItems(
                        R.array.color_theme_day_night,
                        Color .RED
                    ) { d, i ->
                        settings.edit(context) {
                            reloadColorTheme()
                        }
                        d.dismiss()
                    }
                    .show()
            },
            ListPopupItem(
                text = context.getString(R.string.rss_sources),
                icon = ContextCompat.getDrawable(context, R.drawable.ic_news),
            ) {
                context.startActivity(Intent(context, FeedSourcesChooserActivity::class.java))
            },
            ListPopupItem(
                text = context.getString(R.string.icon_packs),
                icon = ContextCompat.getDrawable(context, R.drawable.ic_shapes),
            ) {
                context.startActivity(Intent(context, IconPackPickerActivity::class.java))
            },
        )
    }
}
