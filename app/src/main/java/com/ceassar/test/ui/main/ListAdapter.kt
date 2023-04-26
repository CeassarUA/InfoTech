package com.ceassar.test.ui.main

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions.withCrossFade
import com.ceassar.test.R
import com.ceassar.test.data.datasource.local.database.CityEntity
import com.ceassar.test.databinding.ItemCityListBinding

class ListAdapter(val onItemClick: (CityEntity) -> Unit) :
    PagingDataAdapter<CityEntity, ListAdapter.ViewHolder>(ITEMS_COMPARATOR) {

    override fun getItemViewType(position: Int): Int =
        R.layout.item_city_list

    override fun getItemCount(): Int {
        val count = super.getItemCount()
        Log.d("adapter_count", count.toString())
        return count
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(viewType, parent, false)
        return ViewHolder(ItemCityListBinding.bind(view))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(position)
    }


    inner class ViewHolder(val binding: ItemCityListBinding) :
        RecyclerView.ViewHolder(binding.root) {
        init {
            binding.root.setOnClickListener {
                getItem(absoluteAdapterPosition)?.let { it1 -> onItemClick(it1) }
            }
        }


        fun bind(pos: Int) {
            getItem(pos)?.apply {
                binding.tvName.text = name
                Glide.with(binding.ivLogo).load(if (pos % 2 == 0) imageEven else imageOdd)
                    .transition(withCrossFade())
                    .fitCenter()
                    .placeholder(android.R.color.white)
                    .diskCacheStrategy(DiskCacheStrategy.ALL).into(binding.ivLogo)
            }

        }
    }

    companion object {

        const val imageOdd = "https://infotech.gov.ua/storage/img/Temp1.png"
        const val imageEven = "https://infotech.gov.ua/storage/img/Temp3.png"
        private val ITEMS_COMPARATOR =
            object : DiffUtil.ItemCallback<CityEntity>() {
                override fun areItemsTheSame(
                    oldItem: CityEntity, newItem: CityEntity
                ): Boolean {
                    return newItem.id == oldItem.id
                }

                override fun areContentsTheSame(
                    oldItem: CityEntity, newItem: CityEntity
                ): Boolean {
                    return newItem == oldItem
                }
            }

    }


}