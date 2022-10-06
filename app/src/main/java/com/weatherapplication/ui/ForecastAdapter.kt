package com.weatherapplication.ui

import android.util.Log
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.weatherapplication.data.model.forecast.ForecastItem

class ForecastAdapter : ListAdapter<ForecastItem, ForecastViewHolder>(DIFF_CALLBACK){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ForecastViewHolder {
        Log.d("ViewHolder", ForecastViewHolder.create(parent).toString())
        return ForecastViewHolder.create(parent)
    }

    override fun onBindViewHolder(holder: ForecastViewHolder, position: Int) {
        holder.bind(getItem(position), position)
    }

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<ForecastItem>() {
            override fun areItemsTheSame(oldItem: ForecastItem, newItem: ForecastItem): Boolean {
                return oldItem.dt == newItem.dt
            }

            override fun areContentsTheSame(oldItem: ForecastItem, newItem: ForecastItem): Boolean {
                return oldItem == newItem
            }
        }
    }
}