package com.roy93group.launcher.ui.view

import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent

/**
 * Updated by Loitp on 2022.12.17
 * Galaxy One company,
 * Vietnam
 * +840766040293
 * freuss47@gmail.com
 */

class ExpandableListView : android.widget.ExpandableListView {

    constructor(context: Context) : super(context)
    constructor(
        context: Context,
        attr: AttributeSet
    ) : super(context, attr)

    constructor(
        context: Context,
        attr: AttributeSet,
        defStyleAttr: Int
    ) : super(
        context,
        attr,
        defStyleAttr
    )

    override fun dispatchTouchEvent(ev: MotionEvent): Boolean {
        return super.dispatchTouchEvent(ev).also {
            if (ev.action == MotionEvent.ACTION_DOWN && canScrollVertically(-1)) {
                requestDisallowInterceptTouchEvent(true)
            }
        }
    }
}
