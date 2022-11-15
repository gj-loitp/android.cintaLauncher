package com.roy93group.launcher.providers.feed

import com.roy93group.launcher.data.feed.items.FeedItem
import com.roy93group.launcher.data.feed.profiles.FeedProfile
import com.roy93group.launcher.storage.Settings
import java.util.concurrent.locks.ReentrantLock
import kotlin.concurrent.thread
import kotlin.concurrent.withLock

class Feed {
    companion object {
        const val MAX_ITEMS_HINT = 36
    }

    private var profile: FeedProfile? = null
    private var onUpdate: (List<FeedItem>) -> Unit = {}
    private lateinit var providers: Array<out FeedItemProvider>
    lateinit var settings: Settings
        private set
    private val lock = ReentrantLock()
    private var itemCache: List<FeedItem>? = null

    fun init(
        settings: Settings,
        vararg providers: FeedItemProvider,
        onUpdate: (List<FeedItem>) -> Unit,
        onDone: () -> Unit
    ) {
        this.onUpdate = onUpdate
        this.providers = providers
        this.settings = settings
        providers.forEach {
            it.preInit(feed = this)
        }
        thread(name = "Feed init thread") {
            lock.withLock {
                providers.forEach {
                    it.init()
                }
            }
            onDone()
        }
    }

    private fun List<FeedItem>.filtered(): List<FeedItem> {
        return profile?.let { filter(it::filter) } ?: this
    }

    fun setProfile(profile: FeedProfile) {
        if (this.profile == profile) return
        this.profile = profile
        itemCache?.filtered()?.also(onUpdate) ?: update()
    }

    fun update() {
        thread(name = "Feed update thread", isDaemon = true) {
            val items = lock.withLock {
                ArrayList<FeedItem>().apply {
                    providers.forEach {
                        addAll(it.get())
                    }
                }
            }
            itemCache = FeedSorter.rearrange(items).also {
                it.filtered().also(onUpdate)
            }
        }
    }
}
