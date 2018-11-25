package com.yatochk.weather.view.cities

import android.app.AlertDialog
import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.view.View
import com.yatochk.weather.R
import com.yatochk.weather.dagger.App
import com.yatochk.weather.presenter.DialogPresenter
import kotlinx.android.synthetic.main.add_city.view.*

class AddCityDialog : DialogFragment(), AddCityView {
    private lateinit var presenter: DialogPresenter
    private lateinit var dialogView: View
    private var cityName: String? = null
    private var onAddCityListener: (() -> Unit)? = null
    fun setOnCancelListener(listener: () -> Unit) {
        onAddCityListener = listener
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        dialogView = activity!!.layoutInflater.inflate(R.layout.add_city, null)
        presenter = App.component.dialogPresenter
        presenter.attachDialog(this, onAddCityListener)

        return AlertDialog.Builder(activity)
            .setPositiveButton("Set") { _, _ ->
                if (cityName != null)
                    presenter.setClick(cityName!!)
            }
            .setNegativeButton("Cancel", null)
            .setView(dialogView)
            .create()
    }

    override fun setCity(cityName: String) {
        this.cityName = cityName
        dialogView.add_city_name.text = cityName
    }

    override fun onDismiss(dialog: DialogInterface?) {
        super.onDismiss(dialog)
        presenter.detachDialog()
    }
}