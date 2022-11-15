package com.roy93group.launcher.providers.color

import androidx.annotation.Keep
import com.roy93group.launcher.providers.color.pallete.ColorPalette
import com.roy93group.launcher.providers.color.theme.ColorTheme
import com.roy93group.launcher.providers.color.theme.DarkColorTheme
import com.roy93group.launcher.providers.color.theme.LightColorTheme

@Keep
data class ColorThemeOptions(
    val mode: DayNight
) {
    enum class DayNight {
        AUTO,

        @Suppress("unused")
        DARK,
        LIGHT,
    }

    fun createColorTheme(palette: ColorPalette): ColorTheme {
        return if (mode == DayNight.LIGHT) LightColorTheme(palette)
        else DarkColorTheme(palette)
    }

    override fun toString() = "${javaClass.simpleName} { mode: $mode }"
}
