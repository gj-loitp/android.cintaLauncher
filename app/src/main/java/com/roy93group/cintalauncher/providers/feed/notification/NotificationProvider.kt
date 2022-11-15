package com.roy93group.cintalauncher.providers.feed.notification

import android.content.Context
import com.roy93group.cintalauncher.data.feed.items.FeedItem
import com.roy93group.cintalauncher.providers.feed.FeedItemProvider

class NotificationProvider(val context: Context) : FeedItemProvider() {

    override fun onInit() {
        NotificationService.init(context)
        NotificationService.setOnUpdate(javaClass.name, ::update)
    }

    override fun getUpdated(): List<FeedItem> {
        return NotificationService.notifications
    }
}

