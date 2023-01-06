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
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.loitp.core.ext.setButtonTintList
import com.loitp.core.ext.vibrate
import com.roy93group.app.C
import com.roy93group.launcher.R
import kotlinx.android.synthetic.main.bottom_sheet_option.*


class BottomSheetOption(
    private val isCancelableFragment: Boolean = true,
    private val title: String,
    private val des: String,
    private val value0: String,
    private val value1: String,
    private val firstIndexCheck: Int = 0,
    private val onConfirm: ((Int) -> Unit)? = null,
    private val onDismiss: ((Unit) -> Unit)? = null
) : BottomSheetDialogFragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        isCancelable = isCancelableFragment
        return inflater.inflate(R.layout.bottom_sheet_option, container, false)
    }

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?
    ) {
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

    private fun setupViews() {
        val colorPrimary = C.getColorPrimary()
        val colorBackground = C.getColorBackground()

        llRoot.apply {
            setCardBackgroundColor(colorBackground)
            C.setCornerCardView(activity = requireActivity(), cardView = this)
        }
        ivSlider.setColorFilter(colorPrimary)
        tvTitle.apply {
            text = title
            setTextColor(colorPrimary)
        }
        tvDes.apply {
            text = des
            setTextColor(colorPrimary)
        }

        rb0.apply {
            text = value0
            setTextColor(colorPrimary)
            this.setButtonTintList(colorPrimary)
        }
        rb1.apply {
            text = value1
            setTextColor(colorPrimary)
            this.setButtonTintList(colorPrimary)
        }

        when (firstIndexCheck) {
            0 -> rb0.isChecked = true
            1 -> rb1.isChecked = true
            else -> {}
        }

        rb0.setOnCheckedChangeListener { _, b ->
            if (b) {
                context?.vibrate(milliseconds = 500L)
                onConfirm?.invoke(0)
                dismiss()
            }
        }
        rb1.setOnCheckedChangeListener { _, b ->
            if (b) {
                context?.vibrate(milliseconds = 500L)
                onConfirm?.invoke(1)
                dismiss()
            }
        }
    }
}
