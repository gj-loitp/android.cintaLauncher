package com.roy93group.lookerupper.data.results

import android.app.Activity
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
class CompactAppResult(
    val app: App
) : CompactResult() {

    inline val packageName: String get() = app.packageName
    inline val name: String get() = app.name
    override val title: String get() = app.label
    override val icon: Drawable get() = app.icon
    override val subtitle: Nothing? get() = null
    override var relevance = Relevance(0f)

    override val onLongPress = { _: View, _: Activity ->
        true
    }

//    fun getColor(): Int = app.getColor()

    override fun open(view: View) {
        app.open(context = view.context, view = view)
    }
}
