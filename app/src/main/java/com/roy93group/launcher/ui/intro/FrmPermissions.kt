package com.roy93group.launcher.ui.intro

import android.Manifest
import android.content.ComponentName
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import com.loitpcore.core.utilities.LActivityUtil
import com.roy93group.app.C
import com.roy93group.launcher.BuildConfig
import com.roy93group.launcher.R
import com.roy93group.launcher.providers.feed.suggestions.SuggestionsManager
import com.roy93group.launcher.ui.LauncherActivity

/**
 * Updated by Loitp on 2022.12.17
 * Galaxy One company,
 * Vietnam
 * +840766040293
 * freuss47@gmail.com
 */
class FrmPermissions : FrmWithNext(R.layout.frm_intro_permissions) {

    private var tickStorage: ImageView? = null
    private var tickContacts: ImageView? = null
    private var tickNotifications: ImageView? = null
    private var tickUsageAccess: ImageView? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return super.onCreateView(inflater, container, savedInstanceState)
            ?.apply(::updatePermissionStatus)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupViews()
    }

    private fun setupViews() {
        view?.let { v ->
            tickStorage = v.findViewById<ImageView>(R.id.tickStorage).apply {
                setColorFilter(C.COLOR_PRIMARY_2)
            }
            tickContacts = v.findViewById<ImageView>(R.id.tickContacts).apply {
                setColorFilter(C.COLOR_PRIMARY_2)
            }
            tickNotifications = v.findViewById<ImageView>(R.id.tickNotifications).apply {
                setColorFilter(C.COLOR_PRIMARY_2)
            }
            tickUsageAccess = v.findViewById<ImageView>(R.id.tickUsageAccess).apply {
                setColorFilter(C.COLOR_PRIMARY_2)
            }

            v.findViewById<TextView>(R.id.tvPermissions).apply {
                setTextColor(C.COLOR_PRIMARY_2)
            }
            v.findViewById<TextView>(R.id.tvStorage).apply {
                setTextColor(C.COLOR_PRIMARY_2)
            }
            v.findViewById<TextView>(R.id.tvStorageDes).apply {
                setTextColor(C.COLOR_PRIMARY_2)
            }
            v.findViewById<Button>(R.id.buttonStorage).apply {
                setTextColor(C.COLOR_PRIMARY)
                C.setBackground(this)
            }

            v.findViewById<TextView>(R.id.tvContacts).apply {
                setTextColor(C.COLOR_PRIMARY_2)
            }
        }
    }

    fun updatePermissionStatus() = updatePermissionStatus(requireView())

    private fun updatePermissionStatus(v: View) = v.apply {
        if (
            ContextCompat.checkSelfPermission(
                context,
                Manifest.permission.READ_EXTERNAL_STORAGE
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            findViewById<View>(R.id.buttonStorage).isVisible = false
            tickStorage?.isVisible = true
        } else {
            findViewById<View>(R.id.buttonStorage).setOnClickListener {
                requestStoragePermission()
            }
        }

        if (
            ContextCompat.checkSelfPermission(
                context,
                Manifest.permission.READ_CONTACTS
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            findViewById<View>(R.id.buttonContacts).isVisible = false
            tickContacts?.isVisible = true
        } else {
            findViewById<View>(R.id.buttonContacts).setOnClickListener {
                requestContactsPermission()
            }
        }

        if (
            NotificationManagerCompat.getEnabledListenerPackages(context)
                .contains(context.packageName)
        ) {
            findViewById<View>(R.id.buttonNotifications).isVisible = false
            tickNotifications?.isVisible = true
        } else {
            findViewById<View>(R.id.buttonNotifications).setOnClickListener(::requestNotificationsPermission)
        }

        if (SuggestionsManager.checkUsageAccessPermission(context)) {
            findViewById<View>(R.id.buttonUsageAccess).isVisible = false
            tickUsageAccess?.isVisible = true
        } else {
            findViewById<View>(R.id.buttonUsageAccess).setOnClickListener(::requestUsageAccessPermission)
        }
    }

    override fun next(
        activity: IntroActivity,
        isCheckedPolicy: Boolean
    ) {
        if (isFullPermission()) {
            if (isCheckedPolicy) {
//                activity.setFragment(FrmQuickSettings())
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
                activity.showShortError(getString(R.string.pls_read_policy_first))
            }
        } else {
            activity.showShortError(getString(R.string.pls_grant_permission_first))
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
        return (tickStorage?.isVisible == true
                && tickContacts?.isVisible == true
                && tickNotifications?.isVisible == true
                && tickUsageAccess?.isVisible == true
                )
    }
}
