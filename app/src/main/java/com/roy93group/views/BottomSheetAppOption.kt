package com.roy93group.views

/**
 * Created by Loitp on 06,December,2022
 * Galaxy One company,
 * Vietnam
 * +840766040293
 * freuss47@gmail.com
 */
import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.loitpcore.core.ext.setSafeOnClickListener
import com.loitpcore.core.utilities.LSocialUtil
import com.roy93group.app.C
import com.roy93group.launcher.R
import com.roy93group.launcher.data.items.App
import com.roy93group.launcher.data.items.LauncherItem

class BottomSheetAppOption(
    private val item: LauncherItem,
    private val isCancelableFragment: Boolean = true,
    private val onDismiss: ((Unit) -> Unit)? = null
) : BottomSheetDialogFragment() {
    private var tvTitle: TextView? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
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

        setupViews(view)
    }

    override fun onDismiss(dialog: DialogInterface) {
        super.onDismiss(dialog)
        onDismiss?.invoke(Unit)
    }

    private fun setupViews(view: View) {
        view.findViewById<ViewGroup>(R.id.llRoot).setBackgroundColor(C.COLOR_0)
        tvTitle = view.findViewById(R.id.tvTitle)

        val btAppSetting = view.findViewById<Button>(R.id.btAppSetting)
        val btPlayStore = view.findViewById<Button>(R.id.btPlayStore)
        val btUninstall = view.findViewById<Button>(R.id.btUninstall)

        btAppSetting.setSafeOnClickListener {
            (item as? App)?.packageName?.let { packageName ->
                dismiss()
                C.launchSystemSetting(context = requireContext(), packageName = packageName)
            }
        }
        btPlayStore.setSafeOnClickListener {
            (item as? App)?.packageName?.let { packageName ->
                dismiss()
                LSocialUtil.rateApp(activity = requireActivity(), packageName = packageName)
            }
        }
        btUninstall.setSafeOnClickListener {
            (item as? App)?.packageName?.let { packageName ->
                C.uninstallApp(activity = requireActivity(), packageName = packageName)
                dismiss()
            }
        }
    }

}
