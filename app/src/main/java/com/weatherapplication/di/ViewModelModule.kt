package com.weatherapplication.di

import android.app.Application
import android.content.Context
import android.location.Geocoder
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.weatherapplication.location.CoLocation
import com.weatherapplication.location.LocationViewModel
import com.weatherapplication.ui.WeatherViewModel
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoMap
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import java.util.*

@Module
abstract class ViewModelModule {
    @Binds
    @AppScoped
    internal abstract fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory

    @Binds
    @IntoMap
    @ViewModelKey(WeatherViewModel::class)
    internal abstract fun weatherViewModel(weatherViewModel: WeatherViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(LocationViewModel::class)
    internal abstract fun locationViewModel(locationViewModel: LocationViewModel): ViewModel

    @Binds
    internal abstract fun context(appInstance: Application): Context

    companion object {
        @Provides
        internal fun coLocationImpl(context: Context): CoLocation {
            return CoLocation.from(context)
        }

        @Provides
        internal fun providesGeocoder(context: Context): Geocoder =
            Geocoder(context, Locale.getDefault())

        @Provides
        internal fun providesIODispatcher(): CoroutineDispatcher = Dispatchers.IO
    }
}