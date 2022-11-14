package com.roy93group.lookerupper.data.results

import android.app.Activity
import android.graphics.drawable.Drawable
import android.view.View

abstract class CompactResult : SearchResult {
    abstract val icon: Drawable
    abstract val subtitle: String?
    override var relevance = Relevance(0f)
    abstract val onLongPress: ((View, Activity) -> Boolean)?
}
