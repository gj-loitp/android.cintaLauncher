package com.roy93group.launcher.ui.intro

import androidx.fragment.app.Fragment

/**
 * Updated by Loitp on 2022.12.17
 * Galaxy One company,
 * Vietnam
 * +840766040293
 * freuss47@gmail.com
 */
abstract class FrmWithNext(layout: Int) : Fragment(layout) {
    abstract fun next(
        activity: IntroActivity,
        isCheckedPolicy: Boolean
    )
}
