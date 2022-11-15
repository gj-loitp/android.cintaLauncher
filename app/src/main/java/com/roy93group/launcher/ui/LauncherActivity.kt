package com.roy93group.launcher.ui

import android.Manifest
import android.app.Activity
import android.app.WallpaperColors
import android.app.WallpaperManager
import android.content.Intent
import android.content.pm.LauncherApps
import android.content.pm.PackageManager
import android.content.res.Configuration
import android.graphics.*
import android.graphics.drawable.*
import android.os.Build
import android.os.Bundle
import android.view.*
import androidx.annotation.RequiresApi
import androidx.core.app.ActivityCompat
import androidx.core.view.updateLayoutParams
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.loitpcore.annotation.IsAutoAnimation
import com.loitpcore.annotation.IsFullScreen
import com.loitpcore.annotation.LogTag
import com.loitpcore.core.base.BaseFontActivity
import com.roy93group.launcher.LauncherContext
import com.roy93group.launcher.R
import com.roy93group.launcher.data.feed.items.FeedItem
import com.roy93group.launcher.providers.app.AppCallback
import com.roy93group.launcher.providers.app.AppCollection
import com.roy93group.launcher.providers.color.pallete.ColorPalette
import com.roy93group.launcher.providers.color.theme.ColorTheme
import com.roy93group.launcher.providers.feed.notification.MediaProvider
import com.roy93group.launcher.providers.feed.notification.NotificationProvider
import com.roy93group.launcher.providers.feed.rss.RssProvider
import com.roy93group.launcher.providers.feed.suggestions.SuggestedAppsProvider
import com.roy93group.launcher.providers.feed.suggestions.SuggestionsManager
import com.roy93group.launcher.storage.*
import com.roy93group.launcher.storage.ColorExtractorSetting.colorTheme
import com.roy93group.launcher.ui.bottomBar.BottomBar
import com.roy93group.launcher.ui.drawer.AppDrawer
import com.roy93group.launcher.ui.feed.FeedAdapter
import com.roy93group.launcher.ui.feedProfiles.FeedProfiles
import com.roy93group.launcher.ui.popup.PopupUtils
import com.roy93group.launcher.ui.popup.appItem.ItemLongPress
import com.roy93group.launcher.util.StackTraceActivity
import com.roy93group.launcher.util.blur.AcrylicBlur
import io.posidon.android.conveniencelib.getNavigationBarHeight
import io.posidon.android.launcherutils.LiveWallpaper
import kotlin.concurrent.thread
import kotlin.math.abs

var acrylicBlur: AcrylicBlur? = null
    private set

@LogTag("LauncherActivity")
@IsFullScreen(false)
@IsAutoAnimation(false)
class LauncherActivity : BaseFontActivity() {

    companion object {
        fun Activity.loadBlur(wallpaperManager: WallpaperManager, updateBlur: () -> Unit) =
            thread(isDaemon = true, name = "Blur thread") {
                if (ActivityCompat.checkSelfPermission(
                        /* context = */ this,
                        /* permission = */ Manifest.permission.READ_EXTERNAL_STORAGE
                    ) != PackageManager.PERMISSION_GRANTED
                ) {
                    if (acrylicBlur == null) return@thread
                    acrylicBlur = null
                    runOnUiThread(updateBlur)
                    return@thread
                }
                val drawable = wallpaperManager.peekDrawable()
                if (drawable == null) {
                    if (acrylicBlur == null) return@thread
                    acrylicBlur = null
                    runOnUiThread(updateBlur)
                    return@thread
                }
                AcrylicBlur.blurWallpaper(context = this, drawable = drawable) {
                    acrylicBlur = it
                    runOnUiThread(updateBlur)
                }
            }
    }

    val launcherContext = LauncherContext()
    val settings by launcherContext::settings
    private val notificationProvider = NotificationProvider(this)
    private val mediaProvider = MediaProvider(this)
    private val suggestedAppsProvider = SuggestedAppsProvider()
    private val homeContainer: View by lazy {
        findViewById(R.id.flHomeContainer)
    }
    val feedRecycler: RecyclerView by lazy {
        findViewById(R.id.rvFeed)
    }
    val blurBG: View by lazy {
        findViewById(R.id.blurBg)
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
    private lateinit var wallpaperManager: WallpaperManager
//    private var colorThemeOptions = ColorThemeOptions(settings.colorThemeDayNight)

    override fun setLayoutResourceId(): Int {
        return R.layout.activity_launcher
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        StackTraceActivity.init(applicationContext)
        configureWindow()
        settings.init(applicationContext)
//        colorThemeOptions = ColorThemeOptions(settings.colorThemeDayNight)
        wallpaperManager = WallpaperManager.getInstance(this)

        feedRecycler.layoutManager = LinearLayoutManager(
            /* context = */ this,
            /* orientation = */RecyclerView.VERTICAL,
            /* reverseLayout = */false
        )
        feedAdapter = FeedAdapter(this)
        feedAdapter.setHasStableIds(true)
        feedRecycler.setItemViewCacheSize(20)
        feedRecycler.adapter = feedAdapter

        launcherContext.feed.init(
            settings,
            notificationProvider,
            RssProvider,
            mediaProvider,
            suggestedAppsProvider,
            onUpdate = this::loadFeed
        ) {
            runOnUiThread {
                feedAdapter.onFeedInitialized()
            }
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
        launcherApps.registerCallback(AppCallback(::loadApps))

        loadApps()

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O_MR1) {
            wallpaperManager.addOnColorsChangedListener(
                ::onColorsChangedListener,
                feedRecycler.handler
            )
            thread(name = "onCreate color update", isDaemon = true) {
                ColorPalette.onColorsChanged(
                    context = this,
                    colorTheme = settings.colorTheme,
                    onFinished = LauncherActivity::updateColorTheme
                ) {
                    wallpaperManager.getWallpaperColors(WallpaperManager.FLAG_SYSTEM)
                }
            }
            onWallpaperChanged()
        }
    }

    override fun onBaseBackPressed() {
        if (appDrawer.isOpen) appDrawer.close()
        else feedRecycler.scrollToPosition(0)
    }

//    override fun onBackPressed() {
//        if (appDrawer.isOpen) appDrawer.close()
//        else feedRecycler.scrollToPosition(0)
//    }

    private var lastUpdateTime = System.currentTimeMillis()

    override fun onResume() {
        super.onResume()
        SuggestionsManager.onResume(this) {
            suggestedAppsProvider.update()
        }
        val shouldUpdate = settings.reload(applicationContext)
        if (shouldUpdate) {
            loadApps()
        }
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.O_MR1) {
            thread(isDaemon = true) {
                ColorPalette.onResumePreOMR1(
                    context = this,
                    colorTheme = settings.colorTheme,
                    onFinished = LauncherActivity::updateColorTheme
                )
                onWallpaperChanged()
            }
        } else {
            if (acrylicBlur == null) {
                loadBlur(wallpaperManager, ::updateBlur)
            }
        }
        val current = System.currentTimeMillis()
        if (shouldUpdate || current - lastUpdateTime > 1000L * 60L * 5L) {
            lastUpdateTime = current
            RssProvider.update()
        }
    }

    override fun onPause() {
        super.onPause()
        if (appDrawer.isOpen) {
            appDrawer.close()
        }
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
            handleGestureContract(intent)
        }
    }

    fun updateBlur() {
        blurBG.background = acrylicBlur?.let { b ->
            LayerDrawable(
                arrayOf(
                    BitmapDrawable(resources, b.partialBlurSmall),
                    BitmapDrawable(resources, b.partialBlurMedium),
                    BitmapDrawable(resources, b.fullBlur),
                    BitmapDrawable(resources, b.insaneBlur).also {
                        it.alpha = 160
                    },
                )
            )
        }
        bottomBar.blurBG.drawable = acrylicBlur?.smoothBlur?.let { BitmapDrawable(resources, it) }
        homeContainer.background = acrylicBlur?.let { b ->
            LayerDrawable(
                arrayOf(
                    BitmapDrawable(resources, b.smoothBlur).also {
                        it.alpha = 100
                    },
                )
            )
        }
    }

    private fun updateColorTheme(colorPalette: ColorPalette) {
//        colorThemeOptions = ColorThemeOptions(settings.colorThemeDayNight)
//        ColorTheme.updateColorTheme(colorThemeOptions.createColorTheme(colorPalette))
        runOnUiThread {
            feedAdapter.updateColorTheme()
            feedRecycler.setBackgroundColor(ColorTheme.uiBG and 0xffffff or 0xbb000000.toInt())
            feedProfiles.updateColorTheme()
            updateBlur()
            appDrawer.updateColorTheme()
            bottomBar.updateColorTheme()
        }
    }

    @RequiresApi(Build.VERSION_CODES.O_MR1)
    fun onColorsChangedListener(
        colors: WallpaperColors?,
        which: Int
    ) {
        if (which and WallpaperManager.FLAG_SYSTEM != 0) {
            onWallpaperChanged()
            ColorPalette.onColorsChanged(
                context = this,
                colorTheme = settings.colorTheme,
                onFinished = LauncherActivity::updateColorTheme
            ) { colors }
        }
    }

    fun reloadColorThemeSync() {
//        colorThemeOptions = ColorThemeOptions(settings.colorThemeDayNight)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O_MR1) {
            ColorPalette.onColorsChanged(
                context = this,
                colorTheme = settings.colorTheme,
                onFinished = LauncherActivity::updateColorTheme
            ) {
                wallpaperManager.getWallpaperColors(WallpaperManager.FLAG_SYSTEM)
            }
        } else ColorPalette.onResumePreOMR1(
            context = this,
            colorTheme = settings.colorTheme,
            onFinished = LauncherActivity::updateColorTheme
        )
    }

    private fun onWallpaperChanged() {
        loadBlur(wallpaperManager = wallpaperManager, updateBlur = ::updateBlur)
    }

    private fun handleGestureContract(intent: Intent) {
        //val gnc = GestureNavContract.fromIntent(intent)
        //gnc?.sendEndPosition(scrollBar.clipBounds.toRectF(), null)
    }

    private fun loadFeed(items: List<FeedItem>) {
        runOnUiThread {
            feedAdapter.updateItems(items)
//            Log.d("Cinta", "updated feed (${items.size} items)")
        }
    }

    fun loadApps() {
        launcherContext.appManager.loadApps(this) { apps: AppCollection ->
            suggestedAppsProvider.update()
            bottomBar.appDrawerIcon.reloadController(settings)
            bottomBar.scrollBar.controller.loadSections(apps)
            appDrawer.update(bottomBar.scrollBar, apps.sections)
            runOnUiThread {
                bottomBar.onAppsLoaded()
            }
//            Log.d("Cinta", "updated apps (${apps.size} items)")
        }
    }

    private fun onTouch(v: View, event: MotionEvent): Boolean {
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
            getNavigationBarHeight() + resources.getDimension(R.dimen.item_card_margin).toInt()
        val feedFilterY = searchBarY + resources.getDimension(R.dimen.search_bar_height).toInt()

        bottomBar.view.updateLayoutParams<ViewGroup.MarginLayoutParams> {
            bottomMargin = searchBarY
        }

        feedProfiles.feedFilterRecycler.updateLayoutParams<ViewGroup.MarginLayoutParams> {
            bottomMargin = feedFilterY
        }
    }

    fun getFeedBottomMargin(): Int {
        val searchBarY =
            getNavigationBarHeight() + resources.getDimension(R.dimen.item_card_margin).toInt()
        val feedFilterY = searchBarY + resources.getDimension(R.dimen.search_bar_height).toInt()
        return feedFilterY + resources.getDimension(R.dimen.feed_filter_height).toInt()
    }
}
