package com.piestack.adventelegraph.di.component

import com.piestack.adventelegraph.di.module.AppModule
import com.piestack.adventelegraph.di.module.DatabaseModule
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [(AppModule::class)])
interface AppComponent {
    fun getDatabaseComponent(databaseModule: DatabaseModule): DatabaseComponent
}