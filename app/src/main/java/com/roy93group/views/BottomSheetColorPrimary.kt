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
import com.loitp.core.ext.vibrate
import com.loitp.picker.shiftColor.OnColorChangedListener
import com.roy93group.app.C
import com.roy93group.launcher.R
import com.roy93group.launcher.ui.LauncherActivity
import kotlinx.android.synthetic.main.bottom_sheet_color_primary.*

class BottomSheetColorPrimary(
    private val isCancelableFragment: Boolean = true,
    private val title: String,
    private val des: String,
    private val warning: String,
    private val onDismiss: ((Int) -> Unit)? = null
) : BottomSheetDialogFragment() {
    private var newColorPrimary = C.getColorPrimary()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        isCancelable = isCancelableFragment
        return inflater.inflate(R.layout.bottom_sheet_color_primary, container, false)
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
        if (newColorPrimary != C.getColorPrimary()) {
            onDismiss?.invoke(newColorPrimary)
        }
    }

    private fun setupViews() {
        val colorPrimary = C.getColorPrimary()
        val colorBackground = C.getColorBackground()

        llRoot.apply {
            setCardBackgroundColor(colorBackground)
            C.setCornerCardView(cardView = this)
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
        tvWarning.apply {
            text = warning
            setTextColor(colorPrimary)
        }

        colorPicker.apply {
            colors = C.colors
            setBackgroundColor(colorPrimary)
            setSelectedColor(colorPrimary)
            setOnColorChangedListener(object : OnColorChangedListener {
                override fun onColorChanged(c: Int) {
                    context.vibrate(milliseconds = 10L)
                    if (c == colorBackground) {
                        (activity as? LauncherActivity)?.showShortError(getString(R.string.err_same_color))
                    } else {
                        newColorPrimary = c
                        colorPicker.setBackgroundColor(newColorPrimary)
                        ivSlider.setColorFilter(newColorPrimary)
                        tvTitle.setTextColor(newColorPrimary)
                        tvDes.setTextColor(newColorPrimary)
                        tvWarning.setTextColor(newColorPrimary)
                    }
                }
            })
        }
    }
}
