package com.roy93group.launcher.providers.feed

import com.roy93group.launcher.data.feed.items.FeedItem
import kotlin.concurrent.thread

/**
 * Updated by Loitp on 2022.12.16
 * Galaxy One company,
 * Vietnam
 * +840766040293
 * freuss47@gmail.com
 */
abstract class AsyncFeedItemProvider : FeedItemProvider() {

    final override fun getUpdated(): List<FeedItem> {
        thread(isDaemon = true) {
            itemCache = loadItems()
            feed.update()
        }
        return itemCache ?: emptyList()
    }

    abstract fun shouldReload(): Boolean

    abstract fun loadItems(): List<FeedItem>
}
