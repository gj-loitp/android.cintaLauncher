package com.roy93group.launcher.providers.feed.notification

import android.app.Notification
import android.app.NotificationManager
import android.content.Context
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.os.Build
import android.os.Bundle
import android.service.notification.StatusBarNotification
import android.view.View
import androidx.core.app.NotificationManagerCompat
import com.roy93group.launcher.data.feed.items.*
import java.time.Instant

object NotificationCreator {

    private fun getSmallIcon(context: Context, n: StatusBarNotification): Drawable? {
        return n.notification.smallIcon?.loadDrawable(context) ?: n.notification.getLargeIcon()
            ?.loadDrawable(context)
    }

    @Suppress("unused")
    fun getLargeIcon(context: Context, n: StatusBarNotification): Drawable? {
        return n.notification.getLargeIcon()?.loadDrawable(context)
    }

    private fun getSource(context: Context, n: StatusBarNotification): String {
        return context.packageManager.getApplicationLabel(
            context.packageManager.getApplicationInfo(
                /* p0 = */ n.packageName,
                /* p1 = */ 0
            )
        ).toString()
    }

    fun getColor(n: StatusBarNotification): Int {
        return n.notification.color
    }

    private fun getTitle(extras: Bundle): CharSequence? {
        val title = extras.getCharSequence(Notification.EXTRA_TITLE)
        if (title == null || title.toString().replace(oldValue = " ", newValue = "").isEmpty()) {
            return null
        }
        return title
    }

    private fun getText(extras: Bundle): CharSequence? {
        val messages = extras.getParcelableArray(Notification.EXTRA_MESSAGES)
        return if (messages == null) {
            extras.getCharSequence(Notification.EXTRA_BIG_TEXT)
                ?: extras.getCharSequence(Notification.EXTRA_TEXT)
        } else buildString {
            messages.forEach {
                val bundle = it as Bundle
                appendLine(bundle.getCharSequence("text"))
            }
            delete(lastIndex, length)
        }
    }

    private fun getBigImage(context: Context, extras: Bundle): Drawable? {
        val b = extras[Notification.EXTRA_PICTURE] as Bitmap?
        if (b != null) {
            try {
                val d = BitmapDrawable(context.resources, b)
                if (b.width < 64 || b.height < 64) {
                    return null
                }
                return d
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
        return null
    }

    private fun getImportance(importance: Int): Int {
        return when (importance) {
            NotificationManager.IMPORTANCE_NONE,
            NotificationManager.IMPORTANCE_MIN -> -1
            NotificationManager.IMPORTANCE_LOW,
            NotificationManager.IMPORTANCE_DEFAULT -> 0
            NotificationManager.IMPORTANCE_HIGH -> 1
            NotificationManager.IMPORTANCE_MAX -> 2
            else -> throw IllegalStateException("Invalid notification importance")
        }
    }

    fun create(
        context: Context,
        notification: StatusBarNotification,
        notificationService: NotificationService
    ): FeedItem {

        val extras = notification.notification.extras

        var title = getTitle(extras)
        var text = getText(extras)
        if (title == null) {
            title = text
            text = null
        }
        val icon = getSmallIcon(context = context, n = notification)
        val source = getSource(context = context, n = notification)

        //println(extras.keySet().joinToString("\n") { "$it -> " + extras[it].toString() })

        val instant = Instant.ofEpochMilli(notification.postTime)

        val progress = extras.getInt(Notification.EXTRA_PROGRESS, -1)
        val maxProgress = extras.getInt(Notification.EXTRA_PROGRESS_MAX, -1)
        val intermediate = extras.getBoolean(Notification.EXTRA_PROGRESS_INDETERMINATE, false)

        val color = getColor(notification)

        val channel = NotificationManagerCompat.from(context)
            .getNotificationChannel(/* channelId = */ notification.notification.channelId)
        val importance = channel?.importance?.let { getImportance(it) } ?: 0

        val actions = notification.notification.actions?.map { action ->
            FeedItemAction(action.title.toString()) {
                action.actionIntent.send()
            }
        }?.toTypedArray() ?: emptyArray()

        val id = (notification.id.toLong() shl 32 or
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) notification.uid.toLong()
                else notification.packageName.hashCode().toLong()
                ) xor (notification.tag?.longHash() ?: 0)

        val uid = buildString {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                append('⍾')
                append((notification.id.toLong() shl 32 or notification.uid.toLong()).toString(16))
                append('⍾')
                append(notification.tag)
            } else {
                append(notification.packageName)
                append('⍾')
                append(notification.id.toString(16))
                append('⍾')
                append(notification.tag)
            }
        }

        val isDismissible = notification.isClearable
        val autoCancel = notification.notification.flags and Notification.FLAG_AUTO_CANCEL != 0

        val meta = FeedItemMeta(
            sourcePackageName = notification.packageName,
            importance = importance.coerceAtLeast(0),
            isNotification = true
        )

        if (importance == -1) {
            return object : FeedItemSmall {
                override val color = color
                override val title = title?.toString() ?: ""
                override val sourceIcon = icon
                override val description = text?.toString()
                override val source = source
                override val actions = actions
                override val instant = instant
                override fun onTap(view: View) {
                    try {
                        notification.notification.contentIntent?.send()
                        if (autoCancel)
                            notificationService.cancelNotification(notification.key)
                    } catch (e: Exception) {
                        notificationService.cancelNotification(notification.key)
                        e.printStackTrace()
                    }
                }

                override val isDismissible = isDismissible
                override fun onDismiss(view: View) {
                    notificationService.cancelNotification(notification.key)
                }

                override val uid = uid
                override val id = id
                override val meta = meta
            }
        }

        if (maxProgress > 0 || intermediate) {
            return object : FeedItemWithProgress {
                override val max = maxProgress
                override val progress = progress
                override val isIntermediate = intermediate
                override val color = color
                override val title = title?.toString() ?: ""
                override val sourceIcon = icon
                override val description = text?.toString()
                override val source = source
                override val actions = actions
                override val instant = instant
                override fun onTap(view: View) {
                    try {
                        notification.notification.contentIntent?.send()
                        if (autoCancel)
                            notificationService.cancelNotification(notification.key)
                    } catch (e: Exception) {
                        notificationService.cancelNotification(notification.key)
                        e.printStackTrace()
                    }
                }

                override val isDismissible = isDismissible
                override fun onDismiss(view: View) {
                    notificationService.cancelNotification(notification.key)
                }

                override val uid = uid
                override val id = id
                override val meta = meta
            }
        }

        val bigPic = getBigImage(context, extras)

        if (bigPic != null) {
            return object : FeedItemWithBigImage {
                override val image: Drawable = bigPic
                override val color = color
                override val title = title?.toString() ?: ""
                override val sourceIcon = icon
                override val description = text?.toString()
                override val source = source
                override val actions = actions
                override val instant = instant
                override fun onTap(view: View) {
                    try {
                        notification.notification.contentIntent?.send()
                        if (autoCancel)
                            notificationService.cancelNotification(notification.key)
                    } catch (e: Exception) {
                        notificationService.cancelNotification(notification.key)
                        e.printStackTrace()
                    }
                }

                override val isDismissible = isDismissible
                override fun onDismiss(view: View) {
                    notificationService.cancelNotification(notification.key)
                }

                override val uid = uid
                override val id = id
                override val meta = meta
            }
        }

        return object : FeedItem {
            override val color = color
            override val title = title?.toString() ?: ""
            override val sourceIcon = icon
            override val description = text?.toString()
            override val source = source
            override val actions = actions
            override val instant = instant
            override fun onTap(view: View) {
                try {
                    notification.notification.contentIntent?.send()
                    if (autoCancel)
                        notificationService.cancelNotification(notification.key)
                } catch (e: Exception) {
                    notificationService.cancelNotification(notification.key)
                    e.printStackTrace()
                }
            }

            override val isDismissible = isDismissible
            override fun onDismiss(view: View) {
                notificationService.cancelNotification(notification.key)
            }

            override val uid = uid
            override val id = id
            override val meta = meta
        }
    }
}
