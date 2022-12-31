package com.roy93group.lookerupper.data.results

import android.content.pm.LauncherApps
import android.content.pm.ShortcutInfo
import android.graphics.drawable.Drawable
import android.view.View
import com.roy93group.launcher.data.items.App

/**
 * Updated by Loitp on 2022.12.18
 * Galaxy One company,
 * Vietnam
 * +840766040293
 * freuss47@gmail.com
 */
class AppResult(
    val app: App
) : SearchResult {

    inline val packageName: String get() = app.packageName
    inline val name: String get() = app.name
    override val title: String get() = app.label
    inline val icon: Drawable get() = app.icon

    override var relevance = Relevance(0f)

//    fun getColor(): Int = app.getColor()

    override fun open(view: View) {
        app.open(context = view.context, view = view)
    }

    fun getStaticShortcuts(launcherApps: LauncherApps): List<ShortcutInfo> =
        app.getStaticShortcuts(launcherApps = launcherApps)

    fun getDynamicShortcuts(launcherApps: LauncherApps): List<ShortcutInfo> =
        app.getDynamicShortcuts(launcherApps = launcherApps)
}
