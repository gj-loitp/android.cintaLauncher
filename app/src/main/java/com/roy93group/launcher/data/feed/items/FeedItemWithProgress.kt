package com.roy93group.launcher.data.feed.items

/**
 * Updated by Loitp on 2022.12.16
 * Galaxy One company,
 * Vietnam
 * +840766040293
 * freuss47@gmail.com
 */
interface FeedItemWithProgress : FeedItem {
    val max: Int
    val progress: Int
    val isIntermediate: Boolean
}
