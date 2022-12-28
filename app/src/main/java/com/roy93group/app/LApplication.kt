package com.roy93group.app

import com.loitp.annotation.LogTag
import com.loitp.core.base.BaseApplication
import com.loitp.core.common.Constants
import com.loitp.core.utilities.LUIUtil
import com.loitp.data.ActivityData

/**
 * Updated by Loitp on 2022.12.16
 * Galaxy One company,
 * Vietnam
 * +840766040293
 * freuss47@gmail.com
 */
//https://console.firebase.google.com/u/0/project/cinta-launcher-71015/overview
//TODO firebase set is_show_flickr_gallery true when app available

//TODO background color black -> custom color
//TODO fav app
//TODO lock app
//TODO hide app
//TODO app badge
//TODO sort app drawer theo tan suat
//TODO what new dialog
//TODO animation intro
//TODO tich hop he sinh thai app roy93group
//TODO gg pay
//TODO spotlight

//feat custom font?

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
//o man hinh chinh neu nhan back press se show app launcher va nguoc lai
//luu main color o pref
//feat request feat in setting
//da thu firebase crashlytics ko su dung chung duoc voi StackTraceActivity#Thread.setDefaultUncaughtExceptionHandler -> ko co log len firebase console
//test thu uninstall app thi app drawer co refresh hay ko
//test thu install app moi tu chplay xem co show ra ko
//uninstall app
//su dung remote config de bat tat cac chuc nang nhay cam nhu gallery
//da test thu LParallaxRecyclerView nhung ko dep
//round corner bottom sheet
//setting enable/disable swipe out rv de open search screen

@LogTag("LApplication")
class LApplication : BaseApplication() {

    override fun onCreate() {
        super.onCreate()

        ActivityData.instance.type = Constants.TYPE_ACTIVITY_TRANSITION_ZOOM
//        LUIUtil.fontForAll = Constants.FONT_PATH
        LUIUtil.setDarkTheme(true)
        C.getMainColor()
    }

    override fun onAppInBackground() {
        super.onAppInBackground()
        logD("onAppInBackground")
    }

    override fun onAppInForeground() {
        super.onAppInForeground()
        logD("onAppInForeground")
    }
}
