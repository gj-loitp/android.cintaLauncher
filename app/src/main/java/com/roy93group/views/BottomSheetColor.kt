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
import android.widget.TextView
import androidx.appcompat.widget.LinearLayoutCompat
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.loitpcore.picker.shiftColorPicker.LineColorPicker
import com.loitpcore.picker.shiftColorPicker.OnColorChangedListener
import com.roy93group.app.C
import com.roy93group.launcher.R

class BottomSheetColor(
    private val isCancelableFragment: Boolean = true,
    private val title: String,
    private val des: String,
    private val warning: String,
    private val onDismiss: ((Int) -> Unit)? = null
) : BottomSheetDialogFragment() {
    private var llRoot: LinearLayoutCompat? = null
    private var tvTitle: TextView? = null
    private var tvDes: TextView? = null
    private var tvWarning: TextView? = null
    private var newColor = C.COLOR_0

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        isCancelable = isCancelableFragment
        return inflater.inflate(R.layout.bottom_sheet_color, container, false)
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
        if (newColor != C.COLOR_0) {
            onDismiss?.invoke(newColor)
        }
    }

    private fun setupViews(view: View) {
        llRoot = view.findViewById(R.id.llRoot)
        tvTitle = view.findViewById(R.id.tvTitle)
        tvDes = view.findViewById(R.id.tvDes)
        tvWarning = view.findViewById(R.id.tvWarning)
        updateUI()

        tvTitle?.text = title
        tvDes?.text = des
        tvWarning?.text = warning

        view.findViewById<LineColorPicker>(R.id.colorPicker).apply {
            colors = C.colors
            setSelectedColor(C.COLOR_0)
            setOnColorChangedListener(object : OnColorChangedListener {
                override fun onColorChanged(c: Int) {
                    C.vibrate(milliseconds = 10)

                    newColor = c
                    updateUI()
                }
            })
        }
    }

    private fun updateUI() {
        llRoot?.setBackgroundColor(C.COLOR_0)
    }
}
