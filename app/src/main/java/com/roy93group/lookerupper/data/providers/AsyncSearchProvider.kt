package com.roy93group.lookerupper.data.providers

import com.roy93group.lookerupper.data.SearchQuery
import com.roy93group.lookerupper.data.Searcher
import com.roy93group.lookerupper.data.results.SearchResult

abstract class AsyncSearchProvider(
    val searcher: Searcher
) : SearchProvider {

    val lastResults = HashMap<SearchQuery, List<SearchResult>>()

    override fun getResults(query: SearchQuery): List<SearchResult> {
        return lastResults.getOrElse(query) {
            loadResults(query)
            emptyList()
        }
    }

    abstract fun loadResults(query: SearchQuery)

    fun update(query: SearchQuery, results: List<SearchResult>) {
        lastResults[query] = results
        searcher.query(query.text)
    }
}