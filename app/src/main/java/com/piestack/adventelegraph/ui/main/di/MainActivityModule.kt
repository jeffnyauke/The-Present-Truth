package com.piestack.adventelegraph.ui.main.di

import com.piestack.adventelegraph.ui.main.MainActivityViewModel
import com.piestack.adventelegraph.util.SchedulerProvider
import dagger.Module
import dagger.Provides

@Module
class MainActivityModule {

    @Provides
    fun provideViewModel(schedulerProvider: SchedulerProvider) = MainActivityViewModel(schedulerProvider)
}