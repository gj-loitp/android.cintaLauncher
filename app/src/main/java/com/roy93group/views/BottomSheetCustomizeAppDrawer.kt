package com.roy93group.views

/**
 * Created by Loitp on 06,December,2022
 * Galaxy One company,
 * Vietnam
 * +840766040293
 * freuss47@gmail.com
 */
import android.annotation.SuppressLint
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
import com.loitpcore.core.ext.setSafeOnClickListener
import com.loitpcore.core.utilities.LAppResource
import com.roy93group.app.C
import com.roy93group.launcher.R

//TODO customize seekbar
//TODO customize checkbox
//TODO keep value o db
class BottomSheetCustomizeAppDrawer(
    private val seekRadiusValue: Int,
    private val seekPeekValue: Int,
    private var gravityValue: Int,
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
    private var btGravity: Button? = null

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
        btGravity = view.findViewById(R.id.btGravity)
        val sOrientation = view.findViewById<AppCompatSpinner>(R.id.sOrientation)
        val cbRotate = view.findViewById<AppCompatCheckBox>(R.id.cbRotate)

        seekRadius.setOnSeekBarChangeListener(radiusListener)
        seekPeek.setOnSeekBarChangeListener(peekListener)

        tvRadius?.text = resources.getString(R.string.radius_format, seekRadiusValue)
        tvPeekText?.text = resources.getString(R.string.peek_format, seekPeekValue)
        seekRadius.progress = seekRadiusValue
        seekPeek.progress = seekPeekValue

        updateUIGravity()
        btGravity?.setSafeOnClickListener {
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

        sOrientation.onItemSelectedListener = orientationOptionsClickListener

        sOrientation.adapter = OrientationAdapter(requireContext(), R.layout.view_spinner_item_tlm)
        cbRotate.setOnCheckedChangeListener(rotateListener)
    }

    @SuppressLint("SetTextI18n")
    private fun updateUIGravity() {
        val title = LAppResource.getString(R.string.gravity)
        val value0 = LAppResource.getString(R.string.start)
        val value1 = LAppResource.getString(R.string.end)
        if (gravityValue == 0) {
            btGravity?.text = "$title: $value0"
        } else {
            btGravity?.text = "$title: $value1"
        }
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


    private val rotateListener = CompoundButton.OnCheckedChangeListener { _, isChecked ->
        onRotate?.invoke(isChecked)
    }

    private class OrientationAdapter(
        context: Context, @LayoutRes resource: Int
    ) : ArrayAdapter<String?>(context, resource, arrayOf("Vertical", "Horizontal"))

}
