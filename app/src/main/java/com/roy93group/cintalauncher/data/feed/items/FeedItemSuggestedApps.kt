package com.roy93group.cintalauncher.data.feed.items

import com.roy93group.cintalauncher.data.items.LauncherItem

interface FeedItemSuggestedApps : FeedItem {
    val apps: List<LauncherItem>
}
