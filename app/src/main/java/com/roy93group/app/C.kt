package com.roy93group.app

import android.app.Activity
import android.content.ComponentName
import android.content.Context
import android.content.Context.VIBRATOR_SERVICE
import android.content.Intent
import android.content.pm.PackageManager
import android.content.res.ColorStateList
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
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.loitpcore.core.common.Constants
import com.loitpcore.core.helper.gallery.GalleryCoreSplashActivity
import com.loitpcore.core.utilities.LActivityUtil
import com.loitpcore.core.utilities.LAppResource
import com.loitpcore.core.utilities.LSocialUtil
import com.roy93group.launcher.R
import com.roy93group.launcher.util.FakeLauncherActivity
import com.roy93group.views.BottomSheetOption
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
    var COLOR_0 = Color.parseColor("#FF0000")
    private val COLOR_1 = Color.parseColor("#F0E9D2")
    private val COLOR_2 = Color.parseColor("#FF9E9E")
    private val COLOR_3 = Color.parseColor("#F2F7A1")
    private val COLOR_4 = Color.parseColor("#FFBF00")
    private val COLOR_5 = Color.parseColor("#E5B8F4")
    private val COLOR_6 = Color.parseColor("#0002A1")
    private val COLOR_7 = Color.parseColor("#22A39F")
    private val COLOR_8 = Color.parseColor("#D23369")
    private val COLOR_9 = Color.parseColor("#FFE15D")
    private val COLOR_10 = Color.parseColor("#B3FFAE")
    private val COLOR_11 = Color.parseColor("#E0144C")
    private val COLOR_12 = Color.parseColor("#38E54D")
    private val COLOR_13 = Color.parseColor("#FF74B1")
    private val COLOR_14 = Color.parseColor("#3D8361")
    private val COLOR_15 = Color.parseColor("#FFFFFF")

    val COLOR_FAST_SCROLL_TEXT = COLOR_PRIMARY
    const val COLOR_FAST_SCROLL_TEXT_HIGHLIGHT = Color.WHITE
    val COLOR_FAST_SCROLL_BACKGROUND = COLOR_0

    val colors = intArrayOf(
        COLOR_0,
        COLOR_1,
        COLOR_2,
        COLOR_3,
        COLOR_4,
        COLOR_5,
        COLOR_6,
        COLOR_7,
        COLOR_8,
        COLOR_9,
        COLOR_10,
        COLOR_11,
        COLOR_12,
        COLOR_13,
        COLOR_14,
        COLOR_15,
    )

    fun searchIconPack(activity: Activity) {
        val url = "market://search?q=icon%20pack&c=apps"
        try {
            activity.startActivity(
                Intent(
                    Intent.ACTION_VIEW,
                    Uri.parse(url)
                )
            )
            LActivityUtil.tranIn(activity)
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
            LActivityUtil.tranIn(activity)
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
        LActivityUtil.tranIn(activity)
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
        LActivityUtil.tranIn(activity)
        activity.packageManager.setComponentEnabledSetting(
            /* p0 = */ componentName,
            /* p1 = */ PackageManager.COMPONENT_ENABLED_STATE_DEFAULT,
            /* p2 = */ PackageManager.DONT_KILL_APP
        )
    }

    fun launchWallpaper(activity: Activity) {
        val intent = Intent(activity, GalleryCoreSplashActivity::class.java)
        intent.putExtra(Constants.BKG_SPLASH_SCREEN, Constants.URL_IMG_11)
        // neu muon remove albumn nao thi cu pass id cua albumn do
        val removeAlbumFlickrList = ArrayList<String>()
        removeAlbumFlickrList.add(Constants.FLICKR_ID_STICKER)
        intent.putStringArrayListExtra(
            Constants.KEY_REMOVE_ALBUM_FLICKR_LIST,
            removeAlbumFlickrList
        )
        activity.startActivity(intent)
        LActivityUtil.tranIn(activity)
    }

    fun setBackground(v: View?) {
        //TODO swich case fav color
//        v?.setBackgroundResource(R.drawable.ripple_color_primary_2)
        v?.setBackgroundResource(R.drawable.ripple_color_red)
    }

    fun setBackgroundTintList(v: View?) {
        v?.backgroundTintList = ColorStateList.valueOf(COLOR_0)
    }

    fun launchSelector(
        activity: AppCompatActivity,
        isCancelableFragment: Boolean = true,
        title: String,
        des: String,
        value0: String,
        value1: String,
        firstIndexCheck: Int,
        onConfirm: ((Int) -> Unit)? = null,
        onDismiss: ((Unit) -> Unit)? = null,
    ) {
        val fragment = BottomSheetOption(
            isCancelableFragment = isCancelableFragment,
            title = title,
            des = des,
            value0 = value0,
            value1 = value1,
            firstIndexCheck = firstIndexCheck,
            onConfirm = onConfirm,
            onDismiss = onDismiss,
        )
        fragment.show(activity.supportFragmentManager, fragment.tag)
    }
}
