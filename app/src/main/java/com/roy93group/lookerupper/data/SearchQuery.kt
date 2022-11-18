package com.roy93group.lookerupper.data

/**
 * Updated by Loitp on 2022.12.18
 * Galaxy One company,
 * Vietnam
 * +840766040293
 * freuss47@gmail.com
 */
@JvmInline
value class SearchQuery(
    val text: CharSequence
) {
    inline val length: Int get() = text.length

    override fun toString() = text.toString()
}
