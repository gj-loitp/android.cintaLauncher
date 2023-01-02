package com.roy93group.launcher.ui.popup.drawer

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.widget.PopupWindow
import androidx.appcompat.widget.ListPopupWindow
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.ktx.Firebase
import com.google.firebase.remoteconfig.ktx.get
import com.google.firebase.remoteconfig.ktx.remoteConfig
import com.loitp.core.base.BaseActivity
import com.loitp.core.ext.setSafeOnClickListener
import com.loitp.core.utilities.LActivityUtil
import com.loitp.core.utilities.LAppResource
import com.loitp.core.utilities.LSocialUtil
import com.loitp.core.utilities.LUIUtil
import com.roy93group.app.C
import com.roy93group.launcher.BuildConfig
import com.roy93group.launcher.R
import com.roy93group.launcher.storage.DoReshapeAdaptiveIconsSetting.doReshapeAdaptiveIcons
import com.roy93group.launcher.storage.ScrollbarControllerSetting.scrollbarController
import com.roy93group.launcher.storage.Settings
import com.roy93group.launcher.ui.LauncherActivity
import com.roy93group.launcher.ui.intro.IntroActivity
import com.roy93group.launcher.ui.popup.PopupUtils
import com.roy93group.launcher.ui.popup.listPopup.ListPopupAdapter
import com.roy93group.launcher.ui.popup.listPopup.ListPopupItem
import com.roy93group.launcher.ui.settings.iconPackPicker.IconPackPickerActivity
import io.posidon.android.conveniencelib.Device
import kotlinx.android.synthetic.main.view_list_popup.view.*

/**
 * Updated by Loitp on 2022.12.17
 * Galaxy One company,
 * Vietnam
 * +840766040293
 * freuss47@gmail.com
 */
object DrawerLongPressPopup {

    @SuppressLint("InflateParams")
    fun show(
        launcherActivity: LauncherActivity,
        parent: View,
        touchX: Float,
        touchY: Float,
        navbarHeight: Int,
        settings: Settings,
        reloadApps: () -> Unit,
    ) {
        val colorPrimary = C.getColorPrimary()
        val colorBackground = C.getColorBackground()

        val content = LayoutInflater.from(parent.context).inflate(R.layout.view_list_popup, null)
        val window = PopupWindow(
            /* contentView = */ content,
            /* width = */ListPopupWindow.MATCH_PARENT,
            /* height = */ListPopupWindow.WRAP_CONTENT,
            /* focusable = */true
        )
        PopupUtils.setCurrent(window)

        content.fabDismiss.apply {
            setColorFilter(colorBackground)
            C.setBackgroundTintList(this)
            setSafeOnClickListener {
                window.dismiss()
            }
        }

        content.card.apply {
            setCardBackgroundColor(colorBackground)
        }
        content.recyclerView.apply {
            layoutManager = LinearLayoutManager(
                /* context = */ context,
                /* orientation = */RecyclerView.VERTICAL,
                /* reverseLayout = */false
            )
            adapter = ListPopupAdapter().apply {
                fun update() {
                    updateItems(
                        createMainAdapter(
                            launcherActivity = launcherActivity,
                            context = parent.context,
                            settings = settings,
                            reloadScrollbarController = {
                                launcherActivity.runOnUiThread {
                                    reloadApps()
                                    update()
                                }
                            },
                            reloadApps = reloadApps,
                            onDismiss = {
                                window.dismiss()
                            }
                        )
                    )
                }
                update()
            }
        }

        val gravity = Gravity.CENTER
        val x = touchX.toInt() - Device.screenWidth(parent.context) / 2
        val y = touchY.toInt() - Device.screenHeight(parent.context) / 2 + navbarHeight
        window.showAtLocation(
            /* parent = */ parent,
            /* gravity = */gravity,
            /* x = */x,
            /* y = */y
        )
    }

    private fun createMainAdapter(
        launcherActivity: LauncherActivity,
        context: Context,
        settings: Settings,
        reloadScrollbarController: () -> Unit,
        reloadApps: () -> Unit,
        onDismiss: () -> Unit,
    ): List<ListPopupItem> {
        return listOf(
            ListPopupItem(text = context.getString(R.string.setting), isTitle = true),
            ListPopupItem(
                text = context.getString(R.string.choose_launcher),
                description = context.getString(R.string.default_home_app),
                icon = ContextCompat.getDrawable(context, R.drawable.ic_home),
            ) {
                C.chooseLauncher(launcherActivity)
            },
            ListPopupItem(
                text = context.getString(R.string.scrollbar_controller),
                description = context.resources.getStringArray(R.array.scrollbar_controllers)[settings.scrollbarController],
                icon = ContextCompat.getDrawable(context, R.drawable.ic_sorting),
            ) {
                onDismiss.invoke()
                C.launchSelector(
                    activity = launcherActivity,
                    isCancelableFragment = true,
                    title = LAppResource.getString(R.string.setting),
                    des = LAppResource.getString(R.string.app_sorting),
                    value0 = LAppResource.getString(R.string.alphabetic),
                    value1 = LAppResource.getString(R.string.by_hue),
                    firstIndexCheck = settings.scrollbarController,
                    onConfirm = { index ->
                        settings.edit(context) {
                            scrollbarController = index
                            reloadScrollbarController()
                        }
                    },
                    onDismiss = {
                        //do nothing
                    }
                )
            },
            ListPopupItem(text = context.getString(R.string.home_page), isTitle = true),
            ListPopupItem(
                text = context.getString(R.string.smart_search),
                description = context.getString(R.string.enable_search_when_scroll_to_top),
                icon = ContextCompat.getDrawable(context, R.drawable.ic_shapes),
                value = C.getOpenSearchWhenScrollTop(),
                onToggle = { _, value ->
                    C.setOpenSearchWhenScrollTop(value)
                }
            ),
            ListPopupItem(text = context.getString(R.string.theme), isTitle = true),
            ListPopupItem(
                text = context.getString(R.string.color),
                description = context.getString(R.string.pick_your_favorite_color),
                icon = ContextCompat.getDrawable(context, R.drawable.baseline_palette_black_48),
            ) {
                onDismiss.invoke()
                C.launchColorPrimary(activity = launcherActivity,
                    isCancelableFragment = true,
                    title = LAppResource.getString(R.string.color),
                    des = LAppResource.getString(R.string.pick_your_favorite_color),
                    warning = LAppResource.getString(R.string.the_color_launcher_will_be_restarted),
                    onDismiss = { newColor ->
                        val result = C.updatePrimaryColor(newColor)
                        if (result) {
                            launcherActivity.recreate()
                        }
                    })
            },
            ListPopupItem(
                text = context.getString(R.string.color_background),
                description = context.getString(R.string.pick_your_favorite_color_background),
                icon = ContextCompat.getDrawable(context, R.drawable.baseline_palette_black_48),
            ) {
                onDismiss.invoke()
                C.launchColorBackground(activity = launcherActivity,
                    isCancelableFragment = true,
                    title = LAppResource.getString(R.string.color_background),
                    des = LAppResource.getString(R.string.pick_your_favorite_color_background),
                    warning = LAppResource.getString(R.string.the_color_launcher_will_be_restarted),
                    onDismiss = { newColor ->
                        val result = C.updateBackgroundColor(newColor)
                        if (result) {
                            LUIUtil.setWallpaperAndLockScreen(
                                context = launcherActivity,
                                color = newColor,
                                isSetWallpaper = true,
                                isSetLockScreen = true,
                            )
                            launcherActivity.recreate()
                        }
                    })
            },
            ListPopupItem(
                text = context.getString(R.string.auto_color),
                icon = ContextCompat.getDrawable(context, R.drawable.ic_baseline_hdr_auto_24),
                value = C.getAutoColorChanger(),
                onToggle = { _, value ->
                    C.setAutoColorChanger(value)
                }
            ),
            ListPopupItem(
                text = context.getString(R.string.wallpaper),
                icon = ContextCompat.getDrawable(context, R.drawable.baseline_wallpaper_black_48),
            ) {
                val isShowFlickrGallery =
                    Firebase.remoteConfig["is_show_flickr_gallery"].asBoolean()
                if (isShowFlickrGallery || BuildConfig.DEBUG) {
                    C.launchWallpaper(launcherActivity)
                } else {
                    launcherActivity.showShortError(context.getString(R.string.err_unknown_en))
                }
            },
            ListPopupItem(
                text = context.getString(R.string.icon_packs),
                icon = ContextCompat.getDrawable(context, R.drawable.ic_shapes),
            ) {
                context.startActivity(Intent(context, IconPackPickerActivity::class.java))
                LActivityUtil.tranIn(context)
            },
            ListPopupItem(
                text = context.getString(R.string.reshape_adaptive_icons),
                description = context.getString(R.string.reshape_adaptive_icons_explanation),
                icon = ContextCompat.getDrawable(context, R.drawable.ic_shapes),
                value = settings.doReshapeAdaptiveIcons,
                onToggle = { _, value ->
                    settings.edit(context) {
                        doReshapeAdaptiveIcons = value
                        reloadApps()
                    }
                }
            ),
            ListPopupItem(
                text = context.getString(R.string.custom_app_drawer),
                description = context.getString(R.string.dynamic_drawer_for_your_apps),
                icon = ContextCompat.getDrawable(
                    context,
                    R.drawable.baseline_dashboard_customize_black_48
                ),
            ) {
                onDismiss.invoke()
                launcherActivity.appDrawer.customizeAppDrawer()
            },
            ListPopupItem(text = context.getString(R.string.other), isTitle = true),
            ListPopupItem(
                text = context.getString(R.string.policy_en),
                description = context.getString(R.string.read_policy),
                icon = ContextCompat.getDrawable(context, R.drawable.baseline_policy_black_48),
            ) {
                LSocialUtil.openBrowserPolicy(context)
            },
            ListPopupItem(
                text = context.getString(R.string.rate_app_en),
                description = context.getString(R.string.rate_5_stars),
                icon = ContextCompat.getDrawable(context, R.drawable.ic_thumb_up_alt_black_48dp),
            ) {
                LSocialUtil.rateApp(launcherActivity)
            },
            ListPopupItem(
                text = context.getString(R.string.more_app_en),
                icon = ContextCompat.getDrawable(context, R.drawable.ic_card_giftcard_black_48dp),
            ) {
                LSocialUtil.moreApp(launcherActivity)
            },
            ListPopupItem(
                text = context.getString(R.string.share_app_en),
                icon = ContextCompat.getDrawable(context, R.drawable.ic_share_black_48dp),
            ) {
                LSocialUtil.shareApp(launcherActivity)
            },
            ListPopupItem(
                text = context.getString(R.string.missing_a_feature),
                description = context.getString(R.string.missing_a_feature_msg),
                icon = ContextCompat.getDrawable(context, R.drawable.baseline_pan_tool_black_48),
            ) {
                LSocialUtil.sendEmail(
                    activity = launcherActivity,
                    to = "roy93group@gmail.com",
                    subject = "Feature Request",
                    body = "Please describe the feature you need.\n" +
                            "The more detail you can share, the better.\n" +
                            "\nThanks for taking the time - we'll get back to you as soon as possible to ask a few clarifying question or to give you an update \uD83D\uDE4F\n\n"
                )
            },
            ListPopupItem(
                text = context.getString(R.string.about_color_launcher),
                description = context.getString(R.string.about_color_launcher_des),
                icon = ContextCompat.getDrawable(
                    context,
                    R.drawable.baseline_info_black_24dp
                ),
            ) {
                C.launchAboutLauncher(activity = launcherActivity, isCancelableFragment = true)
            },
            ListPopupItem(
                text = context.getString(R.string.restart_color_launcher),
                description = context.getString(R.string.restart_color_launcher_des),
                icon = ContextCompat.getDrawable(
                    context,
                    R.drawable.baseline_restart_alt_black_24dp
                ),
            ) {
                (launcherActivity as? BaseActivity)?.apply {
                    launchActivity(IntroActivity::class.java)
                    finishAfterTransition()
                }
            },
        )
    }
}
