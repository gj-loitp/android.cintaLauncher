package com.roy93group.launcher.ui.intro

import android.os.Bundle
import androidx.core.view.isVisible
import com.loitp.annotation.IsAutoAnimation
import com.loitp.annotation.IsFullScreen
import com.loitp.annotation.IsKeepScreenOn
import com.loitp.annotation.LogTag
import com.loitp.core.base.BaseFontActivity
import com.loitp.core.ext.setSafeOnClickListener
import com.loitp.core.utilities.LSocialUtil
import com.loitp.core.utilities.LUIUtil
import com.roy93group.app.C
import com.roy93group.launcher.R
import kotlinx.android.synthetic.main.activity_intro.*
import java.util.*

/**
 * Updated by Loitp on 2022.12.17
 * Galaxy One company,
 * Vietnam
 * +840766040293
 * freuss47@gmail.com
 */
@LogTag("IntroActivity")
@IsFullScreen(false)
@IsAutoAnimation(false)
@IsKeepScreenOn(false)
class IntroActivity : BaseFontActivity() {

    override fun setLayoutResourceId(): Int {
        return R.layout.activity_intro
    }

    private val stack = LinkedList<FrmWithNext>().apply {
        push(FrmSplashBackground())
    }

    fun setFragment(fragment: FrmWithNext) {
        stack.push(fragment)
        supportFragmentManager
            .beginTransaction()
            .setCustomAnimations(
                R.anim.slide_in_right,
                R.anim.slide_out_left
            )
            .replace(R.id.flContainer, fragment)
            .commit()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (savedInstanceState == null) {
            supportFragmentManager
                .beginTransaction()
                .replace(R.id.flContainer, stack.peek()!!)
                .commit()
        }

        setupViews()
    }

    private fun setWallpaper() {
        LUIUtil.setWallpaperAndLockScreen(
            activity = this@IntroActivity,
            color = C.getColorBackground(),
            isSetWallpaper = true,
            isSetLockScreen = true,
        )
    }

    private fun setupViews() {
        updateUI()

        toggle.setOnCheckedChangeListener { _, b ->
            btNext.isVisible = b
        }
        btNext.setSafeOnClickListener {
            nextScreen()
        }
        tvPolicy.setSafeOnClickListener {
            LSocialUtil.openBrowserPolicy(this)
        }
    }

    override fun onResume() {
        super.onResume()
        (stack.peek() as? FrmPermissions)?.updatePermissionStatus()
    }

    override fun onPause() {
        super.onPause()
        setWallpaper()
    }

    override fun onBaseBackPressed() {
        stack.pop()
        if (stack.isEmpty()) {
            super.onBaseBackPressed()
        } else {
            stack.peek()?.let { frm ->
                supportFragmentManager
                    .beginTransaction()
                    .setCustomAnimations(
                        R.anim.slide_in_left,
                        R.anim.slide_out_right
                    )
                    .replace(R.id.flContainer, frm)
                    .commit()
            }
        }
    }

//    override fun onActivityResult(
//        requestCode: Int,
//        resultCode: Int,
//        data: Intent?
//    ) {
//        super.onActivityResult(requestCode, resultCode, data)
//        if (requestCode == 0) {
//            (stack.peek() as? FrmPermissions)?.updatePermissionStatus()
//        }
//    }

    fun updateUI() {
        val colorBackground = C.getColorBackground()
        val colorPrimary = C.getColorPrimary()

        C.changeStatusBarContrastStyle(window)
        setCustomStatusBar(
            colorStatusBar = colorBackground,
            colorNavigationBar = colorBackground
        )

        cl.setBackgroundColor(colorBackground)
        toggle.apply {
            trackDrawable = C.generateTrackDrawable(colorPrimary)
            thumbDrawable =
                C.generateThumbDrawable(context = context, color = colorBackground)
        }
        btNext.apply {
            setTextColor(colorBackground)
            C.setBackground(this)
        }
        tvPolicy.apply {
            setTextColor(colorPrimary)
            paint?.isUnderlineText = true
        }
    }

    private fun nextScreen() {
        if (!C.isValidColor()) {
            showSnackBarError(getString(R.string.err_same_color))
            return
        }

        stack.peek()?.next(
            activity = this,
            isCheckedPolicy = toggle?.isChecked ?: true
        )
    }
}
