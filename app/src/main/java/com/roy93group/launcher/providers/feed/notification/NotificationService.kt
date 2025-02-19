package com.roy93group.launcher.providers.feed.notification

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationChannelGroup
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.media.MediaMetadata
import android.media.session.MediaController
import android.media.session.MediaSessionManager
import android.media.session.PlaybackState
import android.os.Build
import android.os.UserHandle
import android.service.notification.NotificationListenerService
import android.service.notification.StatusBarNotification
import androidx.core.app.NotificationManagerCompat
import com.roy93group.launcher.BuildConfig
import com.roy93group.launcher.data.feed.items.FeedItem
import com.roy93group.launcher.data.feed.items.FeedItemWithMedia
import com.roy93group.launcher.providers.feed.media.MediaItemCreator
import com.roy93group.launcher.util.StackTraceActivity
import java.util.concurrent.locks.ReentrantLock
import kotlin.concurrent.thread

/**
 * Updated by Loitp on 2022.12.16
 * Galaxy One company,
 * Vietnam
 * +840766040293
 * freuss47@gmail.com
 */
class NotificationService : NotificationListenerService() {
    companion object {
        fun init(context: Context) {
            if (NotificationManagerCompat.getEnabledListenerPackages(context)
                    .contains(context.packageName)
            ) {
                context.startService(Intent(context, NotificationService::class.java))
            }
        }

        var notifications: MutableList<FeedItem> = ArrayList()
            private set

        var mediaItem: FeedItemWithMedia? = null
            private set

        private var onSummaryUpdate: () -> Unit = {}

        private val lock = ReentrantLock()

        private fun pickController(controllers: List<MediaController>): MediaController {
            for (i in controllers.indices) {
                val mc = controllers[i]
                if (mc.playbackState?.state == PlaybackState.STATE_PLAYING) {
                    return mc
                }
            }
            return controllers[0]
        }

        private val listeners = HashMap<String, () -> Unit>()

        fun setOnUpdate(key: String, onUpdate: () -> Unit) {
            listeners[key] = onUpdate
        }
    }

    private val componentName = ComponentName(BuildConfig.APPLICATION_ID, this::class.java.name)

    override fun onCreate() {
        StackTraceActivity.init(applicationContext)
        if (!NotificationManagerCompat.getEnabledListenerPackages(applicationContext)
                .contains(applicationContext.packageName)
        ) {
            stopSelf()
        } else {
            val msm = getSystemService(MediaSessionManager::class.java)
            msm.addOnActiveSessionsChangedListener(::onMediaControllersUpdated, componentName)
            onMediaControllersUpdated(msm.getActiveSessions(componentName))
        }
    }

    override fun onDestroy() {
        val msm = getSystemService(MediaSessionManager::class.java)
        msm.removeOnActiveSessionsChangedListener(::onMediaControllersUpdated)
        super.onDestroy()
    }

    override fun onListenerConnected() {
        loadNotifications(activeNotifications)
    }

    override fun onNotificationPosted(s: StatusBarNotification) =
        loadNotifications(activeNotifications)

    override fun onNotificationPosted(s: StatusBarNotification?, rm: RankingMap?) =
        loadNotifications(activeNotifications)

    override fun onNotificationRemoved(s: StatusBarNotification) =
        loadNotifications(activeNotifications)

    override fun onNotificationRemoved(s: StatusBarNotification?, rm: RankingMap?) =
        loadNotifications(activeNotifications)

    override fun onNotificationRemoved(s: StatusBarNotification, rm: RankingMap, reason: Int) =
        loadNotifications(activeNotifications)

    override fun onNotificationRankingUpdate(rm: RankingMap) =
        loadNotifications(activeNotifications)

    override fun onNotificationChannelModified(
        pkg: String,
        u: UserHandle,
        c: NotificationChannel,
        modifType: Int
    ) = loadNotifications(activeNotifications)

    override fun onNotificationChannelGroupModified(
        pkg: String,
        u: UserHandle,
        g: NotificationChannelGroup,
        modifType: Int
    ) = loadNotifications(activeNotifications)

    private fun loadNotifications(notifications: Array<StatusBarNotification>?) {
        thread(name = "NotificationService loading thread", isDaemon = true) {
            var tmpNotifications: MutableList<FeedItem> = ArrayList()
            var i = 0
            try {
                if (notifications != null) {
                    while (i < notifications.size) {
                        val notification = notifications[i]
                        if (
                            Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q
                            && notification.notification.bubbleMetadata?.isNotificationSuppressed == true
                        ) {
                            i++
                            continue
                        }
                        val isSummary =
                            notification.notification.flags and Notification.FLAG_GROUP_SUMMARY != 0
                        if (!isSummary) {
                            val isMusic = notification.notification.extras
                                .getCharSequence(Notification.EXTRA_TEMPLATE) == Notification.MediaStyle::class.java.name
                            if (isMusic) {
                                i++
                                continue
                            }
                            tmpNotifications += NotificationCreator.create(
                                context = applicationContext,
                                notification = notification,
                                notificationService = this
                            )
                        }
                        i++
                    }
                }
                tmpNotifications = tmpNotifications.distinctBy { it.uid }.toMutableList()
            } catch (e: Exception) {
                e.printStackTrace()
            }
            lock.lock()
            Companion.notifications = tmpNotifications
            listeners.forEach { (_, x) -> x() }
            onSummaryUpdate()
            lock.unlock()
        }
    }

    private fun onMediaControllersUpdated(controllers: MutableList<MediaController>?) {
        val old = mediaItem
        if (controllers.isNullOrEmpty()) {
            mediaItem = null
            if (old != mediaItem) {
                onSummaryUpdate()
            }
            return
        }
        val controller = pickController(controllers)
        mediaItem =
            controller.metadata?.let {
                MediaItemCreator.create(
                    context = applicationContext,
                    controller = controller,
                    mediaMetadata = it
                )
            }
        if (old != mediaItem) {
            onSummaryUpdate()
        }
        controller.registerCallback(object : MediaController.Callback() {
            override fun onMetadataChanged(metadata: MediaMetadata?) {
                mediaItem =
                    metadata?.let {
                        MediaItemCreator.create(
                            context = applicationContext,
                            controller = controller,
                            mediaMetadata = it
                        )
                    }
                onSummaryUpdate()
            }
        })
    }
}
