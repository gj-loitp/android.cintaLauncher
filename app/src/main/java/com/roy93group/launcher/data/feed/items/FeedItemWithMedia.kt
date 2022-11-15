package com.roy93group.launcher.data.feed.items

import android.view.View
import android.widget.ImageView

interface FeedItemWithMedia : FeedItemWithBigImage {
    fun previous(v: View)
    fun next(v: View)
    fun togglePause(v: ImageView)
    fun isPlaying(): Boolean
}
