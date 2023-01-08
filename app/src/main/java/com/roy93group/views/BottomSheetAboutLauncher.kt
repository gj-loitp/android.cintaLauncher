package com.roy93group.views

/**
 * Created by Loitp on 06,December,2022
 * Galaxy One company,
 * Vietnam
 * +840766040293
 * freuss47@gmail.com
 */
import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.loitp.core.common.Constants
import com.loitp.core.ext.*
import com.loitp.core.helper.adHelper.AdHelperActivity
import com.roy93group.ext.*
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
        val colorPrimary = getColorPrimary()
        val colorBackground = getColorBackground()

        llRoot.apply {
            setCardBackgroundColor(colorBackground)
            this.setCornerCardViewLauncher()
        }
        ivSlider.setColorFilter(colorPrimary)
        tvTitle.setTextColor(colorPrimary)

        layoutVersion.apply {
            v.setBackgroundColor(colorBackground)
            tv1.apply {
                setTextColor(colorPrimary)
                text = "Version: ${BuildConfig.VERSION_NAME}"
            }
            tv2.apply {
                setTextColor(colorPrimary)
                text = BuildConfig.BUILD_TYPE
            }
            setSafeOnClickListener {
                activity?.rateApp()
            }
        }
        layoutChangelog.apply {
            v.setBackgroundColor(colorPrimary)
            tv1.apply {
                setTextColor(colorPrimary)
                text = getString(R.string.changelog)
            }
            tv2.apply {
                setTextColor(colorPrimary)
                text = getString(R.string.changelog_des)
            }
            setSafeOnClickListener {
                activity?.launchSheetText(
                    isCancelableFragment = true,
                    title = getString(R.string.changelog),
                    content = getString(R.string.changelog_detail)
                )
            }
        }
        layoutHallOfFame.apply {
            v.setBackgroundColor(colorPrimary)
            tv1.apply {
                setTextColor(colorPrimary)
                text = getString(R.string.hall_of_fame)
            }
            tv2.apply {
                setTextColor(colorPrimary)
                text = getString(R.string.hall_of_fame_des)
            }
            setSafeOnClickListener {
                activity?.launchSheetText(
                    isCancelableFragment = true,
                    title = getString(R.string.hall_of_fame),
                    content = getString(R.string.hall_of_fame_detail)
                )
            }
        }
        layoutContactTheDeveloper.apply {
            v.setBackgroundColor(colorPrimary)
            tv1.apply {
                setTextColor(colorPrimary)
                text = getString(R.string.contact_the_dev)
            }
            tv2.apply {
                setTextColor(colorPrimary)
                text = getString(R.string.contact_the_dev_des)
            }
            setSafeOnClickListener {
                context?.openUrlInBrowser(
                    url = "https://www.facebook.com/loitp93/"
                )
            }
        }
        layoutWhyDoISeeAd.apply {
            v.setBackgroundColor(colorPrimary)
            tv1.apply {
                setTextColor(colorPrimary)
                text = getString(R.string.why_u_see_ad_en)
            }
            tv2.apply {
                setTextColor(colorPrimary)
                text = getString(R.string.ad_des)
            }
            setSafeOnClickListener {
                val intent = Intent(context, AdHelperActivity::class.java).apply {
                    putExtra(Constants.AD_HELPER_IS_ENGLISH_LANGUAGE, true)
                    putExtra(Constants.AD_HELPER_COLOR_PRIMARY, colorPrimary)
                    putExtra(Constants.AD_HELPER_COLOR_BACKGROUND, colorBackground)
                    putExtra(Constants.AD_HELPER_COLOR_STATUS_BAR, colorBackground)
                    putExtra(Constants.AD_HELPER_IS_LIGHT_ICON_STATUS_BAR, isLightIconStatusBar())
                }
                startActivity(intent)
                context.tranIn()
            }
        }
    }
}
