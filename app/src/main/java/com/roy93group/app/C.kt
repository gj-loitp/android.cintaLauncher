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
import android.provider.Settings
import android.util.StateSet
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentActivity
import com.google.android.material.card.MaterialCardView
import com.loitp.core.common.Constants
import com.loitp.core.helper.gallery.GalleryCoreSplashActivity
import com.loitp.core.utilities.*
import com.loitp.data.ActivityData
import com.roy93group.launcher.R
import com.roy93group.launcher.data.items.LauncherItem
import com.roy93group.launcher.util.FakeLauncherActivity
import com.roy93group.lookerupper.ui.a.SearchActivity
import com.roy93group.views.BottomSheetAppOption
import com.roy93group.views.BottomSheetColor
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
    private const val KEY_MAIN_COLOR = "KEY_MAIN_COLOR"
    private const val KEY_SEEK_RADIUS_VALUE = "KEY_SEEK_RADIUS_VALUE"
    private const val KEY_SEEK_PEEK_VALUE = "KEY_SEEK_PEEK_VALUE"
    private const val KEY_ORIENTATION_VALUE = "KEY_ORIENTATION_VALUE"
    private const val KEY_GRAVITY_VALUE = "KEY_GRAVITY_VALUE"
    private const val KEY_IS_CHECKED_VALUE = "KEY_IS_CHECKED_VALUE"
    private const val KEY_OPEN_SEARCH_WHEN_SCROLL_TOP = "KEY_OPEN_SEARCH_WHEN_SCROLL_TOP"
    private const val KEY_DISPLAY_APP_ICON = "KEY_DISPLAY_APP_ICON"
    private const val KEY_FORCE_COLOR_ICON = "KEY_FORCE_COLOR_ICON"

    val COLOR_PRIMARY = LAppResource.getColor(R.color.colorPrimary)
    var COLOR_0 = LAppResource.getColor(R.color.color0)
    private val COLOR_1 = LAppResource.getColor(R.color.color1)
    private val COLOR_2 = LAppResource.getColor(R.color.color2)
    private val COLOR_3 = LAppResource.getColor(R.color.color3)
    private val COLOR_4 = LAppResource.getColor(R.color.color4)
    private val COLOR_5 = LAppResource.getColor(R.color.color5)
    private val COLOR_6 = LAppResource.getColor(R.color.color6)
    private val COLOR_7 = LAppResource.getColor(R.color.color7)
    private val COLOR_8 = LAppResource.getColor(R.color.color8)
    private val COLOR_9 = LAppResource.getColor(R.color.color9)
    private val COLOR_10 = LAppResource.getColor(R.color.color10)
    private val COLOR_11 = LAppResource.getColor(R.color.color11)
    private val COLOR_12 = LAppResource.getColor(R.color.color12)
    private val COLOR_13 = LAppResource.getColor(R.color.color13)
    private val COLOR_14 = LAppResource.getColor(R.color.color14)
    private val COLOR_15 = LAppResource.getColor(R.color.color15)

    val COLOR_FAST_SCROLL_TEXT = COLOR_PRIMARY
    const val COLOR_FAST_SCROLL_TEXT_HIGHLIGHT = Color.WHITE

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
        if (v == null) {
            return
        }
        when (COLOR_0) {
            COLOR_1 -> v.setBackgroundResource(R.drawable.ripple_color_1)
            COLOR_2 -> v.setBackgroundResource(R.drawable.ripple_color_2)
            COLOR_3 -> v.setBackgroundResource(R.drawable.ripple_color_3)
            COLOR_4 -> v.setBackgroundResource(R.drawable.ripple_color_4)
            COLOR_5 -> v.setBackgroundResource(R.drawable.ripple_color_5)
            COLOR_6 -> v.setBackgroundResource(R.drawable.ripple_color_6)
            COLOR_7 -> v.setBackgroundResource(R.drawable.ripple_color_7)
            COLOR_8 -> v.setBackgroundResource(R.drawable.ripple_color_8)
            COLOR_9 -> v.setBackgroundResource(R.drawable.ripple_color_9)
            COLOR_10 -> v.setBackgroundResource(R.drawable.ripple_color_10)
            COLOR_11 -> v.setBackgroundResource(R.drawable.ripple_color_11)
            COLOR_12 -> v.setBackgroundResource(R.drawable.ripple_color_12)
            COLOR_13 -> v.setBackgroundResource(R.drawable.ripple_color_13)
            COLOR_14 -> v.setBackgroundResource(R.drawable.ripple_color_14)
            COLOR_15 -> v.setBackgroundResource(R.drawable.ripple_color_15)
            else -> v.setBackgroundResource(R.drawable.ripple_color_0)
        }
    }

    fun setBackgroundTintList(v: View?) {
        v?.backgroundTintList = ColorStateList.valueOf(COLOR_0)
    }

    fun launchSelector(
        activity: FragmentActivity?,
        isCancelableFragment: Boolean = true,
        title: String,
        des: String,
        value0: String,
        value1: String,
        firstIndexCheck: Int,
        onConfirm: ((Int) -> Unit)? = null,
        onDismiss: ((Unit) -> Unit)? = null,
    ) {
        if (activity == null) {
            return
        }
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

    fun launchColor(
        activity: AppCompatActivity,
        isCancelableFragment: Boolean = true,
        title: String,
        des: String,
        warning: String,
        onDismiss: ((Int) -> Unit)? = null,
    ) {
        val fragment = BottomSheetColor(
            isCancelableFragment = isCancelableFragment,
            title = title,
            des = des,
            warning = warning,
            onDismiss = onDismiss,
        )
        fragment.show(activity.supportFragmentManager, fragment.tag)
    }

    fun updateMainColor(newColor: Int) {
        COLOR_0 = newColor
        LSharedPrefsUtil.instance.putInt(KEY_MAIN_COLOR, COLOR_0)
    }

    fun getMainColor() {
        COLOR_0 =
            LSharedPrefsUtil.instance.getInt(KEY_MAIN_COLOR, LAppResource.getColor(R.color.color0))
    }

    fun launchAppOption(
        activity: AppCompatActivity,
        item: LauncherItem,
        isCancelableFragment: Boolean = true,
        onDismiss: ((Unit) -> Unit)? = null,
    ) {
        val fragment = BottomSheetAppOption(
            item = item,
            isCancelableFragment = isCancelableFragment,
            onDismiss = onDismiss,
        )
        fragment.show(activity.supportFragmentManager, fragment.tag)
    }

    fun launchSystemSetting(
        context: Context,
        packageName: String
    ) {
        val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
        intent.data = Uri.parse("package:$packageName")
        context.startActivity(intent)
        LActivityUtil.tranIn(context)
    }

    fun uninstallApp(
        activity: Activity,
        packageName: String
    ) {
        val intent = Intent(Intent.ACTION_DELETE)
        intent.data = Uri.parse("package:$packageName")
        activity.startActivity(intent)
        LActivityUtil.tranIn(activity)
    }

    fun goToSearchScreen(context: Context) {
        ActivityData.instance.type = Constants.TYPE_ACTIVITY_TRANSITION_SLIDE_UP
        vibrate(milliseconds = 100)
        context.startActivity(
            Intent(
                context,
                SearchActivity::class.java
            ).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        )
        LActivityUtil.tranIn(context)
    }

    fun setSeekRadiusValue(seekRadiusValue: Int) {
        LSharedPrefsUtil.instance.putInt(KEY_SEEK_RADIUS_VALUE, seekRadiusValue)
    }

    fun getSeekRadiusValue(): Int {
        return LSharedPrefsUtil.instance.getInt(KEY_SEEK_RADIUS_VALUE, 0)
    }

    fun setSeekPeekValue(seekPeekValue: Int) {
        LSharedPrefsUtil.instance.putInt(KEY_SEEK_PEEK_VALUE, seekPeekValue)
    }

    fun getSeekPeekValue(): Int {
        return LSharedPrefsUtil.instance.getInt(KEY_SEEK_PEEK_VALUE, 0)
    }

    fun setOrientationValue(orientationValue: Int) {
        LSharedPrefsUtil.instance.putInt(KEY_ORIENTATION_VALUE, orientationValue)
    }

    fun getOrientationValue(): Int {
        return LSharedPrefsUtil.instance.getInt(KEY_ORIENTATION_VALUE, 1)
    }

    fun setGravityValue(gravityValue: Int) {
        LSharedPrefsUtil.instance.putInt(KEY_GRAVITY_VALUE, gravityValue)
    }

    fun getGravityValue(): Int {
        return LSharedPrefsUtil.instance.getInt(KEY_GRAVITY_VALUE, 0)
    }

    fun setChecked(isCheckedValue: Boolean) {
        LSharedPrefsUtil.instance.putBoolean(KEY_IS_CHECKED_VALUE, isCheckedValue)
    }

    fun getChecked(): Boolean {
        return LSharedPrefsUtil.instance.getBoolean(KEY_IS_CHECKED_VALUE, false)
    }

    fun setCornerCardView(activity: Activity, cardView: MaterialCardView) {
        val radiusTL = activity.resources.getDimension(R.dimen.round_large)
        val radiusTR = activity.resources.getDimension(R.dimen.round_large)
        val radiusBL = 0f
        val radiusBR = 0f
        LUIUtil.setCornerCardView(
            cardView = cardView,
            radiusTL = radiusTL,
            radiusTR = radiusTR,
            radiusBL = radiusBL,
            radiusBR = radiusBR
        )
    }

    fun setOpenSearchWhenScrollTop(isEnable: Boolean) {
        LSharedPrefsUtil.instance.putBoolean(KEY_OPEN_SEARCH_WHEN_SCROLL_TOP, isEnable)
    }

    fun getOpenSearchWhenScrollTop(): Boolean {
        return LSharedPrefsUtil.instance.getBoolean(KEY_OPEN_SEARCH_WHEN_SCROLL_TOP, true)
    }

    fun setDisplayAppIcon(isDisplay: Boolean) {
        LSharedPrefsUtil.instance.putBoolean(KEY_DISPLAY_APP_ICON, isDisplay)
    }

    fun getDisplayAppIcon(): Boolean {
        return LSharedPrefsUtil.instance.getBoolean(KEY_DISPLAY_APP_ICON, true)
    }

    fun setForceColorIcon(isForceColorIcon: Boolean) {
        LSharedPrefsUtil.instance.putBoolean(KEY_FORCE_COLOR_ICON, isForceColorIcon)
    }

    fun getForceColorIcon(): Boolean {
        return LSharedPrefsUtil.instance.getBoolean(KEY_FORCE_COLOR_ICON, false)
    }
}
