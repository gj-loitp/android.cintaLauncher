package com.roy93group.launcher.util

import android.app.ActivityManager
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.os.Process
import androidx.core.app.ShareCompat
import com.loitp.annotation.IsAutoAnimation
import com.loitp.annotation.IsFullScreen
import com.loitp.annotation.IsKeepScreenOn
import com.loitp.annotation.LogTag
import com.loitp.core.base.BaseActivityFont
import com.loitp.core.ext.setSafeOnClickListener
import com.loitp.core.ext.tranIn
import com.roy93group.ext.*
import com.roy93group.launcher.BuildConfig
import com.roy93group.launcher.R
import kotlinx.android.synthetic.main.activity_stack_trace.*
import kotlin.system.exitProcess

/**
 * Updated by Loitp on 2022.12.18
 * Galaxy One company,
 * Vietnam
 * +840766040293
 * freuss47@gmail.com
 */
@LogTag("LauncherActivity")
@IsFullScreen(false)
@IsAutoAnimation(false)
@IsKeepScreenOn(false)
class StackTraceActivity : BaseActivityFont() {

    companion object {
        fun init(context: Context) {
            Thread.setDefaultUncaughtExceptionHandler { _, throwable ->
                try {
                    context.startActivity(
                        Intent(context, StackTraceActivity::class.java)
                            .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                            .putExtra("throwable", throwable)
                    )
                    context.tranIn()
                    Process.killProcess(Process.myPid())
                    exitProcess(0)
                } catch (e: Throwable) {
                    e.printStackTrace()
                }
            }
        }
    }

    override fun setLayoutResourceId(): Int {
        return R.layout.activity_stack_trace
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        changeStatusBarContrastStyle(
            lightIcons = isLightIconStatusBar(),
            colorBackground = getColorBackground(),
            withRecolorEfx = false,
        )
        setupViews()
    }

    private fun setupViews() {
        try {
            val colorPrimary = getColorPrimary()
            val colorBackground = getColorBackground()

            cl.setBackgroundColor(colorBackground)
            tvTitle.setTextColor(colorPrimary)
            tvStackTrace.setTextColor(colorPrimary)
            btSend.apply {
                setTextColor(colorBackground)
                this.setBackgroundLauncher()
            }

            val t = intent.extras!!["throwable"] as Throwable

            val str = buildString {
                appendLine(t.toString())
                appendLine()
                appendLine("Device info:")
                appendLine("    api: " + Build.VERSION.SDK_INT)
                appendLine("    brand: " + Build.BRAND)
                appendLine("    model: " + Build.MODEL)
                appendLine("    ram: " + run {
                    val memInfo = ActivityManager.MemoryInfo()
                    getSystemService(ActivityManager::class.java).getMemoryInfo(memInfo)
                    formatByteAmount(memInfo.totalMem)
                })
                appendLine("Version: " + BuildConfig.VERSION_NAME + " (code: " + BuildConfig.VERSION_CODE + ')')
                appendLine()
                for (tr in t.stackTrace)
                    appendLine().append(format(tr)).appendLine()
                for (throwable in t.suppressed)
                    for (tr in throwable.stackTrace)
                        appendLine().append(format(tr)).appendLine()
                t.cause?.let {
                    for (tr in it.stackTrace)
                        appendLine().append(format(tr)).appendLine()
                }
            }

            tvStackTrace.text = str

            btSend.setSafeOnClickListener {
                onBaseBackPressed()
                ShareCompat.IntentBuilder(this)
                    .setType("text/plain")
                    .setText(str)
                    .setSubject(getString(R.string.crash_email_subject))
                    .addEmailTo(getString(R.string.dev_email))
                    .startChooser()
            }
        } catch (e: Throwable) {
            e.printStackTrace()
        }
    }

    private fun formatByteAmount(bytes: Long): String {
        return when {
            bytes < 1000 -> "$bytes B"
            bytes < 1000_000 -> "${bytes / 1000} KB"
            bytes < 1000_000_000 -> "${bytes / 1000_000} MB"
            else /* bytes < 1000_000_000_000 */ -> "${bytes / 1000_000_000} GB"
        }
    }

    private fun format(e: StackTraceElement) = buildString {
        if (e.isNativeMethod) {
            append("(Native Method)")
        } else if (e.fileName != null) {
            if (e.lineNumber >= 0) {
                append("at ")
                append(e.fileName)
                append(" [")
                append(e.lineNumber)
                append("]")
            } else {
                append("at ")
                append(e.fileName)
            }
        } else {
            if (e.lineNumber >= 0) {
                append("(Unknown Source:")
                append(e.lineNumber)
                append(")")
            } else {
                append("(Unknown Source)")
            }
        }
        appendLine()
        append(e.className)
        append(".")
        append(e.methodName)
    }
}
