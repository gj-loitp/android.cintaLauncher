package com.roy93group.ext

import com.google.android.material.card.MaterialCardView
import com.loitp.core.ext.getDimenValue
import com.loitp.core.ext.setCornerCardView
import com.roy93group.launcher.R

/**
 * Created by Loitp on 08,January,2023
 * Galaxy One company,
 * Vietnam
 * +840766040293
 * freuss47@gmail.com
 */

fun MaterialCardView.setCornerCardViewLauncher() {
    val radiusTL = getDimenValue(R.dimen.round_large).toFloat()
    val radiusTR = getDimenValue(R.dimen.round_large).toFloat()
    val radiusBL = 0f
    val radiusBR = 0f
    this.setCornerCardView(
        radiusTL = radiusTL,
        radiusTR = radiusTR,
        radiusBL = radiusBL,
        radiusBR = radiusBR
    )
}

