package com.yatochk.weather.view.cities

import android.app.AlertDialog
import android.app.Dialog
import android.app.DialogFragment
import android.content.DialogInterface
import android.os.Bundle
import android.view.View
import com.yatochk.weather.R
import com.yatochk.weather.dagger.App
import com.yatochk.weather.presenter.DialogPresenter
import kotlinx.android.synthetic.main.add_city.view.*

class AddCityDialog : DialogFragment(), AddCityView {
    private lateinit var presenter: DialogPresenter
    private lateinit var dialogView: View
    private var cityName: String? = null
    private var onCancelListener: (() -> Unit)? = null
    fun setOnCancelListener(listener: () -> Unit) {
        onCancelListener = listener
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val builder = AlertDialog.Builder(activity)
        dialogView = activity.layoutInflater.inflate(R.layout.add_city, null)
        presenter = App.component.getDialogPresenter()
        presenter.attachDialog(this)

        builder.setPositiveButton("Set") { _, _ ->
            if (cityName != null)
                presenter.setClick(cityName!!)
        }
        builder.setNegativeButton("Cancel") { _, _ ->
            presenter.closeClick()
        }
        builder.setView(dialogView)
        return builder.create()
    }

    override fun setCity(cityName: String) {
        this.cityName = cityName
        dialogView.add_city_name.text = cityName
    }

    override fun closeDialog() {
        dialog?.cancel()
    }

    override fun onCancel(dialog: DialogInterface?) {
        super.onCancel(dialog)
        presenter.detachDialog()
        onCancelListener?.invoke()
    }
}