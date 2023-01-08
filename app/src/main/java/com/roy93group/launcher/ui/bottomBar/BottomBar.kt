package com.roy93group.launcher.ui.bottomBar

import androidx.appcompat.widget.AppCompatImageView
import androidx.cardview.widget.CardView
import com.loitp.core.ext.screenHeight
import com.loitp.core.ext.screenWidth
import com.loitp.core.ext.setBackgroundTintList
import com.loitp.core.ext.setSafeOnClickListenerElastic
import com.roy93group.ext.getColorBackground
import com.roy93group.ext.getColorPrimary
import com.roy93group.ext.goToSearchScreen
import com.roy93group.launcher.ui.LauncherActivity
import com.roy93group.launcher.ui.popup.drawer.DrawerLongPressPopup
import com.roy93group.launcher.ui.view.scrollbar.Scrollbar
import com.roy93group.launcher.ui.view.scrollbar.ScrollbarIconView
import io.posidon.android.conveniencelib.getNavigationBarHeight
import kotlinx.android.synthetic.main.activity_launcher.*

/**
 * Updated by Loitp on 2022.12.16
 * Galaxy One company,
 * Vietnam
 * +840766040293
 * freuss47@gmail.com
 */
class BottomBar(val launcherActivity: LauncherActivity) {

    val scrollBar: Scrollbar get() = appDrawerIcon.scrollBar
    var colorPrimary = getColorPrimary()
    var colorBackground = getColorBackground()

    val cvSearchBarContainer: CardView = launcherActivity.cvSearchBarContainer.apply {
        setCardBackgroundColor(colorPrimary)
        setOnClickListener {
            it.context.goToSearchScreen()
        }
    }

    val appDrawerIcon: ScrollbarIconView = launcherActivity.appDrawerIcon.apply {
        setColorFilter(colorBackground)
        appDrawer = launcherActivity.appDrawer
    }
    private val ivSetting: AppCompatImageView = launcherActivity.ivSetting.apply {
        setColorFilter(colorBackground)
        this.setSafeOnClickListenerElastic(
            runnable = {
                DrawerLongPressPopup.show(
                    launcherActivity = launcherActivity,
                    parent = this,
                    touchX = screenWidth / 2f,
                    touchY = screenHeight / 2f,
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
        colorPrimary = getColorPrimary()
        colorBackground = getColorBackground()

        cvSearchBarContainer.setCardBackgroundColor(colorPrimary)
        appDrawerIcon.setColorFilter(colorBackground)
        ivSetting.apply {
            this.setBackgroundTintList(colorPrimary)
            setColorFilter(colorBackground)
        }
        tvSearch.setTextColor(colorBackground)
    }
}
