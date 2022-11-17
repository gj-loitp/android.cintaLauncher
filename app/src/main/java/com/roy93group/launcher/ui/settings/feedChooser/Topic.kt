package com.roy93group.launcher.ui.settings.feedChooser

import android.content.Context
import io.posidon.android.conveniencelib.loadRaw
import io.posidon.android.rsslib.RssSource
import org.json.JSONArray

/**
 * Updated by Loitp on 2022.12.17
 * Galaxy One company,
 * Vietnam
 * +840766040293
 * freuss47@gmail.com
 */
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
