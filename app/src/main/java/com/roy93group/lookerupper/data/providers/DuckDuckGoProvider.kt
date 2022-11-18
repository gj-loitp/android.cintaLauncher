package com.roy93group.lookerupper.data.providers

import android.app.Activity
import com.roy93group.launcher.BuildConfig
import com.roy93group.lookerupper.data.SearchQuery
import com.roy93group.lookerupper.data.Searcher
import com.roy93group.lookerupper.data.results.InstantAnswerResult
import io.posidon.android.libduckduckgo.DuckDuckGo

/**
 * Updated by Loitp on 2022.12.18
 * Galaxy One company,
 * Vietnam
 * +840766040293
 * freuss47@gmail.com
 */
class DuckDuckGoProvider(searcher: Searcher) : AsyncSearchProvider(searcher) {

    override fun Activity.onCreate() {
    }

    override fun loadResults(query: SearchQuery) {
        if (query.length >= 3) {
            val q = query.toString()
            DuckDuckGo.instantAnswer(query = q, t = BuildConfig.APPLICATION_ID) {
                update(
                    query, listOf(
                        InstantAnswerResult(
                            query = query,
                            title = it.title,
                            description = it.description,
                            sourceName = it.sourceName,
                            sourceUrl = it.sourceUrl,
                            searchUrl = DuckDuckGo.searchURL(q, BuildConfig.APPLICATION_ID),
                            infoTable = it.infoTable?.filter { a -> a.dataType == "string" }
                                ?.map { a -> a.label + ':' to a.value }?.takeIf(List<*>::isNotEmpty)
                        )
                    )
                )
            }
        }
    }
}
