package com.roy93group.lookerupper.data.results

import android.app.Activity
import android.graphics.drawable.Drawable
import android.view.View
import com.roy93group.cintalauncher.data.items.App
import com.roy93group.cintalauncher.providers.color.theme.ColorTheme
import com.roy93group.cintalauncher.ui.popup.appItem.ItemLongPress
import io.posidon.android.conveniencelib.getNavigationBarHeight

class CompactAppResult(
    val app: App
) : CompactResult() {

    inline val packageName: String get() = app.packageName
    inline val name: String get() = app.name
    override val title: String get() = app.label
    override val icon: Drawable get() = app.icon

    override val subtitle: Nothing? get() = null

    override var relevance = Relevance(0f)
    override val onLongPress = { v: View, activity: Activity ->
        val backgroundColor = ColorTheme.tintWithColor(ColorTheme.cardBG, getColor())
        ItemLongPress.onItemLongPress(
            view = v,
            backgroundColor = backgroundColor,
            textColor = ColorTheme.titleColorForBG(background = backgroundColor),
            item = app,
            navbarHeight = activity.getNavigationBarHeight(),
        )
        true
    }

    fun getColor(): Int = app.getColor()

    override fun open(view: View) {
        app.open(context = view.context, view = view)
    }
}
