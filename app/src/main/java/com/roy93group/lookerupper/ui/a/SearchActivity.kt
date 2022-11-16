package com.roy93group.lookerupper.ui.a

import android.app.SearchManager
import android.content.Intent
import android.os.Bundle
import android.view.DragEvent
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.EditText
import androidx.core.widget.doOnTextChanged
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.loitpcore.annotation.IsAutoAnimation
import com.loitpcore.annotation.IsFullScreen
import com.loitpcore.annotation.LogTag
import com.loitpcore.core.base.BaseFontActivity
import com.roy93group.launcher.R
import com.roy93group.launcher.storage.Settings
import com.roy93group.launcher.ui.popup.appItem.ItemLongPress
import com.roy93group.lookerupper.data.Searcher
import com.roy93group.lookerupper.data.providers.AppProvider
import com.roy93group.lookerupper.data.providers.ContactProvider
import com.roy93group.lookerupper.data.providers.DuckDuckGoProvider
import com.roy93group.lookerupper.data.results.SearchResult
import com.roy93group.lookerupper.ui.adapter.SearchAdapter
import kotlin.math.abs

@LogTag("SearchActivity")
@IsFullScreen(false)
@IsAutoAnimation(false)
class SearchActivity : BaseFontActivity() {

    lateinit var adapter: SearchAdapter
    val settings = Settings()
    private val searcher = Searcher(
        settings,
        ::AppProvider,
        ::ContactProvider,
        ::DuckDuckGoProvider,
        update = ::updateResults
    )

    private fun updateResults(list: List<SearchResult>) = runOnUiThread {
        adapter.update(list)
    }

    val container: View by lazy { findViewById(R.id.cvSearchBarContainer) }

    override fun setLayoutResourceId(): Int {
        return R.layout.activity_search
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        settings.init(this)
        searcher.onCreate(this)
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O_MR1) {
//            wallpaperManager.addOnColorsChangedListener(
//                ::onColorsChangedListener,
//                container.handler
//            )
//            thread(name = "onCreate color update", isDaemon = true) {
//                ColorPalette.onColorsChanged(
//                    context = this,
//                    colorTheme = settings.colorTheme,
//                    onFinished = SearchActivity::loadColors
//                ) {
//                    wallpaperManager.getWallpaperColors(WallpaperManager.FLAG_SYSTEM)
//                }
//            }
//        }

        setupViews()

    }

    private fun setupViews() {
        val recyclerView = findViewById<RecyclerView>(R.id.recycler)
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
            container.post {
                setPadding(paddingLeft, container.measuredHeight, paddingRight, paddingBottom)
            }
        }
        findViewById<EditText>(R.id.etSearchBarText).run {
            doOnTextChanged { text, _, _, _ ->
                searcher.query(text)
            }
            setOnEditorActionListener { v, actionId, _ ->
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    val viewSearch = Intent(Intent.ACTION_WEB_SEARCH)
                    viewSearch.putExtra(SearchManager.QUERY, v.text)
                    v.context.startActivity(viewSearch)
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
}