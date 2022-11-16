package com.roy93group.launcher.data.feed.profiles

import com.roy93group.launcher.data.feed.items.FeedItem

/**
 * Updated by Loitp on 2022.12.16
 * Galaxy One company,
 * Vietnam
 * +840766040293
 * freuss47@gmail.com
 */
class ExtraFeedProfileSettings(
    private val onlyTheseSources: List<String>?,
    private val onlyThesePackages: List<String>?,
) {
    fun filter(item: FeedItem): Boolean {
        val packageName = item.meta?.sourcePackageName
        val source = item.meta?.sourceUrl

        if (onlyThesePackages != null && packageName != null) {
            return packageName in onlyThesePackages
        }

        if (onlyTheseSources != null && source != null) {
            return source in onlyTheseSources
        }

        return true
    }
}
