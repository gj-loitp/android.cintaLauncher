package com.roy93group.cintalauncher.data.items

import android.content.Context
import android.content.Intent
import android.graphics.drawable.Drawable
import android.net.Uri
import android.view.View

interface LauncherItem {
    companion object {
        fun parse(string: String, appsByName: HashMap<String, MutableList<App>>): LauncherItem? {
            return App.parse(string, appsByName)
        }
    }

    val icon: Drawable
    val label: String

    fun getColor(): Int = 0

    /**
     * What to do when the item is clicked
     * [view] The view that was clicked
     */
    fun open(context: Context, view: View?)

    /**
     * Text representation of the item, used to save it
     */
    override fun toString(): String
}

@Suppress("unused")
fun LauncherItem.showProperties(view: View, backgroundColor: Int, textColor: Int) {
    if (this is App) {
        view.context.startActivity(
            Intent(android.provider.Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
                .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                .setData(Uri.parse("package:$packageName"))
        )
    }
}
