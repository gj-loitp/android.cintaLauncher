package com.roy93group.cintalauncher.ui.intro

import android.content.ComponentName
import android.content.Intent
import android.content.pm.PackageManager
import com.roy93group.cintalauncher.R
import com.roy93group.cintalauncher.ui.LauncherActivity

class QuickSettingsFragment : FragmentWithNext(R.layout.intro_settings) {
    override fun next(activity: IntroActivity) {
        val home = ComponentName(requireContext(), LauncherActivity::class.java)
        requireContext().packageManager.setComponentEnabledSetting(
            /* p0 = */ home,
            /* p1 = */ PackageManager.COMPONENT_ENABLED_STATE_ENABLED,
            /* p2 = */ PackageManager.DONT_KILL_APP
        )
        val intro = ComponentName(requireContext(), IntroActivity::class.java)
        requireContext().packageManager.setComponentEnabledSetting(
            /* p0 = */ intro,
            /* p1 = */ PackageManager.COMPONENT_ENABLED_STATE_DISABLED,
            /* p2 = */ PackageManager.DONT_KILL_APP
        )
        startActivity(Intent(requireContext(), LauncherActivity::class.java))
    }
}
