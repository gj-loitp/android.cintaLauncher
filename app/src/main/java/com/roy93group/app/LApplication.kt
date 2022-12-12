package com.roy93group.app

import com.loitpcore.annotation.LogTag
import com.loitpcore.core.base.BaseApplication
import com.loitpcore.core.common.Constants
import com.loitpcore.core.utilities.LUIUtil
import com.loitpcore.data.ActivityData

/**
 * Updated by Loitp on 2022.12.16
 * Galaxy One company,
 * Vietnam
 * +840766040293
 * freuss47@gmail.com
 */

//TODO google drive
//TODO thay cac late in
//TODO sort app drawer theo tan suat
//TODO firebase
//TODO tich hop he sinh thai app roy93group
//TODO animation intro
//TODO uninstall app
//TODO app badge
//TODO what new dialog
//TODO fav app
//TODO lock app
//TODO gg pay
//TODO feat request feat in setting
//TODO screen search them cai keyword recents
//TODO o man hinh chinh neu nhan backpress se show applauncher va nguoc lai
//TODO luu main color o pref

//done
//chon man hinh chu o screen setting
//ripple activity intro
//ic launcher
//man hinh intro cho accept het cac permission moi cho next
//keep vi tri cua app drawer
//thay card view bang material cardview
//policy in intro screen
//policy in setting
//add gallery
//rate more share app
//set background to black
//remove some permission manifest
//lam man hinh choose fav color first intro
//pick color in setting screen

@LogTag("LApplication")
class LApplication : BaseApplication() {

    override fun onCreate() {
        super.onCreate()

        ActivityData.instance.type = Constants.TYPE_ACTIVITY_TRANSITION_ZOOM
//        LUIUtil.fontForAll = Constants.FONT_PATH
        LUIUtil.setDarkTheme(true)
    }

    override fun onAppInBackground() {
        super.onAppInBackground()
        logE("onAppInBackground")
    }

    override fun onAppInForeground() {
        super.onAppInForeground()
        logE("onAppInForeground")
    }

}
