package com.kerolosatya.dailyforecast.ui.home

import android.os.Bundle
import android.util.Log
import android.view.MotionEvent
import android.view.View
import android.widget.ArrayAdapter
import androidx.activity.enableEdgeToEdge
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.kerolosatya.dailyforecast.R
import com.kerolosatya.dailyforecast.data.db.pref.SharedPreferenceHelper
import com.kerolosatya.dailyforecast.data.model.cities.CityModel
import com.kerolosatya.dailyforecast.data.model.weather.CachedForecastModel
import com.kerolosatya.dailyforecast.data.model.weather.ForecastModel
import com.kerolosatya.dailyforecast.data.model.weather.ResponseForecastModel
import com.kerolosatya.dailyforecast.data.util.Common
import com.kerolosatya.dailyforecast.data.util.Resource
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
    private var city: CityModel = CityModel()

    @Inject
    lateinit var forecastViewModel: ForecastViewModel
    @Inject
    lateinit var citiesViewModel: CitiesViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        enableEdgeToEdge()

        init()
        onClick()
    }

    private fun init() {
        observeCities()
        observeForecast(city.lat, city.lon)
    }

    private fun onClick() {
        binding.txtCity.setOnItemClickListener { _, _, position, _ ->
            city = citiesList[position]
            forecastViewModel.getForecast(city.lat, city.lon)
            showProgressDialog(binding.progressLoading)
        }
        binding.swipeRefresh.setOnRefreshListener {
            citiesViewModel.getCities()
            binding.swipeRefresh.isRefreshing = false
        }
    }

    private fun setupAutoCompleteCityAdapter() {
        val adapter =
            ArrayAdapter(baseContext, android.R.layout.simple_list_item_1, getCitiesList())
        binding.txtCity.setAdapter(adapter)
        city = citiesList[0]
        val cityName = "${city.cityNameEn} - ${city.cityNameAr}"
        binding.txtCity.setText(cityName, false)
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

    private fun observeForecast(lat: String, lon: String) {
        forecastViewModel.getForecast(lat, lon).observe(this) { resources ->
            showProgressDialog(binding.progressLoading)
            when (resources) {
                is Resource.Loading -> {
                    Log.d(Common.KeroDebug, "Forecast Api LOADING")
                    showProgressDialog(binding.progressLoading)
                }

                is Resource.Success -> {
                    hideProgressDialog(binding.progressLoading)
                    binding.txtNoData.visibility = View.GONE
                    forecastList.clear()
                    forecastList.addAll(resources.data.list)

                    SharedPreferenceHelper.forecastObj = CachedForecastModel(city, forecastList)
                    setupForecast()
                    Log.d(Common.KeroDebug, "Forecast Api DONE: ${forecastList}")
                }

                is Resource.Error -> {
                    hideProgressDialog(binding.progressLoading)
                    onForecastFailed()
                    Log.d(Common.KeroDebug, "Forecast Api ERROR")
                }

                else -> {
                    hideProgressDialog(binding.progressLoading)
                    onForecastFailed()
                    Log.d(Common.KeroDebug, "Forecast Api Nothing")
                }
            }
        }

    }

    private fun observeCities() {
        citiesViewModel.getCities().observe(this) { resources ->
            showProgressDialog(binding.progressLoading)
            when (resources) {
                is Resource.Loading -> {
                    Log.d(Common.KeroDebug, "Cities Api LOADING")
                    showProgressDialog(binding.progressLoading)
                }

                is Resource.Success -> {
                    hideProgressDialog(binding.progressLoading)
                    citiesList.clear()
                    citiesList.addAll(resources.data.cities)

                    setupAutoCompleteCityAdapter()
                    forecastViewModel.getForecast(city.lat, city.lon)
                    showProgressDialog(binding.progressLoading)

                    SharedPreferenceHelper.citiesList = citiesList
                    Log.d(Common.KeroDebug, "Cities Api DONE: ${citiesList}")
                }

                is Resource.Error -> {
                    hideProgressDialog(binding.progressLoading)
                    onCityFailed()

                    Log.d(Common.KeroDebug, "Cities Api ERROR")
                }

                else -> {
                    hideProgressDialog(binding.progressLoading)
                    onCityFailed()
                    Log.d(Common.KeroDebug, "Cities Api Nothing")
                }
            }
        }

    }

    private fun onForecastFailed() {
        val obj = SharedPreferenceHelper.forecastObj?:CachedForecastModel()
        if (obj != CachedForecastModel() && obj.city == city) {
            forecastList.clear()
            forecastList.addAll(obj.list)
            setupForecast()
            binding.txtNoData.visibility = View.GONE

            showSnackReTry("Warning!!\nThere is issue to get Forecast from ${city.cityNameEn}\n" +
                    "This data not accurate data")
        } else {
            forecastList.clear()
            setupForecast()
            binding.txtNoData.visibility = View.VISIBLE

            showSnackReTry("There is no data for ${city.cityNameEn} now")
        }
    }

    private fun onCityFailed() {
        citiesList.clear()
        citiesList.addAll(SharedPreferenceHelper.citiesList?:ArrayList())

        setupAutoCompleteCityAdapter()
        forecastViewModel.getForecast(city.lat, city.lon)
        showProgressDialog(binding.progressLoading)
    }

    private fun showSnackReTry(message: String) {
        val snackbar = Snackbar.make(
            binding.root,
            message,
            Snackbar.LENGTH_LONG
        )
        snackbar.setAction("Retry", MyRetryListener())
        snackbar.setActionTextColor(getColor(R.color.white))
        snackbar.setTextMaxLines(5)
        snackbar.setBackgroundTint(getColor(R.color.snack_red))
        snackbar.show()

    }

    inner class MyRetryListener : View.OnClickListener {
        override fun onClick(view: View) {
            citiesViewModel.getCities()
        }
    }

}
