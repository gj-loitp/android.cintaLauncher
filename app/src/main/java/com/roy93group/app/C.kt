package com.roy93group.app

import android.app.Activity
import android.content.ComponentName
import android.content.Context
import android.content.Context.VIBRATOR_SERVICE
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.graphics.drawable.GradientDrawable
import android.graphics.drawable.LayerDrawable
import android.graphics.drawable.StateListDrawable
import android.net.Uri
import android.os.Build
import android.os.VibrationEffect
import android.os.Vibrator
import android.os.VibratorManager
import android.provider.AlarmClock
import android.provider.CalendarContract
import android.util.StateSet
import com.loitpcore.core.utilities.LAppResource
import com.loitpcore.core.utilities.LSocialUtil
import com.roy93group.launcher.R
import com.roy93group.launcher.util.FakeLauncherActivity
import io.posidon.android.conveniencelib.units.dp
import io.posidon.android.conveniencelib.units.toPixels


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

    fun vibrate(milliseconds: Long) {
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
                /* milliseconds = */ milliseconds,
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

    fun generateTrackDrawable(color: Int): Drawable {
        val out = StateListDrawable()
        out.addState(
            intArrayOf(android.R.attr.state_checked),
            generateBG(color)
        )
        out.addState(
            StateSet.WILD_CARD,
            generateBG(color)
        )
        return out
    }

    fun generateThumbDrawable(
        context: Context,
        color: Int
    ): Drawable {
        val out = StateListDrawable()
        out.addState(
            intArrayOf(android.R.attr.state_checked),
            generateCircle(context = context, color = color)
        )
        out.addState(
            StateSet.WILD_CARD,
            generateCircle(context = context, color = color)
        )
        return out
    }

    private fun generateCircle(
        context: Context,
        color: Int
    ): Drawable {
        val r = 18.dp.toPixels(context)
        val inset = 4.dp.toPixels(context)
        return LayerDrawable(
            arrayOf(
                GradientDrawable().apply {
                    shape = GradientDrawable.OVAL
                    setColor(color)
                    setSize(r, r)
                    setStroke(1, 0x33000000)
                },
            )
        ).apply {
            setLayerInset(0, inset, inset, inset, inset)
        }
    }

    private fun generateBG(color: Int): Drawable {
        return GradientDrawable().apply {
            cornerRadius = Float.MAX_VALUE
            setColor(color)
            setStroke(1, 0x88000000.toInt())
        }
    }

    fun chooseLauncher(activity: Activity) {
        val componentName = ComponentName(activity, FakeLauncherActivity::class.java)
        activity.packageManager.setComponentEnabledSetting(
            /* p0 = */ componentName,
            /* p1 = */ PackageManager.COMPONENT_ENABLED_STATE_ENABLED,
            /* p2 = */ PackageManager.DONT_KILL_APP
        )
        val selector = Intent(Intent.ACTION_MAIN)
        selector.addCategory(Intent.CATEGORY_HOME)
        selector.flags = Intent.FLAG_ACTIVITY_NEW_TASK
        activity.startActivity(selector)
        activity.packageManager.setComponentEnabledSetting(
            /* p0 = */ componentName,
            /* p1 = */ PackageManager.COMPONENT_ENABLED_STATE_DEFAULT,
            /* p2 = */ PackageManager.DONT_KILL_APP
        )
    }
}
