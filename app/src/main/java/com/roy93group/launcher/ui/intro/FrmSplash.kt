package com.roy93group.launcher.ui.intro

import com.roy93group.launcher.R

class FrmSplash : FrmWithNext(R.layout.intro_splash) {
    override fun next(activity: IntroActivity) {
        activity.setFragment(FrmPermissions())
    }
}
