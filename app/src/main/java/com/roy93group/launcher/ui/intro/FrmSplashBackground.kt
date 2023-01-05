package com.roy93group.launcher.ui.intro

import android.os.Bundle
import android.view.View
import com.loitp.picker.shiftColor.OnColorChangedListener
import com.roy93group.app.C
import com.roy93group.launcher.R
import kotlinx.android.synthetic.main.frm_intro_splash_background.*
import me.toptas.fancyshowcase.FancyShowCaseQueue
import me.toptas.fancyshowcase.FancyShowCaseView
import me.toptas.fancyshowcase.listener.DismissListener

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
                    val result = C.updateBackgroundColor(c)
                    if (result) {
                        (activity as? IntroActivity)?.updateUI()
                    } else {
                        (activity as? IntroActivity)?.showSnackBarError(getString(R.string.err_same_color))
                    }
                }
            })
        }
        initShowcase()
    }

    override fun onResume() {
        super.onResume()
        updateUI()
    }

    private fun updateUI() {
        val colorPrimary = C.getColorPrimary()

        ivLogo.setColorFilter(colorPrimary)
        tv.setTextColor(colorPrimary)
        tvDes.setTextColor(colorPrimary)
    }

    private fun initShowcase() {
        var queue: FancyShowCaseQueue? = null
        var fancyView: FancyShowCaseView? = null
        fancyView = C.createFancyShowcase(
            activity = requireActivity(),
            focusView = colorPicker,
            idShowOne = false,
            onDismissListener = object : DismissListener {
                override fun onDismiss(id: String?) {

                }

                override fun onSkipped(id: String?) {
                }
            },
            onViewInflated = {
                C.showFancyShowCaseView(
                    fancyShowCaseView = fancyView,
                    onHide = null,
                    onDismiss = {
                        queue?.cancel(true)
                    }
                )
            }
        )

        queue = FancyShowCaseQueue().apply {
            add(fancyView)
            show()
        }
    }
}
