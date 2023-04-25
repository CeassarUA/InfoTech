package com.ceassar.test.ui.main

import android.view.View
import androidx.recyclerview.widget.DiffUtil
import com.ceassar.test.R
import com.ceassar.test.data.datasource.model.City
import com.ceassar.test.databinding.ItemCityListBinding
import com.ceassar.test.ui.base.BaseListAdapter

class ListAdapter : BaseListAdapter<City, ListAdapter.ViewHolder>(ITEMS_COMPARATOR) {

    inner class ViewHolder(binding: ItemCityListBinding) :
        BaseListAdapter.BaseViewHolder(binding.root) {
        override fun bind(pos: Int) {

        }
    }

    companion object {
        private val ITEMS_COMPARATOR =
            object : DiffUtil.ItemCallback<City>() {
                override fun areItemsTheSame(
                    oldItem: City, newItem: City
                ): Boolean {
                    return newItem.id == oldItem.id
                }

                override fun areContentsTheSame(
                    oldItem: City, newItem: City
                ): Boolean {
                    return newItem == oldItem
                }
            }

    }

    override fun createViewHolder(view: View, viewType: Int): ViewHolder {
        return ViewHolder(ItemCityListBinding.bind(view))
    }

    override fun getItemViewType(position: Int): Int = R.layout.item_city_list


}