package jh.multiweather.forecast.ui

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import jh.multiplatform.R
import jh.multiweather.forecast.model.ForecastWeatherFormatted
import jh.multiweather.shared.ui.descriptionIconMap
import kotlinx.android.synthetic.main.forecast__forecast_list_item.view.*

internal class ForecastWeatherAdapter(
        private val context: Context
) : RecyclerView.Adapter<ForecastWeatherAdapter.ViewHolder>() {

    internal class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val icon: ImageView = itemView.icon
        val date: TextView = itemView.date
        val description: TextView = itemView.description
        val temperatureMin: TextView = itemView.temperatureMin
        val temperatureMax: TextView = itemView.temperatureMax
    }

    var items: List<ForecastWeatherFormatted> = listOf()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
            LayoutInflater.from(context).inflate(R.layout.forecast__forecast_list_item, parent, false)
                    .let { ViewHolder(it) }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        with(items[position]) {
            holder.icon.setImageResource(descriptionIconMap.getOrDefault(descriptionIcon, R.drawable.ic_unknown))
            holder.date.text = timestamp
            holder.description.text = descriptionText
            holder.temperatureMin.text = temperatureMin
            holder.temperatureMax.text = temperatureMax
        }
    }

    override fun getItemCount() = items.size
}