package com.roy93group.launcher.data.feed.items

interface FeedItemWithProgress : FeedItem {
    val max: Int
    val progress: Int
    val isIntermediate: Boolean
}
