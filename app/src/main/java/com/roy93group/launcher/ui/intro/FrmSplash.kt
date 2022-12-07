package com.roy93group.launcher.ui.intro

import android.graphics.Color
import android.os.Bundle
import android.view.View
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import com.loitpcore.picker.shiftColorPicker.LineColorPicker
import com.loitpcore.picker.shiftColorPicker.OnColorChangedListener
import com.roy93group.app.C
import com.roy93group.launcher.R

/**
 * Updated by Loitp on 2022.12.17
 * Galaxy One company,
 * Vietnam
 * +840766040293
 * freuss47@gmail.com
 */
class FrmSplash : FrmWithNext(R.layout.frm_intro_splash) {
    override fun next(
        activity: IntroActivity,
        isCheckedPolicy: Boolean
    ) {
        activity.setFragment(FrmPermissions())
    }

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?
    ) {
        super.onViewCreated(view, savedInstanceState)
        updateUI(view)

        view.findViewById<LineColorPicker>(R.id.colorPicker).apply {
            colors = intArrayOf(
                Color.RED,
                Color.GREEN,
                Color.BLUE,
                Color.YELLOW,
                Color.CYAN,
                Color.MAGENTA,
            )
            setSelectedColor(Color.RED)
            setOnColorChangedListener(object : OnColorChangedListener {
                override fun onColorChanged(c: Int) {
                    C.COLOR_PRIMARY_2 = c
                    updateUI(view)
                    if (activity is IntroActivity) {
                        (activity as IntroActivity).updateUI()
                    }
                }
            })
        }
    }

    private fun updateUI(view: View) {
        view.findViewById<AppCompatImageView>(R.id.ivLogo).apply {
            setColorFilter(C.COLOR_PRIMARY_2)
        }
        view.findViewById<AppCompatTextView>(R.id.tv).apply {
            setTextColor(C.COLOR_PRIMARY_2)
        }
        view.findViewById<AppCompatTextView>(R.id.tvDes).apply {
            setTextColor(C.COLOR_PRIMARY_2)
        }
    }
}
