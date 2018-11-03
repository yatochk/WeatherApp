package com.yatochk.weather.view.cities

import android.app.AlertDialog
import android.app.Dialog
import android.app.DialogFragment
import android.os.Bundle
import android.view.View
import com.yatochk.weather.R
import com.yatochk.weather.dagger.App
import com.yatochk.weather.presenter.DialogPresenter

class AddCityDialog : DialogFragment() {
    private lateinit var presenter: DialogPresenter

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val builder = AlertDialog.Builder(this.activity)
        val dialogView = activity.layoutInflater.inflate(R.layout.add_city, null)
        val presenter = App.component.getDialogPresenter()

        builder.setPositiveButton("Set") { _, _ ->

        }
        builder.setNegativeButton("Cancel") { _, _ ->
            dialog.cancel()
        }
        builder.setView(dialogView)
        return builder.create()
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


    }
}