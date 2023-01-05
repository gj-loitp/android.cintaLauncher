package com.roy93group.launcher.ui.intro

import android.Manifest
import android.content.ComponentName
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.View
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import com.loitp.core.ext.setSafeOnClickListener
import com.loitp.core.utilities.LActivityUtil
import com.roy93group.app.C
import com.roy93group.launcher.BuildConfig
import com.roy93group.launcher.R
import com.roy93group.launcher.providers.feed.suggestions.SuggestionsManager
import com.roy93group.launcher.ui.LauncherActivity
import kotlinx.android.synthetic.main.frm_intro_permissions.*
import me.toptas.fancyshowcase.FancyShowCaseQueue
import me.toptas.fancyshowcase.FancyShowCaseView
import me.toptas.fancyshowcase.FocusShape
import me.toptas.fancyshowcase.listener.DismissListener

/**
 * Updated by Loitp on 2022.12.17
 * Galaxy One company,
 * Vietnam
 * +840766040293
 * freuss47@gmail.com
 */
class FrmPermissions : FrmWithNext(R.layout.frm_intro_permissions) {
    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?
    ) {
        super.onViewCreated(view, savedInstanceState)
        setupViews()
        //disabled permission storage
        tvStorage.isVisible = false
        tvStorageDes.isVisible = false
        buttonStorage.isVisible = false
        tickStorage.isVisible = false

        updatePermissionStatus()
        initShowcase()
    }

    private fun setupViews() {
        val colorPrimary = C.getColorPrimary()
        val colorBackground = C.getColorBackground()

        tickStorage.setColorFilter(colorPrimary)
        tickContacts.setColorFilter(colorPrimary)
        tickNotifications.setColorFilter(colorPrimary)
        tickUsageAccess.setColorFilter(colorPrimary)
        tvPermissions.apply {
            setTextColor(colorPrimary)
            setSafeOnClickListener {
                (activity as? IntroActivity)?.onBaseBackPressed()
            }
        }
        tvStorage.setTextColor(colorPrimary)
        tvStorageDes.setTextColor(colorPrimary)
        buttonStorage.apply {
            setTextColor(colorBackground)
            C.setBackground(this)
        }
        tvContacts.setTextColor(colorPrimary)
        tvContactsDes.setTextColor(colorPrimary)
        buttonContacts.apply {
            setTextColor(colorBackground)
            C.setBackground(this)
        }
        tvNotifications.setTextColor(colorPrimary)
        tvNotificationsDes.setTextColor(colorPrimary)
        buttonNotifications.apply {
            setTextColor(colorBackground)
            C.setBackground(this)
        }
        tvUsageAccess.setTextColor(colorPrimary)
        tvUsageAccessDes.setTextColor(colorPrimary)
        buttonUsageAccess.apply {
            setTextColor(colorBackground)
            C.setBackground(this)
        }
    }

    fun updatePermissionStatus() {
        if (
            ContextCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.READ_EXTERNAL_STORAGE
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            buttonStorage.isVisible = false
            tickStorage.isVisible = true
        } else {
            buttonStorage.setSafeOnClickListener {
                requestStoragePermission()
            }
        }

        if (
            ContextCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.READ_CONTACTS
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            buttonContacts.isVisible = false
            tickContacts.isVisible = true
        } else {
            buttonContacts.setSafeOnClickListener {
                requestContactsPermission()
            }
        }

        if (NotificationManagerCompat.getEnabledListenerPackages(requireContext())
                .contains(requireContext().packageName)
        ) {
            buttonNotifications.isVisible = false
            tickNotifications.isVisible = true
        } else {
            buttonNotifications.setSafeOnClickListener(::requestNotificationsPermission)
        }

        if (SuggestionsManager.checkUsageAccessPermission(requireContext())) {
            buttonUsageAccess.isVisible = false
            tickUsageAccess.isVisible = true
        } else {
            buttonUsageAccess.setSafeOnClickListener(::requestUsageAccessPermission)
        }
    }

    override fun next(
        activity: IntroActivity,
        isCheckedPolicy: Boolean
    ) {
        if (isFullPermission()) {
            if (isCheckedPolicy) {
                val home = ComponentName(requireContext(), LauncherActivity::class.java)
                requireContext().packageManager.setComponentEnabledSetting(
                    /* p0 = */ home,
                    /* p1 = */ PackageManager.COMPONENT_ENABLED_STATE_ENABLED,
                    /* p2 = */ PackageManager.DONT_KILL_APP
                )
                val intro =
                    ComponentName(requireContext(), IntroActivity::class.java.name + "Alias")
                requireContext().packageManager.setComponentEnabledSetting(
                    /* p0 = */ intro,
                    /* p1 = */ PackageManager.COMPONENT_ENABLED_STATE_DISABLED,
                    /* p2 = */ PackageManager.DONT_KILL_APP
                )
                startActivity(
                    Intent(
                        /* packageContext = */ requireContext(),
                        /* cls = */ LauncherActivity::class.java
                    )
                )
                LActivityUtil.tranIn(activity)
                C.chooseLauncher(requireActivity())
            } else {
                activity.showSnackBarError(getString(R.string.pls_read_policy_first))
            }
        } else {
            activity.showSnackBarError(getString(R.string.pls_grant_permission_first))
        }
    }

    private fun requestStoragePermission() {
        if (ContextCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.READ_EXTERNAL_STORAGE
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            requireActivity().requestPermissions(
                arrayOf(
                    Manifest.permission.READ_EXTERNAL_STORAGE
                ), 0
            )
        }
    }

    private fun requestContactsPermission() {
        if (ContextCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.READ_CONTACTS
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            requireActivity().requestPermissions(
                arrayOf(
                    Manifest.permission.READ_CONTACTS
                ), 0
            )
        }
    }

    private fun requestNotificationsPermission(v: View) {
        if (!NotificationManagerCompat.getEnabledListenerPackages(v.context)
                .contains(v.context.packageName)
        ) {
            v.context.startActivity(
                Intent("android.settings.ACTION_NOTIFICATION_LISTENER_SETTINGS").addFlags(
                    Intent.FLAG_ACTIVITY_NEW_TASK
                )
            )
            LActivityUtil.tranIn(activity)
        }
    }

    private fun requestUsageAccessPermission(v: View) {
        v.context.startActivity(
            Intent(android.provider.Settings.ACTION_USAGE_ACCESS_SETTINGS).addFlags(
                Intent.FLAG_ACTIVITY_NEW_TASK
            )
        )
        LActivityUtil.tranIn(activity)
    }

    private fun isFullPermission(): Boolean {
        if (BuildConfig.DEBUG) {
            return true
        }
        return (tickContacts.isVisible
                && tickNotifications.isVisible
                && tickUsageAccess.isVisible)
    }

    private fun initShowcase() {
        var fancyView: FancyShowCaseView? = null
        fancyView = C.createFancyShowcase(
            activity = requireActivity(),
            focusView = buttonContacts,
            idShowOne = true,
            focusShape = FocusShape.CIRCLE,
            onDismissListener = object : DismissListener {
                override fun onDismiss(id: String?) {

                }

                override fun onSkipped(id: String?) {
                }
            },
            onViewInflated = {
                C.showFancyShowCaseView(
                    fancyShowCaseView = fancyView,
                    textMain = getString(R.string.grant_permissions),
                    textSub = getString(R.string.app_name) + " " + getString(R.string.needs_per),
                )
            }
        )
        FancyShowCaseQueue().apply {
            add(fancyView)
            show()
        }
    }
}
