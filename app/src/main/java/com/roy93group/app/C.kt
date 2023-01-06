package com.roy93group.app

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Handler
import android.os.Looper
import android.view.View
import android.view.animation.AnimationUtils
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentActivity
import com.google.android.material.card.MaterialCardView
import com.loitp.core.common.Constants
import com.loitp.core.ext.getColor
import com.loitp.core.ext.tranIn
import com.loitp.core.ext.vibrate
import com.loitp.core.helper.gallery.GalleryCoreSplashActivity
import com.loitp.core.utilities.LSharedPrefsUtil
import com.loitp.core.utilities.LUIUtil
import com.loitp.data.ActivityData
import com.roy93group.launcher.R
import com.roy93group.launcher.data.items.LauncherItem
import com.roy93group.launcher.ui.LauncherActivity
import com.roy93group.lookerupper.ui.a.SearchActivity
import com.roy93group.views.*
import kotlinx.android.synthetic.main.layout_show_case.view.*
import me.toptas.fancyshowcase.FancyShowCaseView
import me.toptas.fancyshowcase.FocusShape
import me.toptas.fancyshowcase.listener.DismissListener
import me.toptas.fancyshowcase.listener.OnViewInflateListener


/**
 * Created by Loitp on 23,November,2022
 * Galaxy One company,
 * Vietnam
 * +840766040293
 * freuss47@gmail.com
 */
object C {
    private const val KEY_BACKGROUND_COLOR = "KEY_BACKGROUND_COLOR"
    private const val KEY_PRIMARY_COLOR = "KEY_PRIMARY_COLOR"
    private const val KEY_SEEK_RADIUS_VALUE = "KEY_SEEK_RADIUS_VALUE"
    private const val KEY_SEEK_PEEK_VALUE = "KEY_SEEK_PEEK_VALUE"
    private const val KEY_ORIENTATION_VALUE = "KEY_ORIENTATION_VALUE"
    private const val KEY_GRAVITY_VALUE = "KEY_GRAVITY_VALUE"
    private const val KEY_IS_CHECKED_VALUE = "KEY_IS_CHECKED_VALUE"
    private const val KEY_OPEN_SEARCH_WHEN_SCROLL_TOP = "KEY_OPEN_SEARCH_WHEN_SCROLL_TOP"
    private const val KEY_DISPLAY_FILTER_VIEWS = "KEY_DISPLAY_FILTER_VIEWS"
    private const val KEY_DISPLAY_APP_ICON = "KEY_DISPLAY_APP_ICON"
    private const val KEY_FORCE_COLOR_ICON = "KEY_FORCE_COLOR_ICON"
    private const val KEY_AUTO_COLOR_CHANGER = "KEY_AUTO_COLOR_CHANGER"

    private var colorPrimary = getColor(R.color.color0)
    private var colorBackground = getColor(R.color.colorPrimary)
    private fun setColorPrimary(c: Int) {
        this.colorPrimary = c
    }

    fun getColorPrimary(): Int {
        return this.colorPrimary
    }

    private fun setColorBackground(c: Int) {
        this.colorBackground = c
    }

    fun getColorBackground(): Int {
        return this.colorBackground
    }

    private var COLOR_0 = getColor(R.color.color0)
    private val COLOR_1 = getColor(R.color.color1)
    private val COLOR_2 = getColor(R.color.color2)
    private val COLOR_3 = getColor(R.color.color3)
    private val COLOR_4 = getColor(R.color.color4)
    private val COLOR_5 = getColor(R.color.color5)
    private val COLOR_6 = getColor(R.color.color6)
    private val COLOR_7 = getColor(R.color.color7)
    private val COLOR_8 = getColor(R.color.color8)
    private val COLOR_9 = getColor(R.color.color9)
    private val COLOR_10 = getColor(R.color.color10)
    private val COLOR_11 = getColor(R.color.color11)
    private val COLOR_12 = getColor(R.color.color12)
    private val COLOR_13 = getColor(R.color.color13)
    private val COLOR_14 = getColor(R.color.color14)
    val COLOR_15 = getColor(R.color.color15)
    private val COLOR_16 = getColor(R.color.color16)

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
        COLOR_16,
    )

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
        activity.tranIn()
    }

    fun setBackground(v: View?) {
        if (v == null) {
            return
        }
        when (getColorPrimary()) {
            COLOR_0 -> v.setBackgroundResource(R.drawable.ripple_color_0)
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
            COLOR_16 -> v.setBackgroundResource(R.drawable.ripple_color_16)
            else -> v.setBackgroundResource(R.drawable.ripple_color_0)
        }
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

    fun launchColorPrimary(
        activity: AppCompatActivity,
        isCancelableFragment: Boolean = true,
        title: String,
        des: String,
        warning: String,
        onDismiss: ((Int) -> Unit)? = null,
    ) {
        val fragment = BottomSheetColorPrimary(
            isCancelableFragment = isCancelableFragment,
            title = title,
            des = des,
            warning = warning,
            onDismiss = onDismiss,
        )
        fragment.show(activity.supportFragmentManager, fragment.tag)
    }

    fun launchColorBackground(
        activity: AppCompatActivity,
        isCancelableFragment: Boolean = true,
        title: String,
        des: String,
        warning: String,
        onDismiss: ((Int) -> Unit)? = null,
    ) {
        val fragment = BottomSheetColorBackground(
            isCancelableFragment = isCancelableFragment,
            title = title,
            des = des,
            warning = warning,
            onDismiss = onDismiss,
        )
        fragment.show(activity.supportFragmentManager, fragment.tag)
    }

    fun isValidColor(): Boolean {
        if (getColorPrimary() == getColorBackground()) {
            return false
        }
        return true
    }

    fun updatePrimaryColor(newColor: Int): Boolean {
        return if (newColor == getColorBackground()) {
            false
        } else {
            setColorPrimary(newColor)
            LSharedPrefsUtil.instance.putInt(KEY_PRIMARY_COLOR, newColor)
            true
        }
    }

    fun getPrimaryColor() {
        val c = LSharedPrefsUtil.instance.getInt(
            key = KEY_PRIMARY_COLOR,
            defaultValue = getColor(R.color.color0)
        )
        setColorPrimary(c)
    }

    fun updateBackgroundColor(newColor: Int): Boolean {
        return if (newColor == getColorPrimary()) {
            false
        } else {
            setColorBackground(newColor)
            LSharedPrefsUtil.instance.putInt(KEY_BACKGROUND_COLOR, newColor)
            true
        }
    }

    fun getBackgroundColor() {
        val c = LSharedPrefsUtil.instance.getInt(
            key = KEY_BACKGROUND_COLOR,
            defaultValue = getColor(R.color.colorPrimary)
        )
        setColorBackground(c)
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

    fun goToSearchScreen(context: Context) {
        ActivityData.instance.type = Constants.TYPE_ACTIVITY_TRANSITION_SLIDE_UP
        context.vibrate(milliseconds = 100L)
        context.startActivity(
            Intent(
                context,
                SearchActivity::class.java
            ).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        )
        context.tranIn()
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

    fun setDisplayFilterViews(isEnable: Boolean) {
        LSharedPrefsUtil.instance.putBoolean(KEY_DISPLAY_FILTER_VIEWS, isEnable)
    }

    fun getDisplayFilterViews(): Boolean {
        return LSharedPrefsUtil.instance.getBoolean(KEY_DISPLAY_FILTER_VIEWS, true)
    }

    fun setAutoColorChanger(isEnable: Boolean) {
        LSharedPrefsUtil.instance.putBoolean(KEY_AUTO_COLOR_CHANGER, isEnable)
    }

    fun getAutoColorChanger(): Boolean {
        return LSharedPrefsUtil.instance.getBoolean(KEY_AUTO_COLOR_CHANGER, false)
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

    fun isLightIconStatusBar(): Boolean {
        return getColorBackground() != COLOR_15
    }

    fun launchAboutLauncher(
        activity: AppCompatActivity,
        isCancelableFragment: Boolean = true,
    ) {
        val fragment = BottomSheetAboutLauncher(
            isCancelableFragment = isCancelableFragment,
        )
        fragment.show(activity.supportFragmentManager, fragment.tag)
    }

    fun launchSheetText(
        activity: FragmentActivity,
        isCancelableFragment: Boolean = true,
        title: String,
        content: String,
    ) {
        val fragment = BottomSheetText(
            isCancelableFragment = isCancelableFragment,
            title = title,
            content = content,
        )
        fragment.show(activity.supportFragmentManager, fragment.tag)
    }

    fun configAutoColorChanger(launcherActivity: LauncherActivity) {
        if (getAutoColorChanger()) {
            val newColorPrimary = colors.random()

            fun getColor(exceptColor: Int): Int {
                val newColor = colors.random()
                return if (exceptColor == newColor) {
                    getColor(exceptColor)
                } else {
                    newColor
                }
            }

            val newColorBackground = getColor(newColorPrimary)
            if (newColorPrimary != newColorBackground) {
                val resultUpdatePrimaryColor = updatePrimaryColor(newColorPrimary)
                val resultUpdateBackgroundColor = updateBackgroundColor(newColorBackground)
                if (resultUpdatePrimaryColor && resultUpdateBackgroundColor) {
                    LUIUtil.setWallpaperAndLockScreen(
                        context = launcherActivity,
                        color = newColorBackground,
                        isSetWallpaper = true,
                        isSetLockScreen = true,
                    )
                    launcherActivity.updateTheme()
                }
            }
        }
    }

    fun createFancyShowcase(
        activity: Activity,
        focusView: View,
        idShowOne: Boolean,
        focusShape: FocusShape,
        onDismissListener: DismissListener? = null,
        onViewInflated: ((View) -> Unit)? = null,
    ): FancyShowCaseView {
        val fancyView = FancyShowCaseView.Builder(activity)
            .focusOn(focusView)
            .backgroundColor(colorBackground)
            .focusShape(focusShape)
            .focusBorderColor(colorPrimary)
            .focusBorderSize(15)
            .customView(R.layout.layout_show_case, object : OnViewInflateListener {
                override fun onViewInflated(view: View) {
                    LUIUtil.recolorStatusBar(
                        context = view.context,
                        startColor = null,
                        endColor = colorBackground
                    )
                    LUIUtil.recolorNavigationBar(
                        context = view.context,
                        startColor = null,
                        endColor = colorBackground
                    )
                    onViewInflated?.invoke(view)
                }
            })
        if (idShowOne) {
            fancyView.showOnce(focusView.id.toString())
        }
        onDismissListener?.let {
            fancyView.dismissListener(it)
        }
        return fancyView.build()
    }

    fun showFancyShowCaseView(
        fancyShowCaseView: FancyShowCaseView?,
        textMain: String,
        textSub: String,
        gravity: Int,
    ) {
        if (fancyShowCaseView == null) {
            return
        }
        Handler(Looper.getMainLooper()).postDelayed({
            val mainAnimation = AnimationUtils.loadAnimation(
                /* context = */ fancyShowCaseView.context,
                /* id = */ R.anim.slide_in_left_fancy_showcase
            )
            mainAnimation.fillAfter = true
            val subAnimation = AnimationUtils.loadAnimation(
                /* context = */ fancyShowCaseView.context,
                /* id = */ R.anim.slide_in_left_fancy_showcase
            )
            subAnimation.fillAfter = true
            fancyShowCaseView.llc.gravity = gravity
            fancyShowCaseView.tvMain.apply {
                text = textMain
                setTextColor(colorPrimary)
                startAnimation(mainAnimation)
            }

            Handler(Looper.getMainLooper()).postDelayed({
                fancyShowCaseView.tvSub.apply {
                    text = textSub
                    setTextColor(colorPrimary)
                    startAnimation(subAnimation)
                }
            }, 80)
        }, 200)
    }
}
