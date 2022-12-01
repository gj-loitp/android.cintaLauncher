package com.roy93group.lookerupper.data.providers

import android.Manifest
import android.app.Activity
import android.content.pm.PackageManager
import androidx.core.app.ActivityCompat
import com.roy93group.lookerupper.data.SearchQuery
import com.roy93group.lookerupper.data.Searcher
import com.roy93group.lookerupper.data.results.ContactResult
import com.roy93group.lookerupper.data.results.Relevance
import com.roy93group.lookerupper.data.results.SearchResult
import com.willowtreeapps.fuzzywuzzy.diffutils.FuzzySearch
import java.util.*

/**
 * Updated by Loitp on 2022.12.18
 * Galaxy One company,
 * Vietnam
 * +840766040293
 * freuss47@gmail.com
 */
class ContactProvider(searcher: Searcher) : SearchProvider {

    var contacts: Collection<ContactResult> = emptyList()

    override fun Activity.onCreate() {
        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.READ_CONTACTS
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            requestPermissions(arrayOf(Manifest.permission.READ_CONTACTS), 0)
            return
        }
        contacts = ContactResult.getList(this)
    }

    override fun getResults(query: SearchQuery): List<SearchResult> {
        val results = LinkedList<SearchResult>()
        contacts.forEach {
            val r = FuzzySearch.tokenSortPartialRatio(
                query.toString(),
                it.title
            ) / 100f * if (it.isStarred) 1.1f else 1f
            if (r > .6f) {
                it.relevance = Relevance(r)
                results += it
            }
        }
        return results
    }
}
