package com.roy93group.launcher.ui.view.scrollbar.hue

import android.content.Context
import android.graphics.Canvas
import androidx.core.graphics.ColorUtils
import com.roy93group.launcher.data.items.App
import com.roy93group.launcher.providers.app.AppCollection
import com.roy93group.launcher.ui.drawer.AppDrawerAdapter
import com.roy93group.launcher.ui.view.scrollbar.Scrollbar
import com.roy93group.launcher.ui.view.scrollbar.ScrollbarController
import io.posidon.android.conveniencelib.units.dp
import io.posidon.android.conveniencelib.units.toFloatPixels
import java.util.*

/**
 * Updated by Loitp on 2022.12.17
 * Galaxy One company,
 * Vietnam
 * +840766040293
 * freuss47@gmail.com
 */

class HueScrollbarController(
    scrollbar: Scrollbar
) : ScrollbarController(scrollbar) {

    private val paint by scrollbar::paint
    private var dotRadius = 0f
    private var activeDotRadius = 0f

    override fun draw(canvas: Canvas) {
        val insideHeight =
            scrollbar.height - scrollbar.paddingTop - scrollbar.paddingBottom.toFloat()
        val insideWidth = scrollbar.width - scrollbar.paddingLeft - scrollbar.paddingRight.toFloat()
        val (dotWidth, dotHeight) = if (scrollbar.orientation == Scrollbar.VERTICAL) {
            insideWidth / 2f + scrollbar.paddingLeft to insideHeight / indexer.sections.lastIndex.toFloat()
        } else {
            insideWidth / indexer.sections.lastIndex.toFloat() to insideHeight / 2f + scrollbar.paddingTop
        }
        for (i in indexer.sections.indices) {
            val (x, y) = if (scrollbar.orientation == Scrollbar.VERTICAL) {
                dotWidth to dotHeight * i + scrollbar.paddingTop
            } else {
                dotWidth * i + scrollbar.paddingLeft to dotHeight
            }
            val isHighlighted =
                i >= scrollbar.currentScrolledSectionStart && i <= scrollbar.currentScrolledSectionEnd
            val hsl = floatArrayOf(indexer.sections[i], 1f, if (isHighlighted) 0.4f else 0.3f)
            paint.color = ColorUtils.HSLToColor(hsl)
            val r = if (isHighlighted) activeDotRadius else dotRadius
            canvas.drawCircle(x, y, r, paint)
        }
    }

    override fun updateTheme(context: Context) {
        dotRadius = 4.dp.toFloatPixels(scrollbar)
        activeDotRadius = 8.dp.toFloatPixels(scrollbar)
        scrollbar.invalidate()
    }

    var step = 0f
    override fun loadSections(apps: AppCollection) {
        apps.list.sortBy {
            it.hsl[0]
        }
        var startHue = apps.list[0].hsl[0]
        step = (apps.list.last().hsl[0] - startHue) / 32f
        var section = LinkedList<App>().also { apps.sections.add(it) }
        for (app in apps.list) {
            val currentHue = app.hsl[0]
            if (currentHue - startHue < step) {
                section.add(app)
            } else section = LinkedList<App>().apply {
                add(app)
                apps.sections.add(this)
                startHue = currentHue
            }
        }
    }

    override fun createSectionHeaderItem(
        items: LinkedList<AppDrawerAdapter.DrawerItem>,
        section: List<App>
    ) {
    }

    override val indexer = HueSectionIndexer(this)

    override fun updateAdapterIndexer(
        adapter: AppDrawerAdapter,
        appSections: List<List<App>>
    ) {
        indexer.updateSections(adapter = adapter, appSections = appSections)
        adapter.indexer = this.indexer
    }
}
