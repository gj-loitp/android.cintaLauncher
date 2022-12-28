package com.roy93group.launcher.ui.intro

import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.widget.AppCompatButton
import androidx.appcompat.widget.SwitchCompat
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
    private var toggle: SwitchCompat? = null
    private var btNext: AppCompatButton? = null
    private var tvPolicy: TextView? = null

    override fun setLayoutResourceId(): Int {
        return R.layout.activity_intro
    }

    private val stack = LinkedList<FrmWithNext>().apply {
        push(FrmSplash())
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
        setWallpaper()
    }

    private fun setWallpaper() {
        LUIUtil.setWallpaperAndLockScreen(
            activity = this@IntroActivity,
            color = C.COLOR_PRIMARY,
            isSetWallpaper = true,
            isSetLockScreen = true,
        )
    }

    private fun setupViews() {
        toggle = findViewById(R.id.toggle)
        btNext = findViewById(R.id.btNext)
        tvPolicy = findViewById(R.id.tvPolicy)

        updateUI()

        toggle?.setOnCheckedChangeListener { _, b ->
            btNext?.isVisible = b
        }
        btNext?.setSafeOnClickListener {
            stack.peek()?.next(
                activity = this,
                isCheckedPolicy = toggle?.isChecked ?: true
            )
        }
        tvPolicy?.setSafeOnClickListener {
            LSocialUtil.openBrowserPolicy(this)
        }
    }

    override fun onResume() {
        super.onResume()
        (stack.peek() as? FrmPermissions)?.updatePermissionStatus()
    }

    override fun onBaseBackPressed() {
        stack.pop()
        if (stack.isEmpty()) {
            super.onBaseBackPressed()
        } else {
            supportFragmentManager
                .beginTransaction()
                .setCustomAnimations(
                    R.anim.slide_in_left,
                    R.anim.slide_out_right
                )
                .replace(R.id.flContainer, stack.peek()!!)
                .commit()
        }
    }

    override fun onActivityResult(
        requestCode: Int,
        resultCode: Int,
        data: Intent?
    ) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 0) {
            (stack.peek() as? FrmPermissions)?.updatePermissionStatus()
        }
    }

    fun updateUI() {
        toggle?.apply {
            trackDrawable = C.generateTrackDrawable(C.COLOR_0)
            thumbDrawable = C.generateThumbDrawable(context = context, color = C.COLOR_PRIMARY)
        }
        btNext?.apply {
            setTextColor(C.COLOR_PRIMARY)
            C.setBackground(this)
        }
        tvPolicy?.apply {
            setTextColor(C.COLOR_0)
            paint?.isUnderlineText = true
        }
    }
}
