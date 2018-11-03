package com.yatochk.weather.view.cities

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import com.yatochk.weather.R
import com.yatochk.weather.dagger.App
import com.yatochk.weather.model.database.CityWeather
import com.yatochk.weather.presenter.MainPresenter
import kotlinx.android.synthetic.main.activity_main.*

class CitiesWeatherActivity : AppCompatActivity(), CitiesView {
    override val context = this
    lateinit var presenter: MainPresenter

    private lateinit var recyclerAdapter: CitiesRecyclerViewAdapter
    private val cities = ArrayList<CityWeather>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        presenter = App.component.getMainPresenter()
        recyclerAdapter = CitiesRecyclerViewAdapter(cities)
        recyclerAdapter.setOnItemClickListener {

        }
        val layoutManager = LinearLayoutManager(this)
        with(cities_recycler) {
            itemAnimator = DefaultItemAnimator()
            addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))
            adapter = recyclerAdapter
            this.layoutManager = layoutManager
        }
    }

    override fun onResume() {
        super.onResume()
        presenter.attachView(this)
    }

    override fun onPause() {
        super.onPause()
        presenter.detachView()
    }

    override fun updateCitiesRecycler(cities: ArrayList<CityWeather>) {
        for (city in cities)
            this.cities.add(city)

        recyclerAdapter.notifyDataSetChanged()
    }
}
