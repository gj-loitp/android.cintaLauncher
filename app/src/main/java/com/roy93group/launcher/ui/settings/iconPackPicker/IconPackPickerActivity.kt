package com.roy93group.launcher.ui.settings.iconPackPicker

import android.graphics.drawable.Drawable
import android.os.Bundle
import androidx.annotation.Keep
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.ItemTouchHelper.DOWN
import androidx.recyclerview.widget.ItemTouchHelper.UP
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.loitp.core.ext.setSafeOnClickListener
import com.roy93group.app.C
import com.roy93group.launcher.R
import com.roy93group.launcher.ui.settings.SettingsActivity
import com.roy93group.launcher.ui.settings.iconPackPicker.viewHolders.IconPackViewHolder
import io.posidon.android.conveniencelib.getNavigationBarHeight
import io.posidon.android.conveniencelib.getStatusBarHeight
import io.posidon.android.launcherutils.IconTheming
import kotlinx.android.synthetic.main.activity_settings_icon_pack_picker.*
import java.util.*

/**
 * Updated by Loitp on 2022.12.17
 * Galaxy One company,
 * Vietnam
 * +840766040293
 * freuss47@gmail.com
 */
class IconPackPickerActivity : SettingsActivity() {

    override fun init(savedInstanceState: Bundle?) {
        C.changeStatusBarContrastStyle(this)
        setupViews()
    }

    override fun setLayoutResourceId(): Int {
        return R.layout.activity_settings_icon_pack_picker
    }

    private fun setupViews() {
        val colorPrimary = C.getColorPrimary()
        val colorBackground = C.getColorBackground()

        cl.setBackgroundColor(colorBackground)
        tvIconPacks.apply {
            setTextColor(colorPrimary)
        }
        btGetMoreThemes.apply {
            C.setBackground(this)
        }

        val iconPacks = IconTheming.getAvailableIconPacks(packageManager).mapTo(LinkedList()) {
            IconPack(
                icon = it.loadIcon(packageManager),
                label = it.loadLabel(packageManager).toString(),
                packageName = it.activityInfo.packageName
            )
        }

        iconPacks.sortWith { o1, o2 ->
            o1.label.compareTo(other = o2.label, ignoreCase = true)
        }

        val chosenIconPacks = run {
            val list = LinkedList<IconPack>()
            val strings = settings.getStrings("icon_packs")?.let(Array<String>::toMutableList)
                ?: return@run list
            var deleted = false
            for (string in strings) {
                val iconPack = iconPacks.find { it.packageName == string }
                if (iconPack == null) {
                    strings -= string
                    deleted = true
                } else {
                    iconPacks -= iconPack
                    list += iconPack
                }
            }
            if (deleted) {
                settings.edit(this) {
                    "icon_packs" set strings.toTypedArray()
                }
            }
            list
        }

        val systemPack = IconPack(
            icon = ContextCompat.getDrawable(this, R.drawable.ic_launcher)!!,
            label = getString(R.string.system),
            packageName = "system"
        )

        recyclerView.setPadding(
            /* left = */ 0,
            /* top = */ getStatusBarHeight(),
            /* right = */ 0,
            /* bottom = */ getNavigationBarHeight()
        )
        recyclerView.layoutManager = LinearLayoutManager(
            /* context = */ this,
            /* orientation = */ RecyclerView.VERTICAL,
            /* reverseLayout = */ false
        )
        val adapter = IconPackPickerAdapter(
            settings = settings,
            chosenIconPacks = chosenIconPacks,
            availableIconPacks = iconPacks,
            systemPack = systemPack
        )

        recyclerView.adapter = adapter
        val th = ItemTouchHelper(TouchCallback(adapter))
        th.attachToRecyclerView(recyclerView)

        btGetMoreThemes.setSafeOnClickListener {
            C.searchIconPack(this)
        }
    }

    class TouchCallback(val adapter: IconPackPickerAdapter) : ItemTouchHelper.Callback() {
        override fun getMovementFlags(
            recyclerView: RecyclerView,
            viewHolder: RecyclerView.ViewHolder
        ) = makeMovementFlags(
            if (viewHolder is IconPackViewHolder && viewHolder.type != IconPackPickerAdapter.SYSTEM_ICON_PACK) UP or DOWN else 0,
            0
        )

        override fun onSwiped(
            v: RecyclerView.ViewHolder,
            d: Int
        ) {
        }

        override fun onMove(
            recyclerView: RecyclerView,
            viewHolder: RecyclerView.ViewHolder,
            target: RecyclerView.ViewHolder
        ): Boolean {
            return adapter.onItemMove(
                context = recyclerView.context,
                viewHolder = viewHolder,
                target = target
            )
        }
    }

    @Keep
    class IconPack(
        val icon: Drawable,
        val label: String,
        val packageName: String,
    )
}
