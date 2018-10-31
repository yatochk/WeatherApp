package com.yatochk.weather.presenter

import com.yatochk.weather.view.View

interface Presenter {
    fun attachView(view: View)
}