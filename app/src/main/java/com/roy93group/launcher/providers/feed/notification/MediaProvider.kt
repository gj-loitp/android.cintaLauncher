package com.roy93group.launcher.providers.feed.notification

import android.content.Context
import com.roy93group.launcher.data.feed.items.FeedItem
import com.roy93group.launcher.providers.feed.FeedItemProvider

/**
 * Updated by Loitp on 2022.12.16
 * Galaxy One company,
 * Vietnam
 * +840766040293
 * freuss47@gmail.com
 */
class MediaProvider(val context: Context) : FeedItemProvider() {

    override fun onInit() {
        NotificationService.init(context = context)
        NotificationService.setOnUpdate(key = javaClass.name, onUpdate = ::update)
    }

    override fun getUpdated(): List<FeedItem> {
        return NotificationService.mediaItem?.let(::listOf) ?: emptyList()
    }
}
