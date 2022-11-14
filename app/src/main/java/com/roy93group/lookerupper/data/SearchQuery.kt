package com.roy93group.lookerupper.data

@JvmInline
value class SearchQuery(
    val text: CharSequence
) {
    inline val length: Int get() = text.length

    override fun toString() = text.toString()
}
