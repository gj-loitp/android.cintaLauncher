package com.roy93group.views

import android.content.Context
import android.util.AttributeSet
import androidx.recyclerview.widget.LinearLayoutManager

/**
 * Created by Loitp on 04,January,2023
 * Galaxy One company,
 * Vietnam
 * +840766040293
 * freuss47@gmail.com
 */
class LinearLayoutManagerWrapper : LinearLayoutManager {
    constructor(context: Context?) : super(context) {}
    constructor(
        context: Context?,
        orientation: Int,
        reverseLayout: Boolean
    ) : super(
        context,
        orientation,
        reverseLayout
    ) {
    }

    constructor(
        context: Context?,
        attrs: AttributeSet?,
        defStyleAttr: Int,
        defStyleRes: Int
    ) : super(context, attrs, defStyleAttr, defStyleRes) {
    }

    override fun supportsPredictiveItemAnimations(): Boolean {
        return false
    }
}
