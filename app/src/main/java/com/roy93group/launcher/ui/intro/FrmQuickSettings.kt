package com.roy93group.launcher.ui.intro

import android.content.ComponentName
import android.content.Intent
import android.content.pm.PackageManager
import com.loitp.core.ext.tranIn
import com.roy93group.launcher.R
import com.roy93group.launcher.ui.LauncherActivity

/**
 * Updated by Loitp on 2022.12.17
 * Galaxy One company,
 * Vietnam
 * +840766040293
 * freuss47@gmail.com
 */
class FrmQuickSettings : FrmWithNext(R.layout.frm_intro_settings) {
    override fun next(
        activity: IntroActivity,
        isCheckedPolicy: Boolean
    ) {
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
        requireContext().tranIn()
    }
}
