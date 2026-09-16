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
import com.df.dicodingevent.databinding.ItemEventBinding

class FinishedEventAdapter : ListAdapter<EventItem, FinishedEventAdapter.FinishedViewHolder>(DIFF_CALLBACK) {

    var onItemClick: ((EventItem) -> Unit)? = null

    inner class FinishedViewHolder(private val binding: ItemEventBinding) :
        RecyclerView.ViewHolder(binding.root) {

        @SuppressLint("SetTextI18n")
        fun bind(event: EventItem) {
            binding.tvEventName.text = event.name
            binding.tvEventDescription.text = event.summary

            Glide.with(binding.root.context)
                .load(event.imageLogo)
//                .placeholder(R.drawable.bg_image_placeholder)
//                .error(R.drawable.bg_image_placeholder)
                .into(binding.ivEventImage)

            binding.root.setOnClickListener {
                onItemClick?.invoke(event)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FinishedViewHolder {
        val binding = ItemEventBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return FinishedViewHolder(binding)
    }

    override fun onBindViewHolder(holder: FinishedViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<EventItem>() {
            override fun areItemsTheSame(oldItem: EventItem, newItem: EventItem) =
                oldItem.id == newItem.id

            override fun areContentsTheSame(oldItem: EventItem, newItem: EventItem) =
                oldItem == newItem
        }
    }
}