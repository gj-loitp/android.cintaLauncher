package com.roy93group.launcher.providers.color.theme

import androidx.core.graphics.ColorUtils
import androidx.core.graphics.luminance

/**
 * Updated by Loitp on 2022.12.16
 * Galaxy One company,
 * Vietnam
 * +840766040293
 * freuss47@gmail.com
 */
class ColorTheme {
    companion object {

        fun titleColorForBG(background: Int): Int {
            return (if (background.luminance > .6f) 0 else 0xffffff) or 0xff000000.toInt()
        }

        fun textColorForBG(background: Int): Int {
            return (if (background.luminance > .6f) 0 else 0xffffff) or 0xd2000000.toInt()
        }

        fun hintColorForBG(background: Int): Int {
            return (if (background.luminance > .6f) 0 else 0xffffff) or 0x55000000
        }

        fun tintWithColor(base: Int, color: Int): Int {
            val tintHSL = FloatArray(3)
            val baseHSL = FloatArray(3)
            ColorUtils.colorToHSL(color, tintHSL)
            ColorUtils.colorToHSL(base, baseHSL)
            tintHSL[2] = baseHSL[2]
            return ColorUtils.HSLToColor(tintHSL) and 0xffffff or (base and 0xff000000.toInt())
        }

        @Suppress("unused")
        fun darkestVisibleOn(backgroundColor: Int, color: Int): Int {
            val l = backgroundColor.luminance
            val lab = DoubleArray(3)
            ColorUtils.colorToLAB(color, lab)
            lab[0] = if (l < 0.03) 100.0
            else 20.0
            return ColorUtils.LABToColor(lab[0], lab[1], lab[2])
        }
    }
}
