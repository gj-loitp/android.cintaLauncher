package com.roy93group.launcher.ui.settings.feedChooser

import android.content.Context
import androidx.annotation.Keep
import com.roy93group.launcher.R

/**
 * Updated by Loitp on 2022.12.17
 * Galaxy One company,
 * Vietnam
 * +840766040293
 * freuss47@gmail.com
 */
@Keep
class Suggestions(val context: Context) {

    val list = mapOf(
        "United States" to R.raw.suggestions_united_states,
        "Spain" to R.raw.suggestions_spain,
        "Russia" to R.raw.suggestions_russia,
        "India" to R.raw.suggestions_india,
        "Ukraine" to R.raw.suggestions_ukraine,
        "Tech (English)" to R.raw.suggestions_tech_en,
        "Tech (Spanish)" to R.raw.suggestions_tech_es,
        "Tech (Italian)" to R.raw.suggestions_tech_it,
        "Tech (Gujarati)" to R.raw.suggestions_tech_gu
    )

    val topics = arrayOfNulls<Topic>(list.size)

    operator fun get(i: Int) = topics.getOrNull(i) ?: run {
        val t = Topic(
            context,
            list.entries.elementAt(i).key,
            list.entries.elementAt(i).value
        )
        topics[i] = t
        t
    }
}
