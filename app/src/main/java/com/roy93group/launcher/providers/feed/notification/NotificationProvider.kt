package com.roy93group.launcher.providers.feed.notification

import android.content.Context
import com.roy93group.launcher.data.feed.items.FeedItem
import com.roy93group.launcher.providers.feed.FeedItemProvider

class NotificationProvider(val context: Context) : FeedItemProvider() {

    override fun onInit() {
        NotificationService.init(context)
        NotificationService.setOnUpdate(javaClass.name, ::update)
    }

    override fun getUpdated(): List<FeedItem> {
        return NotificationService.notifications
    }
}

