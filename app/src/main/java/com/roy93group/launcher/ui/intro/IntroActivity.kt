package com.roy93group.launcher.ui.intro

import android.content.Intent
import android.os.Bundle
import com.loitpcore.annotation.IsAutoAnimation
import com.loitpcore.annotation.IsFullScreen
import com.loitpcore.annotation.LogTag
import com.loitpcore.core.base.BaseFontActivity
import com.manojbhadane.QButton
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
class IntroActivity : BaseFontActivity() {

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

        findViewById<QButton>(R.id.btNext).setOnClickListener {
            stack.peek()?.next(this)
        }
    }

    override fun onResume() {
        super.onResume()
        (stack.peek() as? FrmPermissions)?.updatePermissionStatus()
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

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 0) {
            (stack.peek() as? FrmPermissions)?.updatePermissionStatus()
        }
    }
}
