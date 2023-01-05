package com.roy93group.launcher.ui.intro

import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.view.animation.AnimationUtils
import com.loitp.core.ext.setSafeOnClickListener
import com.loitp.core.utilities.LUIUtil
import com.loitp.picker.shiftColor.OnColorChangedListener
import com.roy93group.app.C
import com.roy93group.launcher.R
import kotlinx.android.synthetic.main.frm_intro_splash_background.*
import kotlinx.android.synthetic.main.l_fancy_showcaseanimated_view.*
import kotlinx.android.synthetic.main.l_fancy_showcaseanimated_view.view.*
import me.toptas.fancyshowcase.FancyShowCaseQueue
import me.toptas.fancyshowcase.FancyShowCaseView
import me.toptas.fancyshowcase.FocusShape
import me.toptas.fancyshowcase.listener.DismissListener
import me.toptas.fancyshowcase.listener.OnViewInflateListener

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

        fancyView = FancyShowCaseView.Builder(requireActivity())
            .focusOn(colorPicker)
            .backgroundColor(Color.YELLOW)
            .focusShape(FocusShape.CIRCLE)
            .focusBorderColor(Color.GREEN)
            .focusBorderSize(15)
//            .showOnce()
            .dismissListener(object : DismissListener {
                override fun onDismiss(id: String?) {

                }

                override fun onSkipped(id: String?) {

                }
            })
            .customView(R.layout.l_fancy_showcaseanimated_view, object : OnViewInflateListener {
                override fun onViewInflated(view: View) {
                    LUIUtil.recolorStatusBar(
                        context = requireContext(),
                        startColor = null,
                        endColor = Color.RED
                    )
                    LUIUtil.recolorNavigationBar(
                        context = requireContext(),
                        startColor = null,
                        endColor = Color.RED
                    )
                    setAnimatedContent(fancyView)
                }
            })
            .build()

        queue = FancyShowCaseQueue().apply {
            fancyView?.let {
                add(it)
            }
            show()
        }
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

    private var queue: FancyShowCaseQueue? = null
    private var fancyView: FancyShowCaseView? = null

    @SuppressLint("SetTextI18n")
    private fun setAnimatedContent(
        fancyShowCaseView: FancyShowCaseView?
    ) {
        if (fancyShowCaseView == null) {
            return
        }
        Handler(Looper.getMainLooper()).postDelayed({
            fancyShowCaseView.btnNext.setSafeOnClickListener {
                fancyShowCaseView.hide()
            }
            fancyShowCaseView.btnDismiss.setSafeOnClickListener {
                queue?.cancel(true)
            }

            val mainAnimation = AnimationUtils.loadAnimation(
                /* context = */ context,
                /* id = */ R.anim.slide_in_left_fancy_showcase
            )
            mainAnimation.fillAfter = true

            val subAnimation = AnimationUtils.loadAnimation(
                /* context = */ context,
                /* id = */ R.anim.slide_in_left_fancy_showcase
            )
            subAnimation.fillAfter = true
            fancyShowCaseView.tvMain.startAnimation(mainAnimation)
            Handler(Looper.getMainLooper()).postDelayed({
                fancyShowCaseView.tvSub.startAnimation(subAnimation)
            }, 80)
        }, 200)
    }
}
