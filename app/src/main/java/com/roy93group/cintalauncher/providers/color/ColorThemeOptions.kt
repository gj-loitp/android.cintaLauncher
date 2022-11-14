package com.roy93group.cintalauncher.providers.color

import com.roy93group.cintalauncher.providers.color.pallete.ColorPalette
import com.roy93group.cintalauncher.providers.color.theme.ColorTheme
import com.roy93group.cintalauncher.providers.color.theme.DarkColorTheme
import com.roy93group.cintalauncher.providers.color.theme.LightColorTheme

data class ColorThemeOptions(
    val mode: DayNight
) {
    enum class DayNight {
        AUTO,
        DARK,
        LIGHT,
    }

    fun createColorTheme(palette: ColorPalette): ColorTheme {
        return if (mode == DayNight.LIGHT) LightColorTheme(palette)
        else DarkColorTheme(palette)
    }

    override fun toString() = "${javaClass.simpleName} { mode: $mode }"
}