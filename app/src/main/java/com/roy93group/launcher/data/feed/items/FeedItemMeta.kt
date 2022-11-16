package com.roy93group.launcher.data.feed.items

import androidx.annotation.Keep

/**
 * Updated by Loitp on 2022.12.16
 * Galaxy One company,
 * Vietnam
 * +840766040293
 * freuss47@gmail.com
 */
@Keep
class FeedItemMeta(
    val sourcePackageName: String? = null,
    val sourceUrl: String? = null,
    val importance: Int = 0,
    val isNotification: Boolean = false
)
