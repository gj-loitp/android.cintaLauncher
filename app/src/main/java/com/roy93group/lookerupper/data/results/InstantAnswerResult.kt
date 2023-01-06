package com.roy93group.lookerupper.data.results

import android.content.Intent
import android.net.Uri
import android.view.View
import com.loitp.core.ext.tranIn
import com.roy93group.lookerupper.data.SearchQuery
import com.willowtreeapps.fuzzywuzzy.diffutils.FuzzySearch
import kotlin.math.max

/**
 * Updated by Loitp on 2022.12.18
 * Galaxy One company,
 * Vietnam
 * +840766040293
 * freuss47@gmail.com
 */
class InstantAnswerResult(
    query: SearchQuery,
    override val title: String,
    val description: String,
    val sourceName: String,
    private val sourceUrl: String,
    private val searchUrl: String,
    val infoTable: List<Pair<String, String>>?
) : SearchResult {
    override var relevance = Relevance(
        max(
            FuzzySearch.tokenSortPartialRatio(query.toString(), title),
            FuzzySearch.tokenSortPartialRatio(query.toString(), description)
        ) / 100f
    )

    override fun open(view: View) {
        view.context.startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(sourceUrl)))
        view.context.tranIn()
    }

    fun search(view: View) {
        view.context.startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(searchUrl)))
        view.context.tranIn()
    }
}
