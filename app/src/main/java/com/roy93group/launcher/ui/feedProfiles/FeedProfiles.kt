package com.roy93group.launcher.ui.feedProfiles

import android.annotation.SuppressLint
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.roy93group.app.C
import com.roy93group.launcher.R
import com.roy93group.launcher.data.feed.profiles.FeedProfile
import com.roy93group.launcher.ui.LauncherActivity
import kotlinx.android.synthetic.main.activity_launcher.*

/**
 * Updated by Loitp on 2022.12.17
 * Galaxy One company,
 * Vietnam
 * +840766040293
 * freuss47@gmail.com
 */
class FeedProfiles(val launcherActivity: LauncherActivity) {

    private val feedFilterAdapter = FeedProfileAdapter(launcherActivity.launcherContext)
    val rvFeedFilters: RecyclerView =
        launcherActivity.rvFeedFilters.apply {
            layoutManager = LinearLayoutManager(
                /* context = */ launcherActivity,
                /* orientation = */RecyclerView.HORIZONTAL,
                /* reverseLayout = */false
            )
            adapter = feedFilterAdapter
        }

    init {
        feedFilterAdapter.updateItems(
            FeedProfile(
                icon = AppCompatResources.getDrawable(launcherActivity, R.drawable.ic_home),
                showAppSuggestions = true,
                showMedia = true,
                showNews = true,
                showNotifications = true,
                onlyToday = false,
            ),
            FeedProfile(
                name = launcherActivity.getString(R.string.today),
                icon = AppCompatResources.getDrawable(launcherActivity, R.drawable.ic_lightness),
                showAppSuggestions = true,
                showMedia = true,
                showNews = true,
                showNotifications = true,
                onlyToday = true,
            ),
            FeedProfile(
                name = launcherActivity.getString(R.string.notifications),
                icon = AppCompatResources.getDrawable(launcherActivity, R.drawable.ic_notification),
                showAppSuggestions = false,
                showMedia = false,
                showNews = false,
                showNotifications = true,
                onlyToday = false,
            ),
            FeedProfile(
                name = launcherActivity.getString(R.string.news),
                icon = AppCompatResources.getDrawable(launcherActivity, R.drawable.ic_news),
                showAppSuggestions = false,
                showMedia = false,
                showNews = true,
                showNotifications = false,
                onlyToday = false,
            ),
        )
    }

    @SuppressLint("NotifyDataSetChanged")
    fun updateTheme() {
        if (C.getDisplayFilterViews()) {
            rvFeedFilters.isVisible = true
            feedFilterAdapter.notifyDataSetChanged()
        } else {
            rvFeedFilters.isVisible = false
        }
    }
}
