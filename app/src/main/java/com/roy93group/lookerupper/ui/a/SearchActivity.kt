package com.roy93group.lookerupper.ui.a

import android.app.SearchManager
import android.content.Intent
import android.os.Bundle
import android.view.DragEvent
import android.view.View
import android.view.inputmethod.EditorInfo
import androidx.core.view.isVisible
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.loitp.annotation.IsAutoAnimation
import com.loitp.annotation.IsFullScreen
import com.loitp.annotation.IsKeepScreenOn
import com.loitp.annotation.LogTag
import com.loitp.core.base.BaseFontActivity
import com.loitp.core.common.Constants
import com.loitp.core.ext.changeLayersColor
import com.loitp.core.utilities.LActivityUtil
import com.loitp.core.utilities.LUIUtil
import com.loitp.data.ActivityData
import com.roy93group.app.C
import com.roy93group.launcher.R
import com.roy93group.launcher.storage.Settings
import com.roy93group.launcher.ui.popup.appItem.ItemLongPress
import com.roy93group.lookerupper.data.Searcher
import com.roy93group.lookerupper.data.providers.AppProvider
import com.roy93group.lookerupper.data.providers.ContactProvider
import com.roy93group.lookerupper.data.providers.DuckDuckGoProvider
import com.roy93group.lookerupper.data.results.SearchResult
import com.roy93group.lookerupper.ui.adapter.SearchAdapter
import kotlinx.android.synthetic.main.activity_search.*
import kotlinx.android.synthetic.main.activity_search_search_bar.*
import kotlin.math.abs

/**
 * Updated by Loitp on 2022.12.18
 * Galaxy One company,
 * Vietnam
 * +840766040293
 * freuss47@gmail.com
 */
@LogTag("SearchActivity")
@IsFullScreen(false)
@IsAutoAnimation(false)
@IsKeepScreenOn(false)
class SearchActivity : BaseFontActivity() {

    lateinit var adapter: SearchAdapter
    val settings = Settings()

    private val searcher = Searcher(
        settings = settings,
        ::AppProvider,
        ::ContactProvider,
        ::DuckDuckGoProvider,
        update = ::updateResults
    )

    private fun updateResults(list: List<SearchResult>) = runOnUiThread {
        adapter.update(list)
//        logE("updateResults ${list.size}")
        if (list.isEmpty()) {
            lavNoData?.isVisible = !etSearchBarText?.text.isNullOrEmpty()
        } else {
            lavNoData?.isVisible = false
        }
    }

    override fun setLayoutResourceId(): Int {
        return R.layout.activity_search
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        C.changeStatusBarContrastStyle(this)

        settings.init(this)
        searcher.onCreate(this)
        setupViews()
    }

    private fun setupViews() {
        val colorPrimary = C.getColorPrimary()
        val colorBackground = C.getColorBackground()

        fl.setBackgroundColor(colorBackground)
        cardView.setCardBackgroundColor(colorPrimary)
        lav.changeLayersColor(colorPrimary)
        lavNoData.changeLayersColor(colorPrimary)
        adapter = SearchAdapter(activity = this, recyclerView = recyclerView, isOnCard = false)
        recyclerView.run {
            layoutManager =
                GridLayoutManager(
                    /* context = */ this@SearchActivity,
                    /* spanCount = */ 3,
                    /* orientation = */ RecyclerView.VERTICAL,
                    /* reverseLayout = */ false
                ).apply {
                    spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
                        override fun getSpanSize(i: Int): Int =
                            if (adapter?.getItemViewType(i) == SearchAdapter.RESULT_APP) 1
                            else 3
                    }
                }
            this.adapter = this@SearchActivity.adapter
            cvSearchBarContainer.post {
                setPadding(
                    paddingLeft,
                    cvSearchBarContainer.measuredHeight,
                    paddingRight,
                    paddingBottom
                )
            }
        }
        etSearchBarText?.run {
            LUIUtil.addTextChangedListener(
                editText = this,
                delayInMls = 700,
                afterTextChanged = { s ->
//                    logD("doOnTextChanged $s")
                    lav.isVisible = s.isEmpty()
                    searcher.query(s)
                }
            )
            this.setOnEditorActionListener { v, actionId, _ ->
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    val viewSearch = Intent(Intent.ACTION_WEB_SEARCH)
                    viewSearch.putExtra(SearchManager.QUERY, v.text)
                    v.context.startActivity(viewSearch)
                    LActivityUtil.tranIn(v.context)
                    true
                } else false
            }
        }
        window.decorView.findViewById<View>(android.R.id.content).run {
            setOnDragListener { _, event ->
                when (event.action) {
                    DragEvent.ACTION_DRAG_STARTED -> {
                        val v = (event.localState as? View?)
                        v?.visibility = View.INVISIBLE
                    }
                    DragEvent.ACTION_DRAG_LOCATION -> {
                        val v = (event.localState as? View?)
                        if (v != null) {
                            val location = IntArray(2)
                            v.getLocationOnScreen(location)
                            val x = abs(event.x - location[0] - v.measuredWidth / 2f)
                            val y = abs(event.y - location[1] - v.measuredHeight / 2f)
                            if (x > v.width / 3.5f || y > v.height / 3.5f) {
                                ItemLongPress.currentPopup?.dismiss()
                            }
                        }
                    }
                    DragEvent.ACTION_DRAG_ENDED,
                    DragEvent.ACTION_DROP -> {
                        val v = (event.localState as? View?)
                        v?.visibility = View.VISIBLE
                        ItemLongPress.currentPopup?.isFocusable = true
                        ItemLongPress.currentPopup?.update()
                    }
                }
                true
            }
        }
    }

    override fun onDestroy() {
        ActivityData.instance.type = Constants.TYPE_ACTIVITY_TRANSITION_ZOOM
        super.onDestroy()
    }
}
