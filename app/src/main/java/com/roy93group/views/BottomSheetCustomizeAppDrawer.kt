package com.roy93group.views

/**
 * Created by Loitp on 06,December,2022
 * Galaxy One company,
 * Vietnam
 * +840766040293
 * freuss47@gmail.com
 */
import android.content.Context
import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.annotation.LayoutRes
import androidx.appcompat.widget.AppCompatCheckBox
import androidx.appcompat.widget.AppCompatSpinner
import cdflynn.android.library.turn.TurnLayoutManager
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.roy93group.app.C
import com.roy93group.launcher.R

class BottomSheetCustomizeAppDrawer(
    private val seekRadiusValue: Int,
    private val seekPeekValue: Int,
    private val isCancelableFragment: Boolean = true,
    private val onDismiss: ((Unit) -> Unit)? = null,
    private val onSeekRadiusValue: ((Int) -> Unit)?,
    private val onSeekPeekValue: ((Int) -> Unit)?,
    private val onOrientation: ((Int) -> Unit)?,
    private val onGravity: ((Int) -> Unit)?,
    private val onRotate: ((Boolean) -> Unit)?,
) : BottomSheetDialogFragment() {

    private var tvRadius: TextView? = null
    private var tvPeekText: TextView? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        isCancelable = isCancelableFragment
        return inflater.inflate(R.layout.bottom_sheet_customize_app_drawer, container, false)
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

        tvRadius = view.findViewById(R.id.tvRadius)
        tvPeekText = view.findViewById(R.id.tvPeekText)
        val seekRadius = view.findViewById<SeekBar>(R.id.seekRadius)
        val seekPeek = view.findViewById<SeekBar>(R.id.seekPeek)
        val sGravity = view.findViewById<AppCompatSpinner>(R.id.sGravity)
        val sOrientation = view.findViewById<AppCompatSpinner>(R.id.sOrientation)
        val cbRotate = view.findViewById<AppCompatCheckBox>(R.id.cbRotate)

        seekRadius.setOnSeekBarChangeListener(radiusListener)
        seekPeek.setOnSeekBarChangeListener(peekListener)

        seekRadius.progress = seekRadiusValue
        seekPeek.progress = seekPeekValue

        sGravity.onItemSelectedListener = gravityOptionsClickListener
        sOrientation.onItemSelectedListener = orientationOptionsClickListener

        sGravity.adapter = GravityAdapter(requireContext(), R.layout.view_spinner_item_tlm)
        sOrientation.adapter = OrientationAdapter(requireContext(), R.layout.view_spinner_item_tlm)
        cbRotate.setOnCheckedChangeListener(rotateListener)
    }

    private val radiusListener: SeekBar.OnSeekBarChangeListener = object :
        SeekBar.OnSeekBarChangeListener {
        override fun onProgressChanged(seekBar: SeekBar, progress: Int, fromUser: Boolean) {
            tvRadius?.text = resources.getString(R.string.radius_format, progress)
            if (fromUser) {
                onSeekRadiusValue?.invoke(progress)
            }
        }

        override fun onStartTrackingTouch(seekBar: SeekBar) {
            // do nothing
        }

        override fun onStopTrackingTouch(seekBar: SeekBar) {
            // do nothing
        }
    }

    private val peekListener: SeekBar.OnSeekBarChangeListener = object :
        SeekBar.OnSeekBarChangeListener {
        override fun onProgressChanged(seekBar: SeekBar, progress: Int, fromUser: Boolean) {
            tvPeekText?.text = resources.getString(R.string.peek_format, progress)
            if (fromUser) {
                onSeekPeekValue?.invoke(progress)
            }
        }

        override fun onStartTrackingTouch(seekBar: SeekBar) {
            // do nothing
        }

        override fun onStopTrackingTouch(seekBar: SeekBar) {
            // do nothing
        }
    }

    private val orientationOptionsClickListener: AdapterView.OnItemSelectedListener =
        object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?, view: View, position: Int, id: Long
            ) {
                when (position) {
                    0 -> {
                        onOrientation?.invoke(TurnLayoutManager.VERTICAL)
                    }
                    1 -> {
                        onOrientation?.invoke(TurnLayoutManager.HORIZONTAL)
                    }
                    else -> {}
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }

    private val gravityOptionsClickListener: AdapterView.OnItemSelectedListener =
        object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?, view: View, position: Int, id: Long
            ) {
                when (position) {
                    0 -> {
                        onGravity?.invoke(TurnLayoutManager.Gravity.START)
                    }
                    1 -> {
                        onGravity?.invoke(TurnLayoutManager.Gravity.END)
                    }
                    else -> {}
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }

    private val rotateListener = CompoundButton.OnCheckedChangeListener { _, isChecked ->
        onRotate?.invoke(isChecked)
    }

    private class OrientationAdapter(
        context: Context, @LayoutRes resource: Int
    ) : ArrayAdapter<String?>(context, resource, arrayOf("Vertical", "Horizontal"))

    private class GravityAdapter(
        context: Context, @LayoutRes resource: Int
    ) : ArrayAdapter<String?>(context, resource, arrayOf("Start", "End"))
}
