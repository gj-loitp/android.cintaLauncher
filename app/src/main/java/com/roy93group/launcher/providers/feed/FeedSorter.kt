package com.roy93group.launcher.providers.feed

import com.roy93group.launcher.data.feed.items.FeedItem
import java.time.Instant

/**
 * Updated by Loitp on 2022.12.16
 * Galaxy One company,
 * Vietnam
 * +840766040293
 * freuss47@gmail.com
 */
object FeedSorter {
    fun rearrange(items: ArrayList<FeedItem>): List<FeedItem> {
        items.sortByDescending(selector = FeedSorter::sorter)
        return items
    }

    fun getMostRelevant(items: List<FeedItem>): FeedItem? {
        return items.maxByOrNull(selector = FeedSorter::sorter)
    }

    private fun sorter(it: FeedItem): Instant {
        var r = it.instant
        val m = it.meta
        if (m != null) {
            if (m.isNotification) r = r.plusMillis(3600L * 12)
            when (m.importance) {
                1 -> r = r.plusMillis(3600L * 2)
                2 -> r = r.plusMillis(3600L * 7)
            }
        }
        return r
    }
}
