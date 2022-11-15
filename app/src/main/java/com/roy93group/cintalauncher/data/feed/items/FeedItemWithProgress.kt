package com.roy93group.cintalauncher.data.feed.items

interface FeedItemWithProgress : FeedItem {
    val max: Int
    val progress: Int
    val isIntermediate: Boolean
}
