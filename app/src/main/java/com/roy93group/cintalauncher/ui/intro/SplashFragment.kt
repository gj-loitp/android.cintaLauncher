package com.roy93group.cintalauncher.ui.intro

import com.roy93group.cintalauncher.R

class SplashFragment : FragmentWithNext(R.layout.intro_splash) {
    override fun next(activity: IntroActivity) {
        activity.setFragment(PermissionsFragment())
    }
}
