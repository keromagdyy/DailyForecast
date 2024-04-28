package com.kerolosatya.dailyforecast.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.kerolosatya.dailyforecast.data.model.weather.ForecastModel
import com.kerolosatya.dailyforecast.data.util.ConstantLinks
import com.kerolosatya.dailyforecast.databinding.LayoutForecastBinding
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class ForecastAdapter :
    RecyclerView.Adapter<ForecastAdapter.ViewHolder>() {

    private var restsList: MutableList<ForecastModel> = ArrayList()

    fun setData(cart: MutableList<ForecastModel>) {
        restsList = cart
        notifyDataSetChanged()
    }

    inner class ViewHolder(private val binding: LayoutForecastBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: ForecastModel) {

            binding.imgTemp.load("${ConstantLinks.IMAGE_URL}${item.weather[0].icon}@2x.png")

            binding.txtTemp.text = "Temperature : ${kelvinToCelsius(item.main.temp)}°C"
            binding.txtMain.text = item.weather[0].main
            binding.txtMinTemp.text = "Min Temperature : ${kelvinToCelsius(item.main.tempMin)}°C"
            binding.txtMaxTemp.text = "Max Temperature : ${kelvinToCelsius(item.main.tempMax)}°C"
            binding.txtHumidity.text = "Humidity : ${item.main.humidity}%"
            binding.txtPressure.text = "Pressure : ${item.main.pressure} mb"
            binding.txtDate.text = (item.dt * 1000).toDateString()
        }
    }

    fun kelvinToCelsius(kelvin: Double): String {
        val celsius = kelvin - 273.15
        return String.format("%.2f", celsius)
    }

    fun Long.toDateString(format: String = "yyyy-MM-dd HH:mm"): String {
        val sdf = SimpleDateFormat(format, Locale.getDefault())
        return sdf.format(Date(this))
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = LayoutForecastBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        restsList?.get(position)?.let { holder.bind(it) }
    }

    override fun getItemCount(): Int {
        return restsList.size
    }
}