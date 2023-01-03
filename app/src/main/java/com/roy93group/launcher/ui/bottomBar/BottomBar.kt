package com.roy93group.launcher.ui.bottomBar

import androidx.appcompat.widget.AppCompatImageView
import androidx.cardview.widget.CardView
import com.loitp.core.utilities.LScreenUtil
import com.loitp.core.utilities.LUIUtil
import com.roy93group.app.C
import com.roy93group.launcher.ui.LauncherActivity
import com.roy93group.launcher.ui.popup.drawer.DrawerLongPressPopup
import com.roy93group.launcher.ui.view.scrollbar.Scrollbar
import com.roy93group.launcher.ui.view.scrollbar.ScrollbarIconView
import io.posidon.android.conveniencelib.getNavigationBarHeight
import kotlinx.android.synthetic.main.activity_launcher.*
import kotlinx.android.synthetic.main.activity_launcher.view.*

/**
 * Updated by Loitp on 2022.12.16
 * Galaxy One company,
 * Vietnam
 * +840766040293
 * freuss47@gmail.com
 */
class BottomBar(val launcherActivity: LauncherActivity) {

    val scrollBar: Scrollbar get() = appDrawerIcon.scrollBar
    var colorPrimary = C.getColorPrimary()
    var colorBackground = C.getColorBackground()

    val cvSearchBarContainer: CardView = launcherActivity.cvSearchBarContainer.apply {
        setCardBackgroundColor(colorPrimary)
        setOnClickListener {
            C.goToSearchScreen(it.context)
        }
    }

    val appDrawerIcon: ScrollbarIconView = cvSearchBarContainer.appDrawerIcon.apply {
        setColorFilter(colorBackground)
        appDrawer = launcherActivity.appDrawer
    }
    private val ivSetting: AppCompatImageView = launcherActivity.ivSetting.apply {
        setColorFilter(colorBackground)
        LUIUtil.setSafeOnClickListenerElastic(
            view = this,
            runnable = {
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
        )
    }

    private val tvSearch = launcherActivity.tvSearch.apply {
        setTextColor(colorBackground)
    }

    fun updateTheme() {
        colorPrimary = C.getColorPrimary()
        colorBackground = C.getColorBackground()

        cvSearchBarContainer.setCardBackgroundColor(colorPrimary)
        appDrawerIcon.setColorFilter(colorBackground)
        ivSetting.apply {
            C.setBackgroundTintList(this)
            setColorFilter(colorBackground)
        }
    }
}
