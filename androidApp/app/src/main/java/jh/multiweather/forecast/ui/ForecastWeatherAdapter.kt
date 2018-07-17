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
import kotlinx.android.synthetic.main.forecast__forecast_list_header.view.*
import kotlinx.android.synthetic.main.forecast__forecast_list_item.view.*

internal class ForecastWeatherAdapter(
        private val context: Context
) : RecyclerView.Adapter<ForecastWeatherAdapter.ViewHolder>() {

    private enum class ViewType { ITEM, HEADER }

    internal sealed class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        internal class ItemViewHolder(itemView: View) : ViewHolder(itemView) {
            val icon: ImageView = itemView.icon
            val date: TextView = itemView.date
            val description: TextView = itemView.description
            val temperatureMin: TextView = itemView.temperatureMin
            val temperatureMax: TextView = itemView.temperatureMax
        }

        internal class HeaderViewHolder(itemView: View) : ViewHolder(itemView) {
            val header: TextView = itemView.header
        }
    }

    var items: List<ForecastWeatherFormatted> = listOf()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
            when (viewType) {
                ViewType.ITEM.ordinal -> {
                    LayoutInflater.from(context).inflate(R.layout.forecast__forecast_list_item, parent, false)
                            .let { ViewHolder.ItemViewHolder(it) }
                }
                ViewType.HEADER.ordinal -> {
                    LayoutInflater.from(context).inflate(R.layout.forecast__forecast_list_header, parent, false)
                            .let { ViewHolder.HeaderViewHolder(it) }
                }
                else -> throw IllegalArgumentException("Unsupported view type: $viewType")
            }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        with(items[position]) {
            when (this) {
                is ForecastWeatherFormatted.Item -> {
                    holder as ViewHolder.ItemViewHolder
                    holder.icon.setImageResource(descriptionIconMap.getOrDefault(descriptionIcon, R.drawable.ic_unknown))
                    holder.date.text = timestamp
                    holder.description.text = descriptionText
                    holder.temperatureMin.text = temperatureMin
                    holder.temperatureMax.text = temperatureMax
                }
                is ForecastWeatherFormatted.Header -> {
                    holder as ViewHolder.HeaderViewHolder
                    holder.header.text = this.header
                }
            }
        }
    }

    override fun getItemViewType(position: Int) = when (items[position]) {
        is ForecastWeatherFormatted.Item -> ViewType.ITEM.ordinal
        is ForecastWeatherFormatted.Header -> ViewType.HEADER.ordinal
    }

    override fun getItemCount() = items.size
}