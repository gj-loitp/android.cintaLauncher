package com.roy93group.launcher.data.feed.items

import android.view.View
import android.widget.ImageView

/**
 * Updated by Loitp on 2022.12.16
 * Galaxy One company,
 * Vietnam
 * +840766040293
 * freuss47@gmail.com
 */
interface FeedItemWithMedia : FeedItemWithBigImage {
    fun previous(v: View)
    fun next(v: View)
    fun togglePause(v: ImageView)
    fun isPlaying(): Boolean
}
