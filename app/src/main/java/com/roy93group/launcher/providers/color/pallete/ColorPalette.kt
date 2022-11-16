package com.roy93group.launcher.providers.color.pallete

interface ColorPalette {
    val estimatedWallColor: Int
    val neutralVeryDark: Int
    val neutralDark: Int
    val neutralMedium: Int
    val neutralLight: Int
    val neutralVeryLight: Int
    val primary: Int
    val secondary: Int

    companion object {
        val wallColor: Int
            get() = colorPaletteInstance.estimatedWallColor

        private var colorPaletteInstance: ColorPalette = DefaultPalette

        fun getCurrent(): ColorPalette = colorPaletteInstance
    }
}
