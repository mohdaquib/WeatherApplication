package com.weatherapplication

import android.app.Application
import android.content.Context
import com.weatherapplication.di.AppComponent
import com.weatherapplication.di.DaggerAppComponent

class MyApp : Application() {
    lateinit var appComponent: AppComponent

    override fun onCreate() {
        super.onCreate()
        appComponent = DaggerAppComponent.factory().create(this)
    }
}

val Context.appComp: AppComponent
    get() = (applicationContext as MyApp).appComponent