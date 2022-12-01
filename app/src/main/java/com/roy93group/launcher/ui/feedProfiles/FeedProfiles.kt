package com.roy93group.launcher.ui.feedProfiles

import androidx.appcompat.content.res.AppCompatResources
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.roy93group.launcher.R
import com.roy93group.launcher.data.feed.profiles.FeedProfile
import com.roy93group.launcher.ui.LauncherActivity

/**
 * Updated by Loitp on 2022.12.17
 * Galaxy One company,
 * Vietnam
 * +840766040293
 * freuss47@gmail.com
 */
class FeedProfiles(val activity: LauncherActivity) {

    private val feedFilterAdapter = FeedProfileAdapter(activity.launcherContext)
    val rvFeedFilters: RecyclerView =
        activity.findViewById<RecyclerView>(R.id.rvFeedFilters).apply {
            layoutManager = LinearLayoutManager(
                /* context = */ activity,
                /* orientation = */RecyclerView.HORIZONTAL,
                /* reverseLayout = */false
            )
            adapter = feedFilterAdapter
        }

    init {
        feedFilterAdapter.updateItems(
            FeedProfile(
                icon = AppCompatResources.getDrawable(activity, R.drawable.ic_home),
                showAppSuggestions = true,
                showMedia = true,
                showNews = true,
                showNotifications = true,
                onlyToday = false,
            ),
            FeedProfile(
                name = activity.getString(R.string.today),
                icon = AppCompatResources.getDrawable(activity, R.drawable.ic_lightness),
                showAppSuggestions = true,
                showMedia = true,
                showNews = true,
                showNotifications = true,
                onlyToday = true,
            ),
            FeedProfile(
                name = activity.getString(R.string.news),
                icon = AppCompatResources.getDrawable(activity, R.drawable.ic_news),
                showAppSuggestions = false,
                showMedia = false,
                showNews = true,
                showNotifications = false,
                onlyToday = false,
            ),
            FeedProfile(
                name = activity.getString(R.string.notifications),
                icon = AppCompatResources.getDrawable(activity, R.drawable.ic_notification),
                showAppSuggestions = false,
                showMedia = false,
                showNews = false,
                showNotifications = true,
                onlyToday = false,
            ),
        )
    }
}
