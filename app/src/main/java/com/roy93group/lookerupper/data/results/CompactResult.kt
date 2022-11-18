package com.roy93group.lookerupper.data.results

import android.app.Activity
import android.graphics.drawable.Drawable
import android.view.View

/**
 * Updated by Loitp on 2022.12.18
 * Galaxy One company,
 * Vietnam
 * +840766040293
 * freuss47@gmail.com
 */
abstract class CompactResult : SearchResult {
    abstract val icon: Drawable
    abstract val subtitle: String?
    override var relevance = Relevance(0f)
    abstract val onLongPress: ((View, Activity) -> Boolean)?
}
