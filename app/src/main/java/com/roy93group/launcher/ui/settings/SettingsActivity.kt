package com.roy93group.launcher.ui.settings

import android.graphics.Color
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.LayerDrawable
import android.os.Build
import android.os.Bundle
import android.view.WindowManager
import androidx.fragment.app.FragmentActivity
import com.roy93group.launcher.providers.color.theme.ColorTheme
import com.roy93group.launcher.storage.Settings

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
//                BitmapDrawable(resources, acrylicBlur?.fullBlur),
                ColorDrawable(Color.YELLOW),
            )
        )
    }
}
