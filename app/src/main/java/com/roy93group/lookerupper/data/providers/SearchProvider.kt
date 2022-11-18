package com.roy93group.lookerupper.data.providers

import android.app.Activity
import com.roy93group.lookerupper.data.SearchQuery
import com.roy93group.lookerupper.data.results.SearchResult

/**
 * Updated by Loitp on 2022.12.18
 * Galaxy One company,
 * Vietnam
 * +840766040293
 * freuss47@gmail.com
 */
interface SearchProvider {
    fun Activity.onCreate()
    fun getResults(query: SearchQuery): List<SearchResult>
}
