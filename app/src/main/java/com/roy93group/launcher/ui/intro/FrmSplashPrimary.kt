package com.roy93group.launcher.ui.intro

import android.os.Bundle
import android.view.View
import com.loitp.core.ext.setSafeOnClickListener
import com.loitp.picker.shiftColor.OnColorChangedListener
import com.roy93group.app.C
import com.roy93group.launcher.R
import kotlinx.android.synthetic.main.frm_intro_splash_primary.*

/**
 * Updated by Loitp on 2022.12.17
 * Galaxy One company,
 * Vietnam
 * +840766040293
 * freuss47@gmail.com
 */
class FrmSplashPrimary : FrmWithNext(R.layout.frm_intro_splash_primary) {

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
        updateUI()

        tvBack.apply {
            setTextColor(C.getColorPrimary())
            setSafeOnClickListener {
                (activity as? IntroActivity)?.onBaseBackPressed()
            }
        }
        colorPicker.apply {
            colors = C.colors
            setOnColorChangedListener(object : OnColorChangedListener {
                override fun onColorChanged(c: Int) {
                    C.vibrate(milliseconds = 10)

                    val result = C.updatePrimaryColor(c)
                    if (result) {
                        updateUI()
                        if (activity is IntroActivity) {
                            (activity as IntroActivity).updateUI()
                        }
                    } else {
                        if (activity is IntroActivity) {
                            (activity as IntroActivity).showSnackBarError(getString(R.string.err_same_color))
                        }
                    }
                }
            })
        }
    }

    private fun updateUI() {
        val colorPrimary = C.getColorPrimary()
        ivLogo.setColorFilter(colorPrimary)
        tv.setTextColor(colorPrimary)
        tvDes.setTextColor(colorPrimary)
        tvBack.setTextColor(colorPrimary)
    }
}
