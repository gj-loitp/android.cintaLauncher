package com.roy93group.ext

import android.app.Activity
import android.content.Intent
import android.os.Handler
import android.os.Looper
import android.view.View
import android.view.animation.AnimationUtils
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentActivity
import com.loitp.core.common.Constants
import com.loitp.core.ext.recolorNavigationBar
import com.loitp.core.ext.recolorStatusBar
import com.loitp.core.ext.setWallpaperAndLockScreen
import com.loitp.core.ext.tranIn
import com.loitp.core.helper.gallery.GalleryCoreSplashActivity
import com.roy93group.launcher.R
import com.roy93group.launcher.data.items.LauncherItem
import com.roy93group.launcher.ui.LauncherActivity
import com.roy93group.views.*
import kotlinx.android.synthetic.main.layout_show_case.view.*
import me.toptas.fancyshowcase.FancyShowCaseView
import me.toptas.fancyshowcase.FocusShape
import me.toptas.fancyshowcase.listener.DismissListener
import me.toptas.fancyshowcase.listener.OnViewInflateListener

/**
 * Created by Loitp on 08,January,2023
 * Galaxy One company,
 * Vietnam
 * +840766040293
 * freuss47@gmail.com
 */

fun Activity.launchWallpaper() {
    val intent = Intent(this, GalleryCoreSplashActivity::class.java)
    intent.putExtra(Constants.BKG_SPLASH_SCREEN, Constants.URL_IMG_11)
    // neu muon remove albumn nao thi cu pass id cua albumn do
    val removeAlbumFlickrList = ArrayList<String>()
    removeAlbumFlickrList.add(Constants.FLICKR_ID_STICKER)
    intent.putStringArrayListExtra(
        Constants.KEY_REMOVE_ALBUM_FLICKR_LIST,
        removeAlbumFlickrList
    )
    this.startActivity(intent)
    this.tranIn()
}

fun AppCompatActivity.launchAppOption(
    item: LauncherItem,
    isCancelableFragment: Boolean = true,
    onDismiss: ((Unit) -> Unit)? = null,
) {
    val fragment = BottomSheetAppOption(
        item = item,
        isCancelableFragment = isCancelableFragment,
        onDismiss = onDismiss,
    )
    fragment.show(this.supportFragmentManager, fragment.tag)
}

fun FragmentActivity?.launchSelector(
    isCancelableFragment: Boolean = true,
    title: String,
    des: String,
    value0: String,
    value1: String,
    firstIndexCheck: Int,
    onConfirm: ((Int) -> Unit)? = null,
    onDismiss: ((Unit) -> Unit)? = null,
) {
    if (this == null) {
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
    fragment.show(this.supportFragmentManager, fragment.tag)
}

fun AppCompatActivity.launchColorPrimary(
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
    fragment.show(this.supportFragmentManager, fragment.tag)
}

fun AppCompatActivity.launchColorBackground(
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
    fragment.show(this.supportFragmentManager, fragment.tag)
}

fun AppCompatActivity.launchAboutLauncher(
    isCancelableFragment: Boolean = true,
) {
    val fragment = BottomSheetAboutLauncher(
        isCancelableFragment = isCancelableFragment,
    )
    fragment.show(this.supportFragmentManager, fragment.tag)
}

fun FragmentActivity.launchSheetText(
    isCancelableFragment: Boolean = true,
    title: String,
    content: String,
) {
    val fragment = BottomSheetText(
        isCancelableFragment = isCancelableFragment,
        title = title,
        content = content,
    )
    fragment.show(this.supportFragmentManager, fragment.tag)
}

fun LauncherActivity.configAutoColorChanger() {
    if (this.getAutoColorChanger()) {
        val newColorPrimary = com.loitp.core.ext.colors.random()

        fun getColor(exceptColor: Int): Int {
            val newColor = com.loitp.core.ext.colors.random()
            return if (exceptColor == newColor) {
                getColor(exceptColor)
            } else {
                newColor
            }
        }

        val newColorBackground = getColor(newColorPrimary)
        if (newColorPrimary != newColorBackground) {
            val resultUpdatePrimaryColor = this.updatePrimaryColor(
                newColor = newColorPrimary
            )
            val resultUpdateBackgroundColor = this.updateBackgroundColor(
                newColor = newColorBackground
            )
            if (resultUpdatePrimaryColor && resultUpdateBackgroundColor) {
                this.setWallpaperAndLockScreen(
                    color = newColorBackground,
                    isSetWallpaper = true,
                    isSetLockScreen = true,
                )
                this.updateTheme()
            }
        }
    }
}

fun Activity.createFancyShowcase(
    focusView: View,
    idShowOne: Boolean,
    focusShape: FocusShape,
    onDismissListener: DismissListener? = null,
    onViewInflated: ((View) -> Unit)? = null,
): FancyShowCaseView {
    val fancyView = FancyShowCaseView.Builder(this)
        .focusOn(focusView)
        .backgroundColor(C.colorBackground)
        .focusShape(focusShape)
        .focusBorderColor(C.colorPrimary)
        .focusBorderSize(15)
        .customView(R.layout.layout_show_case, object : OnViewInflateListener {
            override fun onViewInflated(view: View) {
                view.context.recolorStatusBar(
                    startColor = null,
                    endColor = C.colorBackground
                )
                view.context.recolorNavigationBar(
                    startColor = null,
                    endColor = C.colorBackground
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

fun FancyShowCaseView?.showFancyShowCaseView(
    textMain: String,
    textSub: String,
    gravity: Int,
) {
    if (this == null) {
        return
    }
    Handler(Looper.getMainLooper()).postDelayed({
        val mainAnimation = AnimationUtils.loadAnimation(
            /* context = */ this.context,
            /* id = */ R.anim.slide_in_left_fancy_showcase
        )
        mainAnimation.fillAfter = true
        val subAnimation = AnimationUtils.loadAnimation(
            /* context = */ this.context,
            /* id = */ R.anim.slide_in_left_fancy_showcase
        )
        subAnimation.fillAfter = true
        this.llc.gravity = gravity
        this.tvMain.apply {
            text = textMain
            setTextColor(C.colorPrimary)
            startAnimation(mainAnimation)
        }

        Handler(Looper.getMainLooper()).postDelayed({
            this.tvSub.apply {
                text = textSub
                setTextColor(C.colorPrimary)
                startAnimation(subAnimation)
            }
        }, 80)
    }, 200)
}
