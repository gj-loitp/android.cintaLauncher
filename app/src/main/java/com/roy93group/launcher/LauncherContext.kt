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

        private val appLoader = AppLoader { AppCollection(it, settings) }

        private var appsByName = HashMap<String, MutableList<App>>()

        private var _pinnedItems: MutableList<LauncherItem> = ArrayList()
    }
}
