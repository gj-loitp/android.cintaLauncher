package com.roy93group.lookerupper.data.providers

import com.roy93group.lookerupper.data.SearchQuery
import com.roy93group.lookerupper.data.Searcher
import com.roy93group.lookerupper.data.results.SearchResult

/**
 * Updated by Loitp on 2022.12.18
 * Galaxy One company,
 * Vietnam
 * +840766040293
 * freuss47@gmail.com
 */
abstract class AsyncSearchProvider(
    private val searcher: Searcher
) : SearchProvider {

    private val lastResults = HashMap<SearchQuery, List<SearchResult>>()

    override fun getResults(query: SearchQuery): List<SearchResult> {
        return lastResults.getOrElse(query) {
            loadResults(query = query)
            emptyList()
        }
    }

    abstract fun loadResults(query: SearchQuery)

    fun update(
        query: SearchQuery,
        results: List<SearchResult>
    ) {
        lastResults[query] = results
        searcher.query(query.text)
    }
}
