package com.roy93group.launcher.providers.feed

import com.roy93group.launcher.data.feed.items.FeedItem
import com.roy93group.launcher.storage.Settings

/**
 * Updated by Loitp on 2022.12.16
 * Galaxy One company,
 * Vietnam
 * +840766040293
 * freuss47@gmail.com
 */
abstract class FeedItemProvider {
    open fun onInit() {}
    abstract fun getUpdated(): List<FeedItem>

    protected lateinit var feed: Feed
        private set
    protected var itemCache: List<FeedItem>? = null
    protected inline val settings: Settings
        get() = feed.settings

    fun get(): List<FeedItem> = itemCache ?: getUpdated().also {
        itemCache = it
    }

    fun preInit(feed: Feed) {
        this.feed = feed
    }

    fun init() {
        itemCache = getUpdated()
        onInit()
    }

    fun update() {
        itemCache = getUpdated()
        feed.update()
    }
}
