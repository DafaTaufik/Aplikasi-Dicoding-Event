package com.df.dicodingevent.ui.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.df.dicodingevent.R
import com.df.dicodingevent.data.response.EventItem
import com.df.dicodingevent.databinding.ItemEventCardBinding

class EventCardAdapter : ListAdapter<EventItem, EventCardAdapter.EventViewHolder>(DIFF_CALLBACK) {

    var onItemClick: ((EventItem) -> Unit)? = null

    inner class EventViewHolder(private val binding: ItemEventCardBinding) :
        RecyclerView.ViewHolder(binding.root) {

        @SuppressLint("SetTextI18n")
        fun bind(event: EventItem) {
            binding.tvEventName.text = event.name
            binding.tvEventCity.text = event.cityName
            binding.tvEventQuota.text = "${event.registrants} / ${event.quota} pendaftar"
            binding.tvEventDate.text = event.beginTime

            Glide.with(binding.root.context)
                .load(event.imageLogo)
                .placeholder(R.drawable.bg_image_placeholder)
                .error(R.drawable.bg_image_placeholder_error)
                .into(binding.ivEventImage)


            binding.root.setOnClickListener {
                onItemClick?.invoke(event)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EventViewHolder {
        val binding = ItemEventCardBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return EventViewHolder(binding)
    }

    override fun onBindViewHolder(holder: EventViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<EventItem>() {
            override fun areItemsTheSame(oldItem: EventItem, newItem: EventItem): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: EventItem, newItem: EventItem): Boolean {
                return oldItem == newItem
            }
        }
    }
}