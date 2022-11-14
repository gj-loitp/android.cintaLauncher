package com.roy93group.lookerupper.data

import android.app.Activity
import com.roy93group.cintalauncher.storage.Settings
import com.roy93group.lookerupper.data.providers.SearchProvider
import com.roy93group.lookerupper.data.results.SearchResult
import java.util.*

class Searcher(
    val settings: Settings,
    vararg providers: (Searcher) -> SearchProvider,
    val update: (List<SearchResult>) -> Unit
) {
    val providers = providers.map { it(this) }

    private fun query(query: SearchQuery): List<SearchResult> {
        val r = LinkedList<SearchResult>()
        providers.flatMapTo(r) { it.getResults(query) }
        r.sortWith { a, b ->
            b.relevance.compareTo(a.relevance)
        }
        return r
    }

    fun query(query: CharSequence?) {
        update(if (query == null) emptyList()
        else query(SearchQuery(query)))
    }

    fun onCreate(activity: Activity) {
        providers.forEach {
            it.apply { activity.onCreate() }
        }
    }
}