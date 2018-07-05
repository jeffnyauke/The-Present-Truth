package com.piestack.adventelegraph.ui.bible

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

import com.piestack.adventelegraph.di.qualifiers.BibleDataScope
import com.piestack.adventelegraph.repository.LocalRepository
import com.piestack.adventelegraph.util.SchedulerProvider

import javax.inject.Inject

@BibleDataScope
class BibleActivityViewModelFactory @Inject
constructor() : ViewModelProvider.Factory {

    @Inject
    lateinit var localRepository: LocalRepository

    @Inject
    lateinit var schedulerProvider: SchedulerProvider

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(BibleActivityViewModel::class.java)) {
            return BibleActivityViewModel(localRepository, schedulerProvider) as T
        }
        throw IllegalArgumentException("Wrong ViewModel class")
    }
}
