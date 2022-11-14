package com.roy93group.cintalauncher.ui.settings.feedChooser

import android.content.Context
import io.posidon.android.conveniencelib.loadRaw
import io.posidon.android.rsslib.RssSource
import org.json.JSONArray

class Topic(
    val context: Context,
    val name: String,
    val id: Int
) {

    val sources = context.loadRaw(id) {
        val array = JSONArray(it.readText())
        try {
            Array(array.length()) {
                val obj = array.getJSONObject(it)
                RssSource(
                    obj.getString("name"),
                    obj.getString("url"), ""
                )
            }
        } catch (e: Exception) {
            e.printStackTrace()
            arrayOf()
        }
    }

    operator fun get(i: Int) = sources[i]
}
