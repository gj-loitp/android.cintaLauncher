package com.roy93group.app

import com.loitpcore.annotation.LogTag
import com.loitpcore.core.base.BaseApplication
import com.loitpcore.core.utilities.LUIUtil

/**
 * Updated by Loitp on 2022.12.16
 * Galaxy One company,
 * Vietnam
 * +840766040293
 * freuss47@gmail.com
 */
//TODO sort app drawer theo tan suat, cai nao cang nhieu text cang to
//TODO man hinh intro cho accept het cac permission moi cho next
//TODO set background to black
//TODO ripple activity intro
//TODO config gg drive
//TODO rate more share app
//TODO firebase
//TODO tich hop he sinh thai app roy93group
//TODO animation intro

//done
//ic launcher
@LogTag("LApplication")
class LApplication : BaseApplication() {

    override fun onCreate() {
        super.onCreate()

//        ActivityData.instance.type = Constants.TYPE_ACTIVITY_TRANSITION_SLIDE_LEFT
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
