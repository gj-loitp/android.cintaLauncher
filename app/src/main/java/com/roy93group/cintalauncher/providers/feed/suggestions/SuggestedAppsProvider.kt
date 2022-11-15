package com.roy93group.cintalauncher.providers.feed.suggestions

import android.view.View
import com.roy93group.cintalauncher.data.feed.items.FeedItem
import com.roy93group.cintalauncher.data.feed.items.FeedItemSuggestedApps
import com.roy93group.cintalauncher.data.feed.items.longHash
import com.roy93group.cintalauncher.providers.feed.FeedItemProvider
import java.time.Instant

class SuggestedAppsProvider : FeedItemProvider() {

    override fun getUpdated(): List<FeedItem> {
        return SuggestionsManager.getSuggestions().let {
            it.subList(0, it.size.coerceAtMost(6))
        }.let {
            object : FeedItemSuggestedApps {
                override val apps = it
                override val color = 0
                override val title = ""
                override val sourceIcon: Nothing? = null
                override val description: Nothing? = null
                override val source: Nothing? = null
                override val instant = Instant.MAX
                override fun onTap(view: View) {}
                override val isDismissible = false
                override val uid = "suggestions"
                override val id = uid.longHash()
            }
        }.let(::listOf)
    }
}
