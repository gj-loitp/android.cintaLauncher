package com.roy93group.launcher.ui.settings

import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.LayerDrawable
import android.os.Build
import android.os.Bundle
import android.view.WindowManager
import androidx.fragment.app.FragmentActivity
import com.roy93group.app.C
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

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) window.setDecorFitsSystemWindows(false)
        else window.setFlags(
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
        )
        settings.init(applicationContext)
        loadColors()

        init(savedInstanceState)
    }

    abstract fun init(savedInstanceState: Bundle?)

    private fun loadColors() {
        window.decorView.background = LayerDrawable(
            arrayOf(
                ColorDrawable(C.COLOR_PRIMARY),
            )
        )
    }
}
