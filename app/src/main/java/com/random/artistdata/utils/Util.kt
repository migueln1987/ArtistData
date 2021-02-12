package com.random.artistdata.utils

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.DrawableRes
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.RecyclerView
import okhttp3.ResponseBody
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader

const val TRACK_LIST: String = "TRACK_LIST"
const val ARTIST_NAME: String = "ARTIST_NAME"


@BindingAdapter("app:adapter", "app:data")
fun <T : CustomRecyclerViewAdapter<*, *>> bind(
    recyclerView: RecyclerView,
    adapter: T,
    data: ArrayList<Nothing>
) {
    recyclerView.adapter = adapter
    adapter.updateData(data)
}

@BindingAdapter("android:visibility")
fun setVisibility(view: View, value: Boolean) {
    view.visibility = if (value) View.VISIBLE else View.GONE
}

fun getStringResponseFromRaw(response: ResponseBody): String? {
    var reader: BufferedReader? = null
    val sb = StringBuilder()
    try {
        reader = BufferedReader(InputStreamReader(response.byteStream()))
        var line: String?
        try {
            while (reader.readLine().also { line = it } != null) {
                sb.append(line)
            }
        } catch (e: IOException) {
            e.printStackTrace()
        }
    } catch (e: Exception) {
        e.printStackTrace()
    }
    return sb.toString()
}

fun ViewGroup.inflate(layoutId: Int, attachToRoot: Boolean = false): View {
    return LayoutInflater.from(context).inflate(layoutId, this, attachToRoot)
}

fun RecyclerView.setDivider(@DrawableRes drawableRes: Int) {
    val divider = DividerItemDecoration(
        this.context,
        DividerItemDecoration.VERTICAL
    )
    val drawable = ContextCompat.getDrawable(
        this.context,
        drawableRes
    )
    drawable?.let {
        divider.setDrawable(it)
        addItemDecoration(divider)
    }
}