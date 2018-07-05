package com.piestack.adventelegraph.ui.hymnal.di

import com.piestack.adventelegraph.ui.hymnal.HymnalActivityViewModel
import com.piestack.adventelegraph.util.SchedulerProvider
import dagger.Module
import dagger.Provides

@Module
class HymnalActivityModule {

    @Provides
    fun provideViewModel(schedulerProvider: SchedulerProvider) = HymnalActivityViewModel(schedulerProvider)
}