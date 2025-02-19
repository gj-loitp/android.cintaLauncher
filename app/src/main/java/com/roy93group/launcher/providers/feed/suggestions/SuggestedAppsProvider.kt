package com.roy93group.launcher.providers.feed.suggestions

import android.view.View
import com.roy93group.launcher.data.feed.items.FeedItem
import com.roy93group.launcher.data.feed.items.FeedItemSuggestedApps
import com.roy93group.launcher.data.feed.items.longHash
import com.roy93group.launcher.providers.feed.FeedItemProvider
import java.time.Instant

/**
 * Updated by Loitp on 2022.12.16
 * Galaxy One company,
 * Vietnam
 * +840766040293
 * freuss47@gmail.com
 */
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
