package com.roy93group.app

import android.app.Activity
import android.content.Intent
import android.graphics.Color
import android.net.Uri
import com.loitpcore.core.utilities.LAppResource
import com.loitpcore.core.utilities.LSocialUtil
import com.roy93group.launcher.R

/**
 * Created by Loitp on 23,November,2022
 * Galaxy One company,
 * Vietnam
 * +840766040293
 * freuss47@gmail.com
 */
object C {
    val COLOR_PRIMARY = LAppResource.getColor(R.color.colorPrimary)
    val COLOR_PRIMARY_2 = LAppResource.getColor(R.color.colorPrimary2)

    val COLOR_FAST_SCROLL_TEXT = COLOR_PRIMARY
    const val COLOR_FAST_SCROLL_TEXT_HIGHLIGHT = Color.WHITE
    val COLOR_FAST_SCROLL_BACKGROUND = COLOR_PRIMARY_2

    fun searchIconPack(activity: Activity) {
//        val url = "https://play.google.com/store/search?q=icon%20pack&c=apps"
        val url = "market://search?q=icon%20pack&c=apps"
        try {
            activity.startActivity(
                Intent(
                    Intent.ACTION_VIEW,
                    Uri.parse(url)
                )
            )
        } catch (ex: Exception) {
            LSocialUtil.moreApp(activity)
        }
    }
}
