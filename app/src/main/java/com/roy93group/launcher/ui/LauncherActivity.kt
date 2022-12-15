package com.roy93group.launcher.ui

import android.content.Intent
import android.content.pm.LauncherApps
import android.content.res.Configuration
import android.graphics.*
import android.graphics.drawable.*
import android.os.Build
import android.os.Bundle
import android.view.*
import androidx.core.view.updateLayoutParams
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.ktx.Firebase
import com.google.firebase.remoteconfig.ktx.get
import com.google.firebase.remoteconfig.ktx.remoteConfig
import com.google.firebase.remoteconfig.ktx.remoteConfigSettings
import com.loitpcore.annotation.IsAutoAnimation
import com.loitpcore.annotation.IsFullScreen
import com.loitpcore.annotation.IsKeepScreenOn
import com.loitpcore.annotation.LogTag
import com.loitpcore.core.base.BaseFontActivity
import com.loitpcore.core.utilities.LUIUtil
import com.roy93group.app.C
import com.roy93group.launcher.LauncherContext
import com.roy93group.launcher.R
import com.roy93group.launcher.data.feed.items.FeedItem
import com.roy93group.launcher.providers.app.AppCallback
import com.roy93group.launcher.providers.app.AppCollection
import com.roy93group.launcher.providers.feed.notification.MediaProvider
import com.roy93group.launcher.providers.feed.notification.NotificationProvider
import com.roy93group.launcher.providers.feed.rss.RssProvider
import com.roy93group.launcher.providers.feed.suggestions.SuggestedAppsProvider
import com.roy93group.launcher.providers.feed.suggestions.SuggestionsManager
import com.roy93group.launcher.storage.*
import com.roy93group.launcher.ui.bottomBar.BottomBar
import com.roy93group.launcher.ui.drawer.AppDrawer
import com.roy93group.launcher.ui.feed.FeedAdapter
import com.roy93group.launcher.ui.feedProfiles.FeedProfiles
import com.roy93group.launcher.ui.popup.PopupUtils
import com.roy93group.launcher.ui.popup.appItem.ItemLongPress
import com.roy93group.launcher.util.StackTraceActivity
import io.posidon.android.conveniencelib.getNavigationBarHeight
import io.posidon.android.launcherutils.LiveWallpaper
import kotlin.math.abs

/**
 * Updated by Loitp on 2022.12.16
 * Galaxy One company,
 * Vietnam
 * +840766040293
 * freuss47@gmail.com
 */
@LogTag("LauncherActivity")
@IsFullScreen(false)
@IsAutoAnimation(false)
@IsKeepScreenOn(false)
class LauncherActivity : BaseFontActivity() {

    val launcherContext = LauncherContext()
    val settings by launcherContext::settings
    private val notificationProvider = NotificationProvider(this)
    private val mediaProvider = MediaProvider(this)
    private val suggestedAppsProvider = SuggestedAppsProvider()

    @Suppress("unused")
    private val flHomeContainer: View by lazy {
        findViewById(R.id.flHomeContainer)
    }
    val rvFeed: RecyclerView by lazy {
        findViewById(R.id.rvFeed)
    }
    val appDrawer by lazy {
        AppDrawer(this)
    }
    val bottomBar by lazy {
        BottomBar(this)
    }
    val feedProfiles by lazy {
        FeedProfiles(this)
    }
    private lateinit var feedAdapter: FeedAdapter

    override fun setLayoutResourceId(): Int {
        return R.layout.activity_launcher
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        fetchRemoteConfig()
        StackTraceActivity.init(applicationContext)
        configureWindow()
        settings.init(applicationContext)

        feedAdapter = FeedAdapter(this).apply {
            setHasStableIds(true)
        }
        rvFeed.apply {
            LUIUtil.setScrollChange(
                recyclerView = this,
                onTop = {
                    C.goToSearchScreen(context)
                },
                onBottom = {
                    logE("onBottom")
                },
                onScrolled = { isScrollDown ->
                    logE("onScrolled isScrollDown $isScrollDown")
                },
            )
            layoutManager = LinearLayoutManager(
                /* context = */ this@LauncherActivity,
                /* orientation = */RecyclerView.VERTICAL,
                /* reverseLayout = */false
            )
            setItemViewCacheSize(20)
            adapter = feedAdapter
        }

        launcherContext.feed.init(
            settings = settings,
            notificationProvider,
            RssProvider,
            mediaProvider,
            suggestedAppsProvider,
            onUpdate = this::loadFeed
        ) {
        }

        appDrawer.init()
        window.decorView.findViewById<View>(android.R.id.content).run {
            setOnTouchListener(::onTouch)
            setOnDragListener { _, event ->
                when (event.action) {
                    DragEvent.ACTION_DRAG_STARTED -> {
                        val v = (event.localState as? View?)
                        v?.visibility = View.INVISIBLE
                        return@setOnDragListener true
                    }
                    DragEvent.ACTION_DRAG_LOCATION -> {
                        val v = (event.localState as? View?)
                        if (v != null) {
                            val location = IntArray(2)
                            v.getLocationOnScreen(location)
                            val x = abs(event.x - location[0] - v.measuredWidth / 2f)
                            val y = abs(event.y - location[1] - v.measuredHeight / 2f)
                            if (x > v.width / 3.5f || y > v.height / 3.5f) {
                                ItemLongPress.currentPopup?.dismiss()
                            }
                        }
                    }
                    DragEvent.ACTION_DRAG_ENDED,
                    DragEvent.ACTION_DROP -> {
                        val v = (event.localState as? View?)
                        v?.visibility = View.VISIBLE
                        ItemLongPress.currentPopup?.isFocusable = true
                        ItemLongPress.currentPopup?.update()
                    }
                }
                false
            }
        }

        val launcherApps = getSystemService(LauncherApps::class.java)
        launcherApps.registerCallback(AppCallback(callback = ::loadApps))

        loadApps()
    }

    override fun onBaseBackPressed() {
        if (appDrawer.isOpen) {
            appDrawer.close()
        } else {
            appDrawer.open()
//            rvFeed.scrollToPosition(0)
        }
    }

    private var lastUpdateTime = System.currentTimeMillis()

    override fun onResume() {
        super.onResume()
        SuggestionsManager.onResume(context = this) {
            suggestedAppsProvider.update()
        }
        val shouldUpdate = settings.reload(applicationContext)
        if (shouldUpdate) {
            loadApps()
        }
        val current = System.currentTimeMillis()
        if (shouldUpdate || current - lastUpdateTime > 1000L * 60L * 5L) {
            lastUpdateTime = current
            RssProvider.update()
        }
    }

    override fun onPause() {
        super.onPause()
//        if (appDrawer.isOpen) {
//            appDrawer.close()
//        }
        PopupUtils.dismissCurrent()
        SuggestionsManager.onPause(settings = settings, context = this)
    }

    override fun onWindowFocusChanged(hasFocus: Boolean) {
        if (hasFocus) {
            notificationProvider.update()
        }
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        configureWindow()
    }

    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
        val isActionMain = Intent.ACTION_MAIN == intent.action
        if (isActionMain) {
            handleGestureContract()
        }
    }

    private fun handleGestureContract() {
        //val gnc = GestureNavContract.fromIntent(intent)
        //gnc?.sendEndPosition(scrollBar.clipBounds.toRectF(), null)
    }

    private fun loadFeed(items: List<FeedItem>) {
        runOnUiThread {
            feedAdapter.updateItems(items)
        }
    }

    fun loadApps() {
        launcherContext.appManager.loadApps(this) { apps: AppCollection ->
            suggestedAppsProvider.update()
            bottomBar.appDrawerIcon.reloadController(settings)
            bottomBar.scrollBar.controller.loadSections(apps)
            appDrawer.update(scrollBar = bottomBar.scrollBar, appSections = apps.sections)
        }
    }

    private fun onTouch(
        v: View,
        event: MotionEvent
    ): Boolean {
        if (event.action == MotionEvent.ACTION_UP)
            LiveWallpaper.tap(view = v, x = event.rawX.toInt(), y = event.rawY.toInt())
        return false
    }

    private fun configureWindow() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R)
            window.setDecorFitsSystemWindows(false)
        else window.setFlags(
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
        )
        val searchBarY =
            getNavigationBarHeight() + resources.getDimension(R.dimen.margin_padding_medium).toInt()
        val feedFilterY = searchBarY + resources.getDimension(R.dimen.search_bar_height).toInt()

        bottomBar.cvSearchBarContainer.updateLayoutParams<ViewGroup.MarginLayoutParams> {
            bottomMargin = searchBarY
        }

        feedProfiles.rvFeedFilters.updateLayoutParams<ViewGroup.MarginLayoutParams> {
            bottomMargin = feedFilterY
        }
    }

    fun getFeedBottomMargin(): Int {
        val searchBarY =
            getNavigationBarHeight() + resources.getDimension(R.dimen.margin_padding_medium).toInt()
        val feedFilterY = searchBarY + resources.getDimension(R.dimen.search_bar_height).toInt()
        return feedFilterY + resources.getDimension(R.dimen.w_40).toInt()
    }

    private fun fetchRemoteConfig() {
        val remoteConfig = Firebase.remoteConfig
        val configSettings = remoteConfigSettings {
            minimumFetchIntervalInSeconds = 3600
        }
        remoteConfig.setConfigSettingsAsync(configSettings)
        remoteConfig.setDefaultsAsync(R.xml.remote_config_defaults)

        remoteConfig.fetchAndActivate()
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    val isShowFlickrGallery = remoteConfig["is_show_flickr_gallery"].asBoolean()
                    logE("is_show_flickr_gallery: $isShowFlickrGallery")
                } else {
                    logE("Fetch failed")
                }
            }
    }
}
