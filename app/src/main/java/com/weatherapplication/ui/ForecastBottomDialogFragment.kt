package com.weatherapplication.ui

import android.content.DialogInterface
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.weatherapplication.R
import com.weatherapplication.data.model.forecast.ForecastItem
import com.weatherapplication.databinding.FragmentForecastBinding

class ForecastBottomDialogFragment : BottomSheetDialogFragment() {
    private lateinit var forecastAdapter: ForecastAdapter
    private var _binding: FragmentForecastBinding? = null
    private val binding get() = _binding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentForecastBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpRecyclerView()

        var data: List<ForecastItem>? = null
        data = if (Build.VERSION.SDK_INT < Build.VERSION_CODES.TIRAMISU){
            arguments?.getParcelableArrayList<ForecastItem>("forecast_list") as ArrayList<ForecastItem>
        } else {
            arguments?.getParcelableArrayList("forecast_list", ForecastItem::class.java)
        }
        forecastAdapter.submitList(data)
    }

    private fun setUpRecyclerView() {
        binding?.forecastList?.apply {
            forecastAdapter = ForecastAdapter()
            adapter = forecastAdapter
            layoutManager = LinearLayoutManager(requireContext())
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}