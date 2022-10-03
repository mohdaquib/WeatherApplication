package com.weatherapplication.di

import dagger.Module

@Module(
    includes = [ViewModelModule::class,
        NetworkModule::class]
)
class AppModule