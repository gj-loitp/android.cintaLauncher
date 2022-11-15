package com.roy93group.launcher.ui.intro

import android.content.Intent
import android.os.Bundle
import android.view.View
import com.loitpcore.annotation.IsAutoAnimation
import com.loitpcore.annotation.IsFullScreen
import com.loitpcore.annotation.LogTag
import com.loitpcore.core.base.BaseFontActivity
import com.roy93group.launcher.R
import java.util.*

@LogTag("IntroActivity")
@IsFullScreen(false)
@IsAutoAnimation(false)
class IntroActivity : BaseFontActivity() {

    override fun setLayoutResourceId(): Int {
        return R.layout.activity_intro
    }

    private val stack = LinkedList<FragmentWithNext>().apply {
        push(SplashFragment())
    }

    fun setFragment(fragment: FragmentWithNext) {
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

    @Suppress("unused")
    fun next(v: View) {
        stack.peek()?.next(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (savedInstanceState == null) {
            supportFragmentManager
                .beginTransaction()
                .replace(R.id.flContainer, stack.peek()!!)
                .commit()
        }

        updateColorTheme()
    }

    override fun onResume() {
        super.onResume()
        (stack.peek() as? PermissionsFragment)?.updatePermissionStatus()
    }

    override fun onBaseBackPressed() {
        stack.pop()
        if (stack.isEmpty())
            super.onBaseBackPressed()
        else supportFragmentManager
            .beginTransaction()
            .setCustomAnimations(
                R.anim.slide_in_left,
                R.anim.slide_out_right
            )
            .replace(R.id.flContainer, stack.peek()!!)
            .commit()
    }

//    override fun onBackPressed() {
//        stack.pop()
//        if (stack.isEmpty())
//            super.onBackPressed()
//        else supportFragmentManager
//            .beginTransaction()
//            .setCustomAnimations(
//                R.anim.slide_in_left,
//                R.anim.slide_out_right
//            )
//            .replace(R.id.flContainer, stack.peek()!!)
//            .commit()
//    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 0) {
            (stack.peek() as? PermissionsFragment)?.updatePermissionStatus()
        }
    }

    fun updateColorTheme() {
//        findViewById<ImageView>(R.id.btNext).run {
//            backgroundTintList = ColorStateList.valueOf(ColorTheme.buttonColorCallToAction)
//            imageTintList =
//                ColorStateList.valueOf(ColorTheme.titleColorForBG(ColorTheme.buttonColorCallToAction))
//        }
//        window.decorView.setBackgroundColor(ColorTheme.uiBG)
    }
}
