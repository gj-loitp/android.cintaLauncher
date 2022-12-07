package com.roy93group.launcher.ui.bottomBar

import android.content.Intent
import androidx.cardview.widget.CardView
import com.google.android.material.card.MaterialCardView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.loitpcore.core.utilities.LActivityUtil
import com.loitpcore.core.utilities.LScreenUtil
import com.roy93group.app.C
import com.roy93group.launcher.R
import com.roy93group.launcher.ui.LauncherActivity
import com.roy93group.launcher.ui.popup.drawer.DrawerLongPressPopup
import com.roy93group.launcher.ui.view.scrollbar.Scrollbar
import com.roy93group.launcher.ui.view.scrollbar.ScrollbarIconView
import com.roy93group.lookerupper.ui.a.SearchActivity
import io.posidon.android.conveniencelib.getNavigationBarHeight

/**
 * Updated by Loitp on 2022.12.16
 * Galaxy One company,
 * Vietnam
 * +840766040293
 * freuss47@gmail.com
 */
class BottomBar(val launcherActivity: LauncherActivity) {

    val scrollBar: Scrollbar get() = appDrawerIcon.scrollBar

    val cvSearchBarContainer: CardView =
        launcherActivity.findViewById<MaterialCardView>(R.id.cvSearchBarContainer).apply {
            setCardBackgroundColor(C.COLOR_PRIMARY_2)
            strokeColor = C.COLOR_PRIMARY
            setOnClickListener {
                val context = it.context
                context.startActivity(
                    Intent(
                        context,
                        SearchActivity::class.java
                    ).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                )
                LActivityUtil.tranIn(context)
            }
        }

    val appDrawerIcon: ScrollbarIconView =
        cvSearchBarContainer.findViewById<ScrollbarIconView>(R.id.appDrawerIcon).apply {
            appDrawer = launcherActivity.appDrawer
        }
    val cvBackButtonContainer: FloatingActionButton =
        launcherActivity.findViewById<FloatingActionButton>(R.id.fabBack).apply {
            C.setBackgroundTintList(this)
            this.setOnClickListener {
                launcherActivity.appDrawer.close()
            }
        }
    val cvSetting: FloatingActionButton =
        launcherActivity.findViewById<FloatingActionButton>(R.id.fabSetting).apply {
            C.setBackgroundTintList(this)
            this.setOnClickListener {
                DrawerLongPressPopup.show(
                    launcherActivity = launcherActivity,
                    parent = this,
                    touchX = LScreenUtil.screenWidth / 2f,
                    touchY = LScreenUtil.screenHeight / 2f,
                    navbarHeight = launcherActivity.getNavigationBarHeight(),
                    settings = launcherActivity.settings,
                    reloadApps = launcherActivity::loadApps
                )
            }
        }
}
