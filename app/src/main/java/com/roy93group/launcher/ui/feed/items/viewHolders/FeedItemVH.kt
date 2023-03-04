package com.roy93group.launcher.ui.feed.items.viewHolders

import android.annotation.SuppressLint
import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.biometric.BiometricPrompt
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.roy93group.ext.COLOR_15
import com.roy93group.ext.isAppLock
import com.roy93group.launcher.R
import com.roy93group.launcher.data.feed.items.FeedItem
import com.roy93group.launcher.data.feed.items.formatTimeAgo
import com.roy93group.launcher.ui.feed.ActionsAdapter
import com.roy93group.launcher.ui.view.SwipeLayout
import com.roy93group.launcher.ui.view.recycler.DividerItemDecorator
import io.posidon.android.conveniencelib.units.dp
import io.posidon.android.conveniencelib.units.toPixels
import kotlinx.android.synthetic.main.view_feed_item_actions.view.*
import kotlinx.android.synthetic.main.view_feed_item_plain.view.*
import java.time.Instant

/**
 * Updated by Loitp on 2022.12.16
 * Galaxy One company,
 * Vietnam
 * +840766040293
 * freuss47@gmail.com
 */
open class FeedItemVH(
    activity: AppCompatActivity,
    itemView: View,
) : FeedViewHolder(activity, SwipeLayout(itemView)) {
    private val swipeLayout = this.itemView as SwipeLayout

    private val separatorDrawable = GradientDrawable().apply {
        shape = GradientDrawable.RECTANGLE
        setSize(
            /* width = */ 1.dp.toPixels(itemView),
            /* height = */ 0
        )
    }

    @SuppressLint("ClickableViewAccessibility")
    @Suppress("unused")
    private val rvActions: RecyclerView = itemView.rvActions.apply {
        layoutManager = LinearLayoutManager(
            /* context = */ context,
            /* orientation = */RecyclerView.HORIZONTAL,
            /* reverseLayout = */false
        )
        addItemDecoration(
            DividerItemDecorator(
                context = itemView.context,
                orientation = DividerItemDecoration.HORIZONTAL,
                divider = separatorDrawable
            )
        )
        setOnTouchListener { v, _ ->
            v.parent.requestDisallowInterceptTouchEvent(true)
            false
        }
    }

    override fun onBind(
        feedItem: FeedItem,
        isDisplayAppIcon: Boolean,
        isForceColorIcon: Boolean,
    ) {
        swipeLayout.reset()

        itemView.vLine.apply {
            setBackgroundColor(colorPrimary)
        }

        itemView.title.apply {
            text = feedItem.title
            isVisible = feedItem.title.isNotEmpty()
            setTextColor(colorPrimary)
        }

        itemView.description.apply {
            setTextColor(colorPrimary)
            applyIfNotNull(view = this, value = feedItem.description, block = TextView::setText)
            isVisible = !feedItem.description.isNullOrEmpty()
        }

        itemView.icon.apply {
            if (isDisplayAppIcon) {
                applyIfNotNull(
                    view = this, value = feedItem.sourceIcon, block = ImageView::setImageDrawable
                )
                if (isForceColorIcon) {
                    setColorFilter(colorPrimary)
                } else {
                    if (colorBackground == COLOR_15) {
                        setColorFilter(colorPrimary)
                    } else {
                        setColorFilter(Color.TRANSPARENT)
                    }
                }
            } else {
                isVisible = false
            }
        }

        itemView.source.apply {
            setTextColor(colorPrimary)
            applyIfNotNull(view = this, value = feedItem.source, block = TextView::setText)
        }

        itemView.setOnClickListener {
            handleOnClick(view = it, feedItem = feedItem)
        }

        itemView.cvActionsContainer.apply {
            setCardBackgroundColor(colorPrimary)
            if (feedItem.actions.isEmpty()) {
                isVisible = false
            } else {
                isVisible = true
                rvActions.adapter = ActionsAdapter(feedItem.actions)
            }
        }

        itemView.tvTime.apply {
            setTextColor(colorPrimary)
            if (feedItem.instant == Instant.MAX) {
                isVisible = false
            } else {
                isVisible = true
                text = feedItem.formatTimeAgo(itemView.resources)
            }
        }

        swipeLayout.onSwipeAway = feedItem::onDismiss
        swipeLayout.isSwipeAble = feedItem.isDismissible

        swipeLayout.setSwipeColor(colorPrimary)
        swipeLayout.setIconColor(colorBackground)
    }

    private fun handleOnClick(
        view: View,
        feedItem: FeedItem,
    ) {
        val packageName = feedItem.meta?.sourcePackageName
        if (packageName.isNullOrEmpty()) {
            return
        }
        val appName = feedItem.source
        val isAppLock = activity.isAppLock(packageName)
        if (isAppLock) {
            val promptInfo = BiometricPrompt.PromptInfo.Builder()
                .setTitle(activity.getString(R.string.verify_your_identity))
                .setDescription("Unlock $appName?")
                .setNegativeButtonText(activity.getString(R.string.cancel)).build()
            instanceOfBiometricPrompt {
                feedItem.onTap(view)
            }.authenticate(
                promptInfo
            )
        } else {
            feedItem.onTap(view)
        }
    }

    private fun instanceOfBiometricPrompt(
        onSuccess: ((Unit) -> Unit)
    ): BiometricPrompt {
        val executor = ContextCompat.getMainExecutor(activity)
        val callback = object : BiometricPrompt.AuthenticationCallback() {
            override fun onAuthenticationError(errorCode: Int, errString: CharSequence) {
                super.onAuthenticationError(errorCode, errString)
            }

            override fun onAuthenticationFailed() {
                super.onAuthenticationFailed()
            }

            override fun onAuthenticationSucceeded(result: BiometricPrompt.AuthenticationResult) {
                super.onAuthenticationSucceeded(result)
                onSuccess.invoke(Unit)
            }
        }
        return BiometricPrompt(activity, executor, callback)
    }
}
