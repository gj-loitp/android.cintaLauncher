package com.roy93group.launcher

import android.content.Context
import com.roy93group.launcher.data.items.App
import com.roy93group.launcher.data.items.LauncherItem
import com.roy93group.launcher.providers.app.AppCollection
import com.roy93group.launcher.providers.feed.Feed
import com.roy93group.launcher.providers.feed.suggestions.SuggestionsManager
import com.roy93group.launcher.storage.Settings
import io.posidon.android.launcherutils.AppLoader
import io.posidon.android.launcherutils.IconConfig

/**
 * Updated by Loitp on 2022.12.16
 * Galaxy One company,
 * Vietnam
 * +840766040293
 * freuss47@gmail.com
 */
class LauncherContext {

    companion object {
        private const val PINNED_KEY = "pinned_items"
    }

    val feed = Feed()
    val settings = Settings()
    val appManager = AppManager()

    inner class AppManager {

        val pinnedItems: List<LauncherItem> get() = _pinnedItems

        fun <T : Context> loadApps(context: T, onEnd: T.(apps: AppCollection) -> Unit) {
            val iconConfig = IconConfig(
                size = (context.resources.displayMetrics.density * 128f).toInt(),
                density = context.resources.configuration.densityDpi,
                packPackages = settings.getStrings("icon_packs") ?: emptyArray(),
            )

            appLoader.async(context, iconConfig) { ac ->
                appsByName = ac.byName
                _pinnedItems = settings.getStrings(PINNED_KEY)
                    ?.mapNotNull {
                        LauncherItem.parse(it, appsByName)
                    }?.toMutableList()
                    ?: ArrayList()
                SuggestionsManager.onAppsLoaded(this, context, settings)
                onEnd(context, ac)
            }
        }

        fun parseLauncherItem(string: String): LauncherItem? {
            return App.parse(string, appsByName)
        }

        @Suppress("unused")
        fun getAppByPackage(packageName: String): LauncherItem? = appsByName[packageName]?.first()

        @Suppress("unused")
        fun pinItem(context: Context, launcherItem: LauncherItem, i: Int) {
            _pinnedItems.add(i, launcherItem)
            settings.edit(context) {
                val s = launcherItem.toString()
                PINNED_KEY set (settings.getStrings(PINNED_KEY)
                    ?.toMutableList()
                    ?.apply { add(i, s) }
                    ?.toTypedArray()
                    ?: arrayOf(s))
            }
        }

        @Suppress("unused")
        fun unpinItem(context: Context, i: Int) {
            _pinnedItems.removeAt(i)
            settings.edit(context) {
                PINNED_KEY set (settings.getStrings(PINNED_KEY)
                    ?.toMutableList()
                    ?.apply { removeAt(i) }
                    ?.toTypedArray()
                    ?: throw IllegalStateException("Can't unpin an item when no items are pinned"))
            }
        }

        fun setPinned(context: Context, pinned: List<LauncherItem>) {
            _pinnedItems = pinned.toMutableList()
            settings.edit(context) {
                PINNED_KEY set pinned.map(LauncherItem::toString).toTypedArray()
            }
        }

        private val appLoader = AppLoader { AppCollection(it, settings) }

        private var appsByName = HashMap<String, MutableList<App>>()

        private var _pinnedItems: MutableList<LauncherItem> = ArrayList()
    }
}
