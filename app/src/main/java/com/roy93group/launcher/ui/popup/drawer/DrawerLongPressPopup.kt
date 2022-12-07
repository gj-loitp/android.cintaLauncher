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
import com.google.android.material.card.MaterialCardView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.loitpcore.core.ext.setSafeOnClickListener
import com.loitpcore.core.utilities.LActivityUtil
import com.loitpcore.core.utilities.LAppResource
import com.loitpcore.core.utilities.LSocialUtil
import com.roy93group.app.C
import com.roy93group.launcher.R
import com.roy93group.launcher.storage.DoReshapeAdaptiveIconsSetting.doReshapeAdaptiveIcons
import com.roy93group.launcher.storage.ScrollbarControllerSetting.scrollbarController
import com.roy93group.launcher.storage.Settings
import com.roy93group.launcher.ui.LauncherActivity
import com.roy93group.launcher.ui.popup.PopupUtils
import com.roy93group.launcher.ui.popup.listPopup.ListPopupAdapter
import com.roy93group.launcher.ui.popup.listPopup.ListPopupItem
import com.roy93group.launcher.ui.settings.iconPackPicker.IconPackPickerActivity
import io.posidon.android.conveniencelib.Device

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
        val content = LayoutInflater.from(parent.context).inflate(R.layout.view_list_popup, null)
        val window = PopupWindow(
            /* contentView = */ content,
            /* width = */ListPopupWindow.MATCH_PARENT,
            /* height = */ListPopupWindow.WRAP_CONTENT,
            /* focusable = */true
        )
        PopupUtils.setCurrent(window)

        val fabDismiss = content.findViewById<FloatingActionButton>(R.id.fabDismiss).apply {
            setColorFilter(C.COLOR_0)
        }
        val cardView = content.findViewById<MaterialCardView>(R.id.card).apply {
            setCardBackgroundColor(C.COLOR_0)
        }
        content.findViewById<RecyclerView>(R.id.recyclerView).apply {
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
                                cardView.post {
                                    reloadApps()
                                    update()
                                }
                            },
                            reloadApps = reloadApps,
                        )
                    )
                }
                update()
            }
        }

        fabDismiss.setSafeOnClickListener {
            window.dismiss()
        }

        val gravity = Gravity.CENTER
        val x = touchX.toInt() - Device.screenWidth(parent.context) / 2
        val y = touchY.toInt() - Device.screenHeight(parent.context) / 2 + navbarHeight
        window.showAtLocation(parent, gravity, x, y)
    }

    private fun createMainAdapter(
        launcherActivity: LauncherActivity,
        context: Context,
        settings: Settings,
        reloadScrollbarController: () -> Unit,
        reloadApps: () -> Unit,
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
            ListPopupItem(text = context.getString(R.string.theme), isTitle = true),
            ListPopupItem(
                text = context.getString(R.string.color),
                description = context.getString(R.string.pick_your_favorite_color),
                icon = ContextCompat.getDrawable(context, R.drawable.baseline_palette_black_48),
            ) {
                C.launchColor(activity = launcherActivity,
                    isCancelableFragment = true,
                    title = LAppResource.getString(R.string.color),
                    des = LAppResource.getString(R.string.pick_your_favorite_color),
                    warning = LAppResource.getString(R.string.the_color_launcher_will_be_restarted),
                    onDismiss = { newColor ->
                        C.COLOR_0 = newColor
                        launcherActivity.recreate()
                    })
            },
            ListPopupItem(
                text = context.getString(R.string.wallpaper),
                icon = ContextCompat.getDrawable(context, R.drawable.baseline_wallpaper_black_48),
            ) {
                C.launchWallpaper(launcherActivity)
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
        )
    }
}
