package com.kerolosatya.dailyforecast.ui.home

import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import androidx.activity.enableEdgeToEdge
import androidx.recyclerview.widget.LinearLayoutManager
import com.kerolosatya.dailyforecast.data.model.cities.CityModel
import com.kerolosatya.dailyforecast.data.model.weather.ForecastModel
import com.kerolosatya.dailyforecast.data.util.Common
import com.kerolosatya.dailyforecast.databinding.ActivityHomeBinding
import com.kerolosatya.dailyforecast.ui.base.BaseActivity
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class HomeActivity : BaseActivity() {
    private lateinit var binding: ActivityHomeBinding
    private var citiesList: MutableList<CityModel> = ArrayList()
    private var forecastList: MutableList<ForecastModel> = ArrayList()
    private lateinit var forecastAdapter: ForecastAdapter
    private var city = ""
    private var lat = ""
    private var lon = ""

    @Inject
    lateinit var forecastViewModel: ForecastViewModel
    @Inject
    lateinit var citiesViewModel: CitiesViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        init()
        onClick()
    }

    private fun init() {
        enableEdgeToEdge()

        observeForecast()
        observeCities()
    }

    private fun onClick() {
        binding.txtCity.setOnItemClickListener { _, _, position, _ ->
            val item = binding.txtCity.adapter.getItem(position).toString()
            city = item
            lat = citiesList[position].lat
            lon = citiesList[position].lon
            forecastViewModel.getForecast(lat, lon)
            showProgressDialog(binding.progressLoading)
        }
    }

    private fun setupAutoCompleteCityAdapter() {
        val adapter =
            ArrayAdapter(baseContext, android.R.layout.simple_list_item_1, getCitiesList())
        binding.txtCity.setAdapter(adapter)
        city = "${citiesList[0].cityNameEn} - ${citiesList[0].cityNameAr}"
        binding.txtCity.setText(city, false)
        lat = citiesList[0].lat
        lon = citiesList[0].lon
    }

    private fun setupForecast() {
        val layoutManager =
            LinearLayoutManager(baseContext, LinearLayoutManager.VERTICAL, false)
        forecastAdapter = ForecastAdapter()

        forecastAdapter.setData(forecastList)
        binding.recycler.layoutManager = layoutManager
        binding.recycler.adapter = forecastAdapter
    }

    private fun getCitiesList(): List<String> {
        val list: MutableList<String> = ArrayList()
        citiesList.forEach { item ->
            list.add("${item.cityNameEn} - ${item.cityNameAr}")
        }
        return list
    }

    private fun observeForecast() {
        forecastViewModel.forecast.observe(this) {
            showProgressDialog(binding.progressLoading)
            when (forecastViewModel.statusForecast.value) {
                ForecastApiStatus.LOADING -> {
                    Log.d(Common.KeroDebug, "Forecast Api LOADING")
                    showProgressDialog(binding.progressLoading)
                }

                ForecastApiStatus.DONE -> {
                    hideProgressDialog(binding.progressLoading)
                    forecastList.clear()
                    forecastList.addAll(it.list)

                    setupForecast()
                    Log.d(Common.KeroDebug, "Forecast Api DONE: ${forecastList}")
                }

                ForecastApiStatus.ERROR -> {
                    hideProgressDialog(binding.progressLoading)
                    Log.d(Common.KeroDebug, "Forecast Api ERROR")
                }

                else -> {
                    Log.d(Common.KeroDebug, "Forecast Api Nothing")
                }
            }
        }

    }

    private fun observeCities() {
        citiesViewModel.cities.observe(this) {
            showProgressDialog(binding.progressLoading)
            when (citiesViewModel.statusCities.value) {
                CitiesApiStatus.LOADING -> {
                    Log.d(Common.KeroDebug, "Cities Api LOADING")
                    showProgressDialog(binding.progressLoading)
                }

                CitiesApiStatus.DONE -> {
                    hideProgressDialog(binding.progressLoading)
                    citiesList.clear()
                    citiesList.addAll(it.cities)
                    setupAutoCompleteCityAdapter()
                    forecastViewModel.getForecast(lat, lon)
                    showProgressDialog(binding.progressLoading)

                    Log.d(Common.KeroDebug, "Cities Api DONE: ${citiesList}")
                }

                CitiesApiStatus.ERROR -> {
                    hideProgressDialog(binding.progressLoading)
                    Log.d(Common.KeroDebug, "Cities Api ERROR")
                }

                else -> {
                    Log.d(Common.KeroDebug, "Cities Api Nothing")
                }
            }
        }

    }

}