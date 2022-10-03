package com.weatherapplication.di

import android.app.Application
import com.weatherapplication.ui.WeatherFragment
import dagger.BindsInstance
import dagger.Component

@AppScoped
@Component(modules = [AppModule::class])
interface AppComponent {
    fun inject(weatherFragment: WeatherFragment)

    @Component.Factory
    interface Factory {
        fun create(@BindsInstance application: Application): AppComponent
    }
}