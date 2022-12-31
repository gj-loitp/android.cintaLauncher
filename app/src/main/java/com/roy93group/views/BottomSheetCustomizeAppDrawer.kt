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
import android.widget.*
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.jem.rubberpicker.RubberSeekBar
import com.loitp.core.ext.setSafeOnClickListener
import com.loitp.core.utilities.LAppResource
import com.roy93group.app.C
import com.roy93group.launcher.R
import kotlinx.android.synthetic.main.bottom_sheet_customize_app_drawer.*

class BottomSheetCustomizeAppDrawer(
    private val seekRadiusValue: Int,
    private val seekPeekValue: Int,
    private var gravityValue: Int,
    private var orientationValue: Int,
    private var isCheckedValue: Boolean,
    private var isDisplayAppIcon: Boolean,
    private var isForceColorIcon: Boolean,
    private val isCancelableFragment: Boolean = true,
    private val onDismiss: ((Unit) -> Unit)? = null,
    private val onResetAllValue: ((Unit) -> Unit)? = null,
    private val onSeekRadiusValue: ((Int) -> Unit)?,
    private val onSeekPeekValue: ((Int) -> Unit)?,
    private val onOrientation: ((Int) -> Unit)?,
    private val onGravity: ((Int) -> Unit)?,
    private val onRotate: ((Boolean) -> Unit)?,
    private val onDisplayAppIcon: ((Boolean) -> Unit)?,
    private val onForceColorIcon: ((Boolean) -> Unit)?,
) : BottomSheetDialogFragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        isCancelable = isCancelableFragment
        return inflater.inflate(R.layout.bottom_sheet_customize_app_drawer, container, false)
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
        tvTitle.setTextColor(colorPrimary)
        tvRadius.setTextColor(colorPrimary)
        tvPeekText.setTextColor(colorPrimary)

        seekRadius.apply {
            setNormalTrackColor(colorPrimary)
            setHighlightTrackColor(colorPrimary)
            setHighlightThumbOnTouchColor(colorPrimary)
            setDefaultThumbInsideColor(colorPrimary)
        }
        seekPeek.apply {
            setNormalTrackColor(colorPrimary)
            setHighlightTrackColor(colorPrimary)
            setHighlightThumbOnTouchColor(colorPrimary)
            setDefaultThumbInsideColor(colorPrimary)
        }
        btGravity.apply {
            setTextColor(colorBackground)
            setBackgroundColor(colorPrimary)
        }
        btOrientation.apply {
            setTextColor(colorBackground)
            setBackgroundColor(colorPrimary)
        }
        btReset.apply {
            setTextColor(colorBackground)
            setBackgroundColor(colorPrimary)
            setSafeOnClickListener {
                dismiss()
                onResetAllValue?.invoke(Unit)
            }
        }

        seekRadius.setOnRubberSeekBarChangeListener(object :
            RubberSeekBar.OnRubberSeekBarChangeListener {
            override fun onProgressChanged(
                seekBar: RubberSeekBar,
                value: Int,
                fromUser: Boolean
            ) {
                tvRadius.text = resources.getString(R.string.radius_format, value)
                if (fromUser) {
                    onSeekRadiusValue?.invoke(value)
                }
            }

            override fun onStartTrackingTouch(seekBar: RubberSeekBar) {}
            override fun onStopTrackingTouch(seekBar: RubberSeekBar) {}
        })
        seekPeek.setOnRubberSeekBarChangeListener(object :
            RubberSeekBar.OnRubberSeekBarChangeListener {
            override fun onProgressChanged(
                seekBar: RubberSeekBar,
                value: Int,
                fromUser: Boolean
            ) {
                tvPeekText.text = resources.getString(R.string.peek_format, value)
                if (fromUser) {
                    onSeekPeekValue?.invoke(value)
                }
            }

            override fun onStartTrackingTouch(seekBar: RubberSeekBar) {}
            override fun onStopTrackingTouch(seekBar: RubberSeekBar) {}
        })

        tvRadius.text = resources.getString(R.string.radius_format, seekRadiusValue)
        tvPeekText.text = resources.getString(R.string.peek_format, seekPeekValue)
        seekRadius.setCurrentValue(seekRadiusValue)
        seekPeek.setCurrentValue(seekPeekValue)

        updateUIGravity()
        btGravity.setSafeOnClickListener {
            val title = LAppResource.getString(R.string.gravity)
            val value0 = LAppResource.getString(R.string.start)
            val value1 = LAppResource.getString(R.string.end)
            C.launchSelector(
                activity = activity,
                isCancelableFragment = true,
                title = title,
                des = LAppResource.getString(R.string.pick_your_choice),
                value0 = value0,
                value1 = value1,
                firstIndexCheck = gravityValue,
                onConfirm = { index ->
                    gravityValue = index
                    updateUIGravity()
                    onGravity?.invoke(gravityValue)
                },
                onDismiss = {}
            )
        }

        updateUIOrientation()
        btOrientation.setSafeOnClickListener {
            val title = LAppResource.getString(R.string.orientation)
            val value0 = LAppResource.getString(R.string.vertical)
            val value1 = LAppResource.getString(R.string.horizontal)
            C.launchSelector(
                activity = activity,
                isCancelableFragment = true,
                title = title,
                des = LAppResource.getString(R.string.pick_your_choice),
                value0 = value0,
                value1 = value1,
                firstIndexCheck = orientationValue,
                onConfirm = { index ->
                    orientationValue = index
                    updateUIOrientation()
                    onOrientation?.invoke(orientationValue)
                },
                onDismiss = {}
            )
        }

        cbRotate.apply {
            setTextColor(colorPrimary)
            C.setButtonTintList(this, colorPrimary)
            isChecked = isCheckedValue
            setOnCheckedChangeListener { _, isChecked ->
                onRotate?.invoke(isChecked)
            }
        }

        cbDisplayAppIcon.apply {
            setTextColor(colorPrimary)
            C.setButtonTintList(this, colorPrimary)
            isChecked = isDisplayAppIcon
            setOnCheckedChangeListener { _, isChecked ->
                onDisplayAppIcon?.invoke(isChecked)
            }
        }

        cbForceColorIcon.apply {
            setTextColor(colorPrimary)
            C.setButtonTintList(this, colorPrimary)
            isChecked = isForceColorIcon
            setOnCheckedChangeListener { _, isChecked ->
                onForceColorIcon?.invoke(isChecked)
            }
        }
    }

    @SuppressLint("SetTextI18n")
    private fun updateUIGravity() {
        val title = LAppResource.getString(R.string.gravity)
        val value0 = LAppResource.getString(R.string.start)
        val value1 = LAppResource.getString(R.string.end)
        if (gravityValue == 0) {
            btGravity.text = "$title: $value0"
        } else {
            btGravity.text = "$title: $value1"
        }
    }

    @SuppressLint("SetTextI18n")
    private fun updateUIOrientation() {
        val title = LAppResource.getString(R.string.orientation)
        val value0 = LAppResource.getString(R.string.vertical)
        val value1 = LAppResource.getString(R.string.horizontal)
        if (orientationValue == 0) {
            btOrientation.text = "$title: $value0"
        } else {
            btOrientation.text = "$title: $value1"
        }
    }

}
