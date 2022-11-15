package com.roy93group.launcher.data.feed.items

import com.roy93group.launcher.data.items.LauncherItem

interface FeedItemSuggestedApps : FeedItem {
    val apps: List<LauncherItem>
}
