package com.roy93group.launcher.data.items

import android.content.Context
import android.graphics.drawable.Drawable
import android.view.View

/**
 * Updated by Loitp on 2022.12.16
 * Galaxy One company,
 * Vietnam
 * +840766040293
 * freuss47@gmail.com
 */
interface LauncherItem {
    companion object {
        fun parse(
            string: String,
            appsByName: HashMap<String, MutableList<App>>
        ): LauncherItem? {
            return App.parse(string = string, appsByName = appsByName)
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
