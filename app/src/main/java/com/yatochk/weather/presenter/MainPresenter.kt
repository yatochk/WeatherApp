package com.yatochk.weather.presenter

import com.yatochk.weather.model.Model
import com.yatochk.weather.view.View

class MainPresenter(model: Model): Presenter{
    lateinit var view: View

    override fun attachView(view: View) {
        this.view = view
    }


}