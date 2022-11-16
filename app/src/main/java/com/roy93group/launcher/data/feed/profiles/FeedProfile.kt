package com.roy93group.launcher.data.feed.profiles

import android.graphics.drawable.Drawable
import com.roy93group.launcher.data.feed.items.FeedItem
import com.roy93group.launcher.data.feed.items.FeedItemSuggestedApps
import com.roy93group.launcher.data.feed.items.FeedItemWithMedia
import com.roy93group.launcher.data.feed.items.isToday

/**
 * Updated by Loitp on 2022.12.16
 * Galaxy One company,
 * Vietnam
 * +840766040293
 * freuss47@gmail.com
 */
class FeedProfile(
    val name: String? = null,
    val icon: Drawable? = null,
    val showAppSuggestions: Boolean,
    val showMedia: Boolean,
    val showNews: Boolean,
    val showNotifications: Boolean,
    val onlyToday: Boolean,
    private val extraFeedProfileSettings: ExtraFeedProfileSettings? = null,
) {
    fun filter(item: FeedItem): Boolean {
        val today = !onlyToday || item.isToday()
        val suggestions = showAppSuggestions || item !is FeedItemSuggestedApps
        val media = showMedia || item !is FeedItemWithMedia
        val notification = showNotifications || item.meta?.isNotification != true
        val news = showNews || item.meta?.isNotification == true
        return today && suggestions && media && notification && news && extraFeedProfileSettings?.filter(
            item = item
        ) ?: true
    }
}
