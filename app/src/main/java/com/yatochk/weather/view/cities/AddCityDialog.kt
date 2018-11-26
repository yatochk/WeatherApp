package com.yatochk.weather.view.cities

import android.app.AlertDialog
import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.view.View
import android.widget.Toast
import com.yatochk.weather.R
import com.yatochk.weather.dagger.App
import com.yatochk.weather.presenter.DialogPresenter
import kotlinx.android.synthetic.main.add_city.view.*

class AddCityDialog : DialogFragment(), AddCityView {
    private lateinit var presenter: DialogPresenter
    private lateinit var dialogView: View
    private var cityName: String? = null
    private var onAddCityListener: DialogPresenter.OnAddCityListener? = null
    fun setOnCancelListener(listener: DialogPresenter.OnAddCityListener) {
        onAddCityListener = listener
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val builder = AlertDialog.Builder(activity)
        dialogView = activity!!.layoutInflater.inflate(R.layout.add_city, null)
        presenter = App.component.dialogPresenter
        presenter.attachDialog(this, onAddCityListener)

        builder.setPositiveButton("Set") { _, _ ->
            if (cityName != null)
                presenter.setClick(cityName!!)
        }
        builder.setNegativeButton("Cancel", null)
        builder.setView(dialogView)
        return builder.create()
    }

    override fun setCity(cityName: String) {
        this.cityName = cityName
        dialogView.add_city_name.text = cityName
    }

    override fun onDismiss(dialog: DialogInterface?) {
        super.onDismiss(dialog)
        presenter.detachDialog()
    }

    override fun showMessage(msg: String) {
        Toast.makeText(activity, msg, Toast.LENGTH_LONG).show()
    }
}