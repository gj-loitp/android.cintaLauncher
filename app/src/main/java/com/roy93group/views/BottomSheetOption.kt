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
import android.widget.RadioButton
import android.widget.TextView
import androidx.appcompat.widget.LinearLayoutCompat
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.roy93group.app.C
import com.roy93group.launcher.R

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
    private var llRoot: LinearLayoutCompat? = null
    private var tvTitle: TextView? = null
    private var tvDes: TextView? = null
    private var rb0: RadioButton? = null
    private var rb1: RadioButton? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        isCancelable = isCancelableFragment
        return inflater.inflate(R.layout.bottom_sheet_option, container, false)
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
        llRoot = view.findViewById<LinearLayoutCompat>(R.id.llRoot).apply {
            setBackgroundColor(C.COLOR_PRIMARY_2)
        }
        tvTitle = view.findViewById<TextView>(R.id.tvTitle).apply {
            text = title
        }
        tvDes = view.findViewById<TextView>(R.id.tvDes).apply {
            text = des
        }

        rb0 = view.findViewById<RadioButton>(R.id.rb0).apply {
            text = value0
        }
        rb1 = view.findViewById<RadioButton>(R.id.rb1).apply {
            text = value1
        }

        when (firstIndexCheck) {
            0 -> rb0?.isChecked = true
            1 -> rb1?.isChecked = true
            else -> {}
        }

        rb0?.setOnCheckedChangeListener { _, b ->
            if (b) {
                onConfirm?.invoke(0)
                dismiss()
            }
        }
        rb1?.setOnCheckedChangeListener { _, b ->
            if (b) {
                onConfirm?.invoke(1)
                dismiss()
            }
        }
    }
}
