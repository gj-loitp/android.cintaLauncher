package com.roy93group.app

import android.app.Activity
import android.content.Context
import android.content.Context.VIBRATOR_SERVICE
import android.content.Intent
import android.graphics.Color
import android.net.Uri
import android.os.Build
import android.os.VibrationEffect
import android.os.Vibrator
import android.os.VibratorManager
import android.provider.AlarmClock
import android.provider.CalendarContract
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

    fun vibrate() {
        val vib = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            val vibratorManager =
                LAppResource.application.getSystemService(Context.VIBRATOR_MANAGER_SERVICE) as VibratorManager
            vibratorManager.defaultVibrator
        } else {
            @Suppress("DEPRECATION")
            LAppResource.application.getSystemService(VIBRATOR_SERVICE) as Vibrator
        }

        vib.vibrate(
            VibrationEffect.createOneShot(
                /* milliseconds = */ 10,
                /* amplitude = */ VibrationEffect.DEFAULT_AMPLITUDE
            )
        )
    }

    fun launchClockApp(activity: Activity) {
        try {
            val i = Intent(AlarmClock.ACTION_SHOW_ALARMS)
            activity.startActivity(i)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun launchCalendar(activity: Activity) {
        val calendarUri = CalendarContract.CONTENT_URI
            .buildUpon()
            .appendPath("time")
            .build()
        activity.startActivity(Intent(Intent.ACTION_VIEW, calendarUri))
    }
}
