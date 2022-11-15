package com.roy93group.cintalauncher.providers.feed.rss

import android.content.Intent
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.net.Uri
import android.view.View
import com.roy93group.cintalauncher.data.feed.items.FeedItem
import com.roy93group.cintalauncher.data.feed.items.FeedItemMeta
import com.roy93group.cintalauncher.data.feed.items.FeedItemWithBigImage
import com.roy93group.cintalauncher.data.feed.items.longHash
import com.roy93group.cintalauncher.providers.feed.AsyncFeedItemProvider
import com.roy93group.cintalauncher.providers.feed.Feed.Companion.MAX_ITEMS_HINT
import com.roy93group.cintalauncher.util.AsyncLoadDrawable
import com.roy93group.cintalauncher.util.ImageLoader
import io.posidon.android.rsslib.RSS
import io.posidon.android.rsslib.RssItem

object RssProvider : AsyncFeedItemProvider() {

    private val imageCache = HashMap<String, Drawable>()

    private fun loadBitmap(url: String) = imageCache.getOrPut(url) {
        AsyncLoadDrawable.load {
            BitmapDrawable(ImageLoader.loadNullableBitmapOnCurrentThread(url))
        }
    }

    private var urls = emptyList<String>()

    override fun shouldReload(): Boolean {
        val newUrls = settings.getStrings("feed:rss_sources")?.toList() ?: emptyList()
        if (urls != newUrls) {
            urls = newUrls
            return true
        }
        return false
    }

    override fun loadItems(): List<FeedItem> {
        val items = ArrayList<RssItem>()
        if (RSS.load(
                output = items,
                urls = settings.getStrings("feed:rss_sources")?.toList() ?: emptyList(),
                maxItemsPerURL = MAX_ITEMS_HINT
            ).isNotEmpty()
        ) {
            return emptyList()
        }
        items.sort()
        return items.map {
            val id = it.link.longHash()
            val meta = FeedItemMeta(
                sourceUrl = it.source.url
            )
            println(it.title)
            if (it.img == null) {
                object : FeedItem {
                    override val color =
                        if (it.source.accentColor == 0) 0 else (it.source.accentColor
                            ?: 0) or 0xff000000.toInt()
                    override val title = it.title
                    override val sourceIcon = it.source.iconUrl?.let(RssProvider::loadBitmap)
                    override val description: Nothing? = null
                    override val source: String = it.source.name
                    override val instant = it.time.toInstant()
                    override fun onTap(view: View) {
                        view.context.startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(uid)))
                    }

                    override val isDismissible = false
                    override val shouldTintIcon = false
                    override val uid = it.link.trim { it <= ' ' }
                    override val id = id
                    override val meta = meta
                }
            } else {
                object : FeedItemWithBigImage {
                    override val image = it.img!!
                    override val color =
                        if (it.source.accentColor == 0) 0 else (it.source.accentColor
                            ?: 0) or 0xff000000.toInt()
                    override val title = it.title
                    override val sourceIcon = it.source.iconUrl?.let(RssProvider::loadBitmap)
                    override val description: Nothing? = null
                    override val source: String = it.source.name
                    override val instant = it.time.toInstant()
                    override fun onTap(view: View) {
                        view.context.startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(uid)))
                    }

                    override val isDismissible = false
                    override val shouldTintIcon = false
                    override val uid = it.link.trim {
                        it <= ' '
                    }
                    override val id = id
                    override val meta = meta
                }
            }
        }
    }
}
