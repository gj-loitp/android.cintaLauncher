package com.roy93group.cintalauncher.providers.feed

import com.roy93group.cintalauncher.data.feed.items.FeedItem
import kotlin.concurrent.thread

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
