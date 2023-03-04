package com.roy93group.ext

import android.content.Context
import android.view.View
import com.loitp.core.ext.*
import com.roy93group.launcher.R

/**
 * Created by Loitp on 08,January,2023
 * Galaxy One company,
 * Vietnam
 * +840766040293
 * freuss47@gmail.com
 */

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

val colorsPicker = intArrayOf(
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

object C {
    var colorPrimary = getColor(R.color.color0)
    var colorBackground = getColor(R.color.colorPrimary)
}

fun View.setBackgroundLauncher() {
    when (getColorPrimary()) {
        COLOR_0 -> this.setBackgroundResource(R.drawable.ripple_color_0)
        COLOR_1 -> this.setBackgroundResource(R.drawable.ripple_color_1)
        COLOR_2 -> this.setBackgroundResource(R.drawable.ripple_color_2)
        COLOR_3 -> this.setBackgroundResource(R.drawable.ripple_color_3)
        COLOR_4 -> this.setBackgroundResource(R.drawable.ripple_color_4)
        COLOR_5 -> this.setBackgroundResource(R.drawable.ripple_color_5)
        COLOR_6 -> this.setBackgroundResource(R.drawable.ripple_color_6)
        COLOR_7 -> this.setBackgroundResource(R.drawable.ripple_color_7)
        COLOR_8 -> this.setBackgroundResource(R.drawable.ripple_color_8)
        COLOR_9 -> this.setBackgroundResource(R.drawable.ripple_color_9)
        COLOR_10 -> this.setBackgroundResource(R.drawable.ripple_color_10)
        COLOR_11 -> this.setBackgroundResource(R.drawable.ripple_color_11)
        COLOR_12 -> this.setBackgroundResource(R.drawable.ripple_color_12)
        COLOR_13 -> this.setBackgroundResource(R.drawable.ripple_color_13)
        COLOR_14 -> this.setBackgroundResource(R.drawable.ripple_color_14)
        COLOR_15 -> this.setBackgroundResource(R.drawable.ripple_color_15)
        COLOR_16 -> this.setBackgroundResource(R.drawable.ripple_color_16)
        else -> this.setBackgroundResource(R.drawable.ripple_color_0)
    }
}

private fun setColorPrimary(c: Int) {
    C.colorPrimary = c
}

fun getColorPrimary(): Int {
    return C.colorPrimary
}

fun setColorBackground(c: Int) {
    C.colorBackground = c
}

fun getColorBackground(): Int {
    return C.colorBackground
}

fun Context.updatePrimaryColor(
    newColor: Int
): Boolean {
    return if (newColor == getColorBackground()) {
        false
    } else {
        setColorPrimary(newColor)
        this.putInt(KEY_PRIMARY_COLOR, newColor)
        true
    }
}

fun Context.getPrimaryColor() {
    val c = this.getInt(
        key = KEY_PRIMARY_COLOR,
        defaultValue = getColor(R.color.color0)
    )
    setColorPrimary(c)
}

fun Context.updateBackgroundColor(
    newColor: Int
): Boolean {
    return if (newColor == getColorPrimary()) {
        false
    } else {
        setColorBackground(newColor)
        this.putInt(KEY_BACKGROUND_COLOR, newColor)
        true
    }
}

fun Context.getBackgroundColor(
) {
    val c = this.getInt(
        key = KEY_BACKGROUND_COLOR,
        defaultValue = getColor(R.color.colorPrimary)
    )
    setColorBackground(c)
}

fun Context.setSeekRadiusValue(
    seekRadiusValue: Int
) {
    this.putInt(KEY_SEEK_RADIUS_VALUE, seekRadiusValue)
}

fun Context.getSeekRadiusValue(): Int {
    return this.getInt(KEY_SEEK_RADIUS_VALUE, 0)
}

fun Context.setSeekPeekValue(
    seekPeekValue: Int
) {
    this.putInt(KEY_SEEK_PEEK_VALUE, seekPeekValue)
}

fun Context.getSeekPeekValue(): Int {
    return this.getInt(KEY_SEEK_PEEK_VALUE, 0)
}

fun Context.setOrientationValue(
    orientationValue: Int
) {
    this.putInt(KEY_ORIENTATION_VALUE, orientationValue)
}

fun Context.getOrientationValue(): Int {
    return this.getInt(KEY_ORIENTATION_VALUE, 1)
}

fun Context.setGravityValue(
    gravityValue: Int
) {
    this.putInt(KEY_GRAVITY_VALUE, gravityValue)
}

fun Context.getGravityValue(): Int {
    return this.getInt(KEY_GRAVITY_VALUE, 0)
}

fun Context.setChecked(
    isCheckedValue: Boolean
) {
    this.putBoolean(KEY_IS_CHECKED_VALUE, isCheckedValue)
}

fun Context.getChecked(): Boolean {
    return this.getBoolean(KEY_IS_CHECKED_VALUE, false)
}

fun Context.setOpenSearchWhenScrollTop(
    isEnable: Boolean
) {
    this.putBoolean(KEY_OPEN_SEARCH_WHEN_SCROLL_TOP, isEnable)
}

fun Context.getOpenSearchWhenScrollTop(): Boolean {
    return this.getBoolean(KEY_OPEN_SEARCH_WHEN_SCROLL_TOP, true)
}

fun Context.setDisplayFilterViews(
    isEnable: Boolean
) {
    this.putBoolean(KEY_DISPLAY_FILTER_VIEWS, isEnable)
}

fun Context.getDisplayFilterViews(): Boolean {
    return this.getBoolean(KEY_DISPLAY_FILTER_VIEWS, true)
}

fun Context.setAutoColorChanger(
    isEnable: Boolean
) {
    this.putBoolean(KEY_AUTO_COLOR_CHANGER, isEnable)
}

fun Context.getAutoColorChanger(): Boolean {
    return this.getBoolean(KEY_AUTO_COLOR_CHANGER, false)
}

fun Context.setDisplayAppIcon(
    isDisplay: Boolean
) {
    this.putBoolean(KEY_DISPLAY_APP_ICON, isDisplay)
}

fun Context.getDisplayAppIcon(): Boolean {
    return this.getBoolean(KEY_DISPLAY_APP_ICON, true)
}

fun Context.setForceColorIcon(
    isForceColorIcon: Boolean
) {
    this.putBoolean(KEY_FORCE_COLOR_ICON, isForceColorIcon)
}

fun Context.getForceColorIcon(): Boolean {
    return this.getBoolean(KEY_FORCE_COLOR_ICON, false)
}

fun isLightIconStatusBar(): Boolean {
    return getColorBackground() != COLOR_15
}

fun Context.setAppLock(
    packageName: String,
    isLock: Boolean
) {
    this.putBoolean(packageName, isLock)
}

fun Context.isAppLock(packageName: String): Boolean {
    return this.getBoolean(packageName, false)
}
