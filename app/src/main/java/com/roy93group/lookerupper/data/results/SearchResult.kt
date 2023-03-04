package com.roy93group.lookerupper.data.results

import android.view.View
import androidx.appcompat.app.AppCompatActivity

/**
 * Updated by Loitp on 2022.12.18
 * Galaxy One company,
 * Vietnam
 * +840766040293
 * freuss47@gmail.com
 */
interface SearchResult {
    val title: String
    val relevance: Relevance
    fun open(activity: AppCompatActivity, view: View)
}

@JvmInline
value class Relevance(val value: Float) {
    operator fun compareTo(other: Relevance) = value.compareTo(other.value)
}
