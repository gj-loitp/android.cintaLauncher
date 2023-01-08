package com.roy93group.ext

import android.content.Context
import android.content.Intent
import com.loitp.core.common.Constants
import com.loitp.core.ext.tranIn
import com.loitp.core.ext.vibrate
import com.loitp.data.ActivityData
import com.roy93group.lookerupper.ui.a.SearchActivity

/**
 * Created by Loitp on 08,January,2023
 * Galaxy One company,
 * Vietnam
 * +840766040293
 * freuss47@gmail.com
 */
fun Context.goToSearchScreen() {
    ActivityData.instance.type = Constants.TYPE_ACTIVITY_TRANSITION_SLIDE_UP
    this.vibrate(milliseconds = 100L)
    this.startActivity(
        Intent(
            this,
            SearchActivity::class.java
        ).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
    )
    this.tranIn()
}

fun isValidColor(): Boolean {
    if (getColorPrimary() == getColorBackground()) {
        return false
    }
    return true
}
