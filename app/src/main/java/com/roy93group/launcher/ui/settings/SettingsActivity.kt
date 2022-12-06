package com.roy93group.launcher.ui.settings

import android.os.Bundle
import androidx.fragment.app.FragmentActivity
import com.roy93group.launcher.storage.Settings

/**
 * Updated by Loitp on 2022.12.17
 * Galaxy One company,
 * Vietnam
 * +840766040293
 * freuss47@gmail.com
 */
abstract class SettingsActivity : FragmentActivity() {

    val settings = Settings()

    final override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        settings.init(applicationContext)
        init(savedInstanceState)
    }

    abstract fun init(savedInstanceState: Bundle?)
}
