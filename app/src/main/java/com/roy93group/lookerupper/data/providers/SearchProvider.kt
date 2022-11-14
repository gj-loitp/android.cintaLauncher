package com.roy93group.lookerupper.data.providers

import android.app.Activity
import com.roy93group.lookerupper.data.SearchQuery
import com.roy93group.lookerupper.data.results.SearchResult

interface SearchProvider {
    fun Activity.onCreate()
    fun getResults(query: SearchQuery): List<SearchResult>
}
