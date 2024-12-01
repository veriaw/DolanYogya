package com.veriaw.dolanyogya.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.veriaw.dolanyogya.adapter.PlaceAdapter.Companion.DIFF_CALLBACK
import com.veriaw.dolanyogya.data.entity.PlaceEntity
import com.veriaw.dolanyogya.databinding.ListSearchBinding
import com.veriaw.dolanyogya.ui.detail.DetailActivity

class SearchAdapter: ListAdapter<PlaceEntity, SearchAdapter.MyViewHolder>(DIFF_CALLBACK)  {
    class MyViewHolder(val binding: ListSearchBinding): RecyclerView.ViewHolder(binding.root){
        fun bind(place: PlaceEntity) {
            binding.placeName.text=place.placeName
            val rate = place.rating_avg?.div(10)
            binding.ratingText.text=rate.toString()
            binding.tvCategory.text=place.category
            Glide.with(binding.placeImage.context)
                .load(place.pictureUrl)
                .into(binding.placeImage)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = ListSearchBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val place = getItem(position)
        holder.bind(place)
        holder.itemView.setOnClickListener{
            val moveWithObjectIntent = Intent(holder.itemView.context, DetailActivity::class.java)
            moveWithObjectIntent.putExtra("ID_PLACE", place.id)
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