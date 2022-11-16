package com.roy93group.launcher.storage

/**
 * Created by Loitp on 16,November,2022
 * Galaxy One company,
 * Vietnam
 * +840766040293
 * freuss47@gmail.com
 */


object ScrollbarControllerSetting {
    val Settings.scrollbarController: Int
        get() = get(KEY_SCROLLBAR_CONTROLLER, SCROLLBAR_CONTROLLER_DEFAULT)

    var Settings.SettingsEditor.scrollbarController: Int
        get() = settings[KEY_SCROLLBAR_CONTROLLER, SCROLLBAR_CONTROLLER_DEFAULT]
        set(value) = KEY_SCROLLBAR_CONTROLLER set value

    private const val KEY_SCROLLBAR_CONTROLLER = "scrollbar_controller"

    @Suppress("unused")
    const val SCROLLBAR_CONTROLLER_ALPHABETIC = 0
    const val SCROLLBAR_CONTROLLER_BY_HUE = 1

    @Suppress("unused")
    const val SCROLLBAR_CONTROLLER_DEFAULT = SCROLLBAR_CONTROLLER_ALPHABETIC
}

@Suppress("unused")
object DoReshapeAdaptiveIconsSetting {
    val Settings.doReshapeAdaptiveIcons: Boolean
        get() = get(KEY, DEFAULT)

    var Settings.SettingsEditor.doReshapeAdaptiveIcons: Boolean
        get() = settings[KEY, DEFAULT]
        set(value) = KEY set value

    private const val KEY = "icons:reshape_adaptive"

    @Suppress("unused")
    const val DEFAULT = false
}
