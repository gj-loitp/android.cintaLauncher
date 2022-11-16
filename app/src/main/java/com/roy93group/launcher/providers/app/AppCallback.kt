package com.roy93group.launcher.providers.app

import android.content.pm.LauncherApps
import android.os.UserHandle

/**
 * Updated by Loitp on 2022.12.16
 * Galaxy One company,
 * Vietnam
 * +840766040293
 * freuss47@gmail.com
 */
class AppCallback(val callback: () -> Unit) : LauncherApps.Callback() {

    override fun onPackageRemoved(packageName: String?, user: UserHandle?) = callback()

    override fun onPackageAdded(packageName: String?, user: UserHandle?) = callback()

    override fun onPackageChanged(packageName: String?, user: UserHandle?) = callback()

    override fun onPackagesAvailable(
        packageNames: Array<out String>?,
        user: UserHandle?,
        replacing: Boolean
    ) = callback()

    override fun onPackagesUnavailable(
        packageNames: Array<out String>?,
        user: UserHandle?,
        replacing: Boolean
    ) = callback()
}
