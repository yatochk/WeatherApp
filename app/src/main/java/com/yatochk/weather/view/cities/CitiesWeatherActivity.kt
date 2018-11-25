package com.yatochk.weather.view.cities

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.helper.ItemTouchHelper
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import com.yatochk.weather.R
import com.yatochk.weather.dagger.App
import com.yatochk.weather.model.database.CityWeather
import com.yatochk.weather.presenter.MainPresenter
import com.yatochk.weather.view.detailedweather.DetailedWeatherActivity
import com.yatochk.weather.view.settings.SettingsActivity
import kotlinx.android.synthetic.main.activity_main.*

class CitiesWeatherActivity : AppCompatActivity(), CitiesView {
    private lateinit var presenter: MainPresenter

    private lateinit var recyclerAdapter: CitiesRecyclerViewAdapter
    private val cities = ArrayList<CityWeather>()

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.tool_bar_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.settings_item -> presenter.settingsClick()
        }

        return true
    }

    override fun openSettings() {
        val intent = Intent(this, SettingsActivity::class.java)
        startActivity(intent)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(tool_bar)

        presenter = App.component.mainPresenter

        recyclerAdapter = CitiesRecyclerViewAdapter(cities)
        recyclerAdapter.setOnItemClickListener {
            presenter.clickCity()
        }

        with(cities_recycler) {
            itemAnimator = DefaultItemAnimator()
            addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))
            adapter = recyclerAdapter
            layoutManager = LinearLayoutManager(this@CitiesWeatherActivity)
        }

        val itemTouchHelperCallback = RecyclerItemTouchHelper(
            0,
            ItemTouchHelper.LEFT,
            object : RecyclerItemTouchHelper.RecyclerItemTouchHelperListener {
                override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int, position: Int) {
                    if (viewHolder is CitiesRecyclerViewAdapter.CityViewHolder) {
                        val name = cities[viewHolder.adapterPosition].city
                        val deletedItem = cities[viewHolder.adapterPosition]
                        val deletedIndex = viewHolder.adapterPosition
                        val deletedCityId = cities[deletedIndex].id

                        recyclerAdapter.removeItem(viewHolder.adapterPosition)

                        val snackBar = Snackbar
                            .make(main_layout, "$name ${getString(R.string.title_delete_snack)}", Snackbar.LENGTH_LONG)
                        snackBar.setAction(getString(R.string.action_delete_snack)) {
                            recyclerAdapter.restoreItem(deletedItem, deletedIndex)
                        }
                        snackBar.setActionTextColor(Color.YELLOW)
                        snackBar.addCallback(object : Snackbar.Callback() {
                            override fun onDismissed(transientBottomBar: Snackbar?, event: Int) {
                                super.onDismissed(transientBottomBar, event)
                                if (event != Snackbar.Callback.DISMISS_EVENT_ACTION)
                                    presenter.swipeDeleteCity(deletedCityId)
                            }
                        })
                        snackBar.show()
                    }
                }
            })

        ItemTouchHelper(itemTouchHelperCallback).attachToRecyclerView(cities_recycler)

        refresh.setOnRefreshListener {
            presenter.updateWeatherSwipe(cities)
        }

        add_city_button.setOnClickListener {
            presenter.clickAddCity()
        }
    }

    override fun showMessage(msg: String) {
        Toast.makeText(this, msg, Toast.LENGTH_LONG).show()
    }

    override fun stopUpdateAnim() {
        refresh.isRefreshing = false
    }

    override fun openLocationDialog() {
        val dialog = AddCityDialog()
        dialog.show(supportFragmentManager, "AddCity")
        dialog.setOnCancelListener {
            presenter.closeDialog()
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
        this.cities.clear()
        this.cities.addAll(cities)



        /*
        Если я не ошибаюсь этот метод вообще бесполезен. Т.к в исходниках он вообще ничего не делает:
        public void onChanged() {
            // Do nothing
        }*/
        recyclerAdapter.notifyDataSetChanged()
    }

    override fun openWeatherActivity() {
        val intent = Intent(this, DetailedWeatherActivity::class.java)
        startActivity(intent)
    }
}
