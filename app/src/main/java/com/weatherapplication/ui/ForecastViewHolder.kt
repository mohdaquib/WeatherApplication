package com.weatherapplication.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.weatherapplication.data.model.forecast.ForecastItem
import com.weatherapplication.databinding.LayoutForecastItemBinding
import com.weatherapplication.utils.DateTimeUtils

class ForecastViewHolder(private val binding: LayoutForecastItemBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(item: ForecastItem, position: Int) {
        binding.dayView.text = DateTimeUtils.nextDay(position + 1)
        binding.temperature.text = "${item.temp.day} C"
    }

    companion object {
        fun create(parent: ViewGroup): ForecastViewHolder {
            val forecastListItemBinding = LayoutForecastItemBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
            return ForecastViewHolder(forecastListItemBinding)
        }
    }
}