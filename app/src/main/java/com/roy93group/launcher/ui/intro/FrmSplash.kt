package com.roy93group.launcher.ui.intro

import android.os.Bundle
import android.view.View
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
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

        view.findViewById<AppCompatImageView>(R.id.ivLogo).apply {
            setColorFilter(C.COLOR_PRIMARY_2)
        }
        view.findViewById<AppCompatTextView>(R.id.tv).apply {
            setTextColor(C.COLOR_PRIMARY_2)
        }
    }
}
