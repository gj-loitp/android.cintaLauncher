package com.roy93group.launcher.ui.intro

import android.os.Bundle
import android.view.View
import com.loitp.picker.shiftColor.OnColorChangedListener
import com.roy93group.app.C
import com.roy93group.launcher.R
import kotlinx.android.synthetic.main.frm_intro_splash_background.*

/**
 * Updated by Loitp on 2022.12.17
 * Galaxy One company,
 * Vietnam
 * +840766040293
 * freuss47@gmail.com
 */
class FrmSplashBackground : FrmWithNext(R.layout.frm_intro_splash_background) {

    override fun next(
        activity: IntroActivity,
        isCheckedPolicy: Boolean
    ) {
        activity.setFragment(FrmSplashPrimary())
    }

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?
    ) {
        super.onViewCreated(view, savedInstanceState)
        updateUI()

        colorPicker.apply {
            colors = C.colors
            setOnColorChangedListener(object : OnColorChangedListener {
                override fun onColorChanged(c: Int) {
                    C.vibrate(milliseconds = 10)
                    C.updateBackgroundColor(c)
                    if (activity is IntroActivity) {
                        (activity as IntroActivity).updateUI()
                    }
                }
            })
        }
    }

    override fun onResume() {
        super.onResume()
        updateUI()
    }

    private fun updateUI() {
        ivLogo.setColorFilter(C.getColorPrimary())
        tv.setTextColor(C.getColorPrimary())
        tvDes.setTextColor(C.getColorPrimary())
    }
}
