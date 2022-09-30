package com.weatherapplication.di

import dagger.Component

@Component(modules = [NetworkModule::class, ViewModelModule::class])
interface AppComponent {
}