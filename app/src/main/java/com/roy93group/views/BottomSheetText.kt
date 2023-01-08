package com.roy93group.views

/**
 * Created by Loitp on 06,December,2022
 * Galaxy One company,
 * Vietnam
 * +840766040293
 * freuss47@gmail.com
 */
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.loitp.core.ext.setCornerCardView
import com.roy93group.ext.getColorBackground
import com.roy93group.ext.getColorPrimary
import com.roy93group.ext.setCornerCardViewLauncher
import com.roy93group.launcher.R
import kotlinx.android.synthetic.main.bottom_sheet_text.*

class BottomSheetText(
    private val isCancelableFragment: Boolean = true,
    private val title: String,
    private val content: String,
) : BottomSheetDialogFragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        isCancelable = isCancelableFragment
        return inflater.inflate(R.layout.bottom_sheet_text, container, false)
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

    private fun setupViews() {
        val colorPrimary = getColorPrimary()
        val colorBackground = getColorBackground()

        llRoot.apply {
            setCardBackgroundColor(colorBackground)
            this.setCornerCardViewLauncher()
        }
        ivSlider.setColorFilter(colorPrimary)
        tvTitle.apply {
            setTextColor(colorPrimary)
            text = title
        }
        tvContent.apply {
            setTextColor(colorPrimary)
            text = content
        }

    }
}
