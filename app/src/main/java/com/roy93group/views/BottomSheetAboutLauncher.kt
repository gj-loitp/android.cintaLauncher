package com.roy93group.views

/**
 * Created by Loitp on 06,December,2022
 * Galaxy One company,
 * Vietnam
 * +840766040293
 * freuss47@gmail.com
 */
import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.loitp.core.ext.setSafeOnClickListener
import com.loitp.core.utilities.LSocialUtil
import com.roy93group.app.C
import com.roy93group.launcher.BuildConfig
import com.roy93group.launcher.R
import kotlinx.android.synthetic.main.bottom_sheet_about_launcher.*
import kotlinx.android.synthetic.main.view_item.view.*

class BottomSheetAboutLauncher(
    private val isCancelableFragment: Boolean = true,
) : BottomSheetDialogFragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        isCancelable = isCancelableFragment
        return inflater.inflate(R.layout.bottom_sheet_about_launcher, container, false)
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

    @SuppressLint("SetTextI18n")
    private fun setupViews() {
        val colorPrimary = C.getColorPrimary()
        val colorBackground = C.getColorBackground()

        llRoot.apply {
            setCardBackgroundColor(colorBackground)
            C.setCornerCardView(activity = requireActivity(), cardView = this)
        }
        ivSlider.setColorFilter(colorPrimary)
        tvTitle.setTextColor(colorPrimary)

        layoutVersion.apply {
            tv1.apply {
                setTextColor(colorPrimary)
                text = "Version: ${BuildConfig.VERSION_NAME}"
            }
            tv2.apply {
                setTextColor(colorPrimary)
                text = BuildConfig.BUILD_TYPE
            }
            setSafeOnClickListener {
                LSocialUtil.rateApp(requireActivity())
            }
        }
        layoutChangelog.apply {
            tv1.apply {
                setTextColor(colorPrimary)
                text = getString(R.string.changelog)
            }
            tv2.apply {
                setTextColor(colorPrimary)
                text = getString(R.string.changelog_des)
            }
            setSafeOnClickListener {
                //TODO
            }
        }
        layoutHallOfFame.apply {
            tv1.apply {
                setTextColor(colorPrimary)
                text = getString(R.string.hall_of_fame)
            }
            tv2.apply {
                setTextColor(colorPrimary)
                text = getString(R.string.hall_of_fame_des)
            }
            setSafeOnClickListener {
                //TODO
            }
        }
        layoutContactTheDeveloper.apply {
            tv1.apply {
                setTextColor(colorPrimary)
                text = getString(R.string.hall_of_fame)
            }
            tv2.apply {
                setTextColor(colorPrimary)
                text = getString(R.string.contact_the_dev_des)
            }
            setSafeOnClickListener {
                LSocialUtil.openUrlInBrowser(
                    context = requireContext(),
                    url = "https://www.facebook.com/loitp93/"
                )
            }
        }
    }
}
