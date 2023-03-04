package com.roy93group.views

/**
 * Created by Loitp on 06,December,2022
 * Galaxy One company,
 * Vietnam
 * +840766040293
 * freuss47@gmail.com
 */
import android.annotation.SuppressLint
import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.biometric.BiometricManager
import androidx.biometric.BiometricPrompt
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.loitp.core.ext.*
import com.roy93group.ext.*
import com.roy93group.launcher.R
import com.roy93group.launcher.data.items.App
import com.roy93group.launcher.data.items.LauncherItem
import kotlinx.android.synthetic.main.bottom_sheet_app_option.*

class BottomSheetAppOption(
    private val item: LauncherItem,
    private val isCancelableFragment: Boolean = true,
    private val onDismiss: ((Unit) -> Unit)? = null
) : BottomSheetDialogFragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        isCancelable = isCancelableFragment
        return inflater.inflate(R.layout.bottom_sheet_app_option, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // https://stackoverflow.com/questions/37104960/bottomsheetdialog-with-transparent-background
        dialog?.apply {
            setOnShowListener {
                val bottomSheet = findViewById<View?>(R.id.design_bottom_sheet)
                bottomSheet?.setBackgroundResource(android.R.color.transparent)
            }
        }

        setupViews()
    }

    override fun onDismiss(dialog: DialogInterface) {
        super.onDismiss(dialog)
        onDismiss?.invoke(Unit)
    }

    @SuppressLint("SetTextI18n")
    private fun setupViews() {
        val colorPrimary = getColorPrimary()
        val colorBackground = getColorBackground()

        llRoot.apply {
            setCardBackgroundColor(colorBackground)
            this.setCornerCardViewLauncher()
        }
        ivSlider.setColorFilter(colorPrimary)
        tvTitle.setTextColor(colorPrimary)
        tvInfo.apply {
            setTextColor(colorPrimary)
            this.setDrawableTint(colorPrimary)
            (item as? App)?.let { app ->
                this.text = "Label: ${app.label}\nPackage name: ${app.packageName}"
                setSafeOnClickListener {
                    context.setClipboard(app.packageName)
                }
            }
        }
        btAppSetting.setTextColor(colorPrimary)
        btPlayStore.setTextColor(colorPrimary)
        btUninstall.setTextColor(colorPrimary)
        btLockUnlockApp.setTextColor(colorPrimary)

        btAppSetting.setSafeOnClickListener {
            (item as? App)?.packageName?.let { packageName ->
                dismiss()
                context?.launchSystemSetting(packageName = packageName)
            }
        }
        btPlayStore.setSafeOnClickListener {
            (item as? App)?.packageName?.let { packageName ->
                dismiss()
                activity?.rateApp(packageName = packageName)
            }
        }
        btUninstall.setSafeOnClickListener {
            (item as? App)?.packageName?.let { packageName ->
                activity?.uninstallApp(packageName = packageName)
                dismiss()
            }
        }

        btLockUnlockApp.apply {
            val biometricManager = BiometricManager.from(context)
            this.isVisible =
                biometricManager.canAuthenticate() == BiometricManager.BIOMETRIC_SUCCESS

            (item as? App)?.let { app ->
                app.packageName.let { packageName ->
                    val isAppLock = context.isAppLock(packageName)
                    if (isAppLock) {
                        this.text = context.getString(R.string.unlock_app)
                    } else {
                        this.text = context.getString(R.string.lock_app)
                    }
                    btLockUnlockApp.setSafeOnClickListener {
                        toggleLockApp(
                            appName = app.label, packageName = packageName, isAppLock = isAppLock
                        )
                    }
                }
            }
        }
    }

    private fun toggleLockApp(appName: String, packageName: String, isAppLock: Boolean) {
        val description = if (isAppLock) {
            "Unlock $appName?"
        } else {
            "Lock $appName?"
        }

        val promptInfo = BiometricPrompt.PromptInfo.Builder()
            .setTitle(requireContext().getString(R.string.verify_your_identity))
            .setDescription(description)
            .setNegativeButtonText(requireContext().getString(R.string.cancel)).build()
        instanceOfBiometricPrompt(packageName = packageName, isAppLock = isAppLock).authenticate(
            promptInfo
        )

    }

    private fun instanceOfBiometricPrompt(
        packageName: String, isAppLock: Boolean
    ): BiometricPrompt {
        val executor = ContextCompat.getMainExecutor(requireContext())
        val callback = object : BiometricPrompt.AuthenticationCallback() {
            override fun onAuthenticationError(errorCode: Int, errString: CharSequence) {
                super.onAuthenticationError(errorCode, errString)
            }

            override fun onAuthenticationFailed() {
                super.onAuthenticationFailed()
            }

            override fun onAuthenticationSucceeded(result: BiometricPrompt.AuthenticationResult) {
                super.onAuthenticationSucceeded(result)
                if (isAppLock) {
                    context?.setAppLock(packageName, false)
                } else {
                    context?.setAppLock(packageName, true)
                }
                dismiss()
            }
        }
        return BiometricPrompt(requireActivity(), executor, callback)
    }
}
