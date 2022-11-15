package com.roy93group.launcher.ui.intro

import com.roy93group.launcher.R

class SplashFragment : FragmentWithNext(R.layout.intro_splash) {
    override fun next(activity: IntroActivity) {
        activity.setFragment(PermissionsFragment())
    }
}
