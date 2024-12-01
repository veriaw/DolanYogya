package com.veriaw.dolanyogya.adapter

import android.annotation.SuppressLint
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.veriaw.dolanyogya.adapter.PlaceAdapter.MyViewHolder
import com.veriaw.dolanyogya.data.entity.BookmarkEntity
import com.veriaw.dolanyogya.data.entity.PlaceEntity
import com.veriaw.dolanyogya.databinding.ListBookmarksBinding
import com.veriaw.dolanyogya.databinding.ListPlaceBinding
import com.veriaw.dolanyogya.ui.detail.DetailActivity

class BookmarkAdapter: ListAdapter<PlaceEntity, BookmarkAdapter.MyViewHolder>(DIFF_CALLBACK) {
    class MyViewHolder(val binding: ListBookmarksBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(place: PlaceEntity) {
            binding.placeName.text=place.placeName
            binding.placeCategory.text=place.category
            val rate = place.rating_avg?.div(10)
            binding.ratingText.text=rate.toString()
            Glide.with(binding.placeImage.context)
                .load(place.pictureUrl)
                .into(binding.placeImage)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = ListBookmarksBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val bookmark = getItem(position)
        holder.bind(bookmark)
        holder.itemView.setOnClickListener{
            val moveWithObjectIntent = Intent(holder.itemView.context, DetailActivity::class.java)
            moveWithObjectIntent.putExtra("ID_PLACE", bookmark.id)
            holder.itemView.context.startActivity(moveWithObjectIntent)
        }
    }

    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<PlaceEntity>() {
            override fun areItemsTheSame(oldItem: PlaceEntity, newItem: PlaceEntity): Boolean {
                return oldItem == newItem
            }
            override fun areContentsTheSame(oldItem: PlaceEntity, newItem: PlaceEntity): Boolean {
                return oldItem == newItem
            }
        }
    }
}