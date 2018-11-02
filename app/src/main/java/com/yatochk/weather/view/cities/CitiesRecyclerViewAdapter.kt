package com.yatochk.weather.view.cities

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.yatochk.weather.R
import com.yatochk.weather.model.database.CityWeather
import kotlinx.android.synthetic.main.city_item.view.*

class CitiesRecyclerViewAdapter(private val cities: ArrayList<CityWeather>) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private var onItemClickListener: ((CityWeather) -> Unit)? = null
    fun setOnItemClickListener(onClick: ((translates: CityWeather) -> Unit)) {
        onItemClickListener = onClick
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder =
        CityViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.city_item,
                parent,
                false
            )
        )

    override fun getItemCount(): Int = cities.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val mHolder = holder as CityViewHolder
        val itemView = mHolder.itemView

        val city = CityWeather(
            cities[position].id,
            cities[position].city,
            cities[position].temperature
        )

        with(itemView) {
            setOnClickListener {
                onItemClickListener?.invoke(city)
            }

            city_name.text = cities[position].city
            city_temp.text = cities[position].temperature
        }

    }

    class CityViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
}