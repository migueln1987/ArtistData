package com.random.artistdata.adapter

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.random.artistdata.R
import com.random.artistdata.models.TrackDTO
import com.random.artistdata.utils.CustomRecyclerViewAdapter
import com.random.artistdata.utils.inflate
import kotlinx.android.synthetic.main.track_list_item.view.*


class TrackListAdapter(private var onClickItem: (TrackDTO) -> Unit) :
    CustomRecyclerViewAdapter<TrackDTO, TrackListAdapter.TrackViewHolder>() {
    val list: ArrayList<TrackDTO> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TrackViewHolder =
        TrackViewHolder(parent.inflate(R.layout.track_list_item), onClickItem)

    override fun onBindViewHolder(holder: TrackViewHolder, position: Int) =
        holder.loadData(position)

    override fun getItemCount(): Int = list.size

    inner class TrackViewHolder(
        private val view: View,
        private val onClickItem: ((TrackDTO) -> Unit?)?
    ) : RecyclerView.ViewHolder(view) {

        fun loadData(position: Int) {
            val data = list[position]

            view.apply {
                this.track_title.text = data.trackName ?: ""
                this.setOnClickListener {
                    onClickItem?.let { onClick -> onClick(data) }
                }
            }

        }

    }

    override fun updateData(data: List<TrackDTO>) {
        this.list.clear()
        if (data.isNotEmpty()) {
            this.list.addAll(data)
        }
        notifyDataSetChanged()
    }
}