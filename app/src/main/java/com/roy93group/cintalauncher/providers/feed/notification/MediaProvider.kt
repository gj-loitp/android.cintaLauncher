package com.roy93group.cintalauncher.providers.feed.notification

import android.content.Context
import com.roy93group.cintalauncher.data.feed.items.FeedItem
import com.roy93group.cintalauncher.providers.feed.FeedItemProvider

class MediaProvider(val context: Context) : FeedItemProvider() {

    override fun onInit() {
        NotificationService.init(context = context)
        NotificationService.setOnUpdate(key = javaClass.name, onUpdate = ::update)
    }

    override fun getUpdated(): List<FeedItem> {
        return NotificationService.mediaItem?.let(::listOf) ?: emptyList()
    }
}
