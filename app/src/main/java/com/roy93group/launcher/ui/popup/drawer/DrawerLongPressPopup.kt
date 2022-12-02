package com.roy93group.launcher.ui.popup.drawer

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.widget.PopupWindow
import androidx.appcompat.widget.ListPopupWindow
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.loitpcore.core.utilities.LAppResource
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
        val window =
            PopupWindow(content, ListPopupWindow.WRAP_CONTENT, ListPopupWindow.WRAP_CONTENT, true)
        PopupUtils.setCurrent(window)

//        content.findViewById<SeeThoughView>(R.id.blurBg).run {
////            drawable = acrylicBlur?.fullBlur?.let { BitmapDrawable(parent.resources, it) }
//            alpha = 0.1f
//        }

        val cardView = content.findViewById<CardView>(R.id.card)
//        cardView.setCardBackgroundColor(Color.RED)
        content.findViewById<RecyclerView>(R.id.recyclerView).apply {
            layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
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
                launcherActivity.showBottomSheetOptionFragment(
                    isCancelableFragment = true,
                    isShowIvClose = true,
                    title = LAppResource.getString(R.string.setting),
                    message = LAppResource.getString(R.string.app_sorting),
                    textButton1 = LAppResource.getString(R.string.alphabetic),
                    textButton2 = LAppResource.getString(R.string.by_hue),
                    onClickButton1 = {
                        settings.edit(context) {
                            scrollbarController = 0
                            reloadScrollbarController()
                        }
                    },
                    onClickButton2 = {
                        settings.edit(context) {
                            scrollbarController = 1
                            reloadScrollbarController()
                        }
                    },
                    onDismiss = {
                        //do nothing
                    }
                )
            },
            ListPopupItem(text = context.getString(R.string.icons), isTitle = true),
            ListPopupItem(
                text = context.getString(R.string.icon_packs),
                icon = ContextCompat.getDrawable(context, R.drawable.ic_shapes),
            ) {
                context.startActivity(Intent(context, IconPackPickerActivity::class.java))
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
        )
    }
}
