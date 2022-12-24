package com.roy93group.launcher.ui.bottomBar

import androidx.cardview.widget.CardView
import com.google.android.material.card.MaterialCardView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.loitp.core.utilities.LScreenUtil
import com.roy93group.app.C
import com.roy93group.launcher.R
import com.roy93group.launcher.ui.LauncherActivity
import com.roy93group.launcher.ui.popup.drawer.DrawerLongPressPopup
import com.roy93group.launcher.ui.view.scrollbar.Scrollbar
import com.roy93group.launcher.ui.view.scrollbar.ScrollbarIconView
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
            setCardBackgroundColor(C.COLOR_0)
            strokeColor = C.COLOR_PRIMARY
            setOnClickListener {
                C.goToSearchScreen(it.context)
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
