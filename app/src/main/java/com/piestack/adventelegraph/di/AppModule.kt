/*
 * Copyright (c) 2018. Jeffrey Nyauke.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.piestack.adventelegraph.di

import android.content.Context
import android.content.res.Resources
import android.view.LayoutInflater
import androidx.room.Room
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreSettings
import com.google.gson.FieldNamingPolicy
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.piestack.adventelegraph.app.MyApplication
import com.piestack.adventelegraph.local.BooksDAO
import com.piestack.adventelegraph.local.KjvDAO
import com.piestack.adventelegraph.local.MyDatabase
import com.piestack.adventelegraph.local.sqlAsset.AssetSQLiteOpenHelperFactory
import com.piestack.adventelegraph.repository.LocalRepository
import com.piestack.adventelegraph.repository.LocalRepositoryImpl
import com.piestack.adventelegraph.repository.remote.FirebaseRepository
import com.piestack.adventelegraph.repository.remote.FirebaseRepositoryImpl
import com.piestack.adventelegraph.util.SchedulerProvider
import com.tinashe.christInSong.utils.prefs.Prefs
import com.tinashe.christInSong.utils.prefs.PrefsImpl
import dagger.Module
import dagger.Provides
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.Executor
import java.util.concurrent.Executors
import javax.inject.Named
import javax.inject.Singleton


@Module
internal class AppModule {

    @Provides
    @Singleton
    fun provideContext(app: MyApplication): Context = app

    @Provides
    @Singleton
    fun provideFireStoreDatabase(): FirebaseFirestore {
        val store = FirebaseFirestore.getInstance()
        val settings = FirebaseFirestoreSettings.Builder()
                .setPersistenceEnabled(false)
                .setTimestampsInSnapshotsEnabled(true)
                .build()
        store.firestoreSettings = settings

        return store
    }

    @Provides
    @Singleton
    fun providePrefs(context: Context): Prefs = PrefsImpl(context)

    @Provides
    @Singleton
    fun provideResources(context: Context): Resources = context.resources

    @Provides
    @Singleton
    fun provideLayoutInflater(context: Context): LayoutInflater {
        return LayoutInflater.from(context)
    }

    @Provides
    @Singleton
    fun provideSchedulerProvider() = SchedulerProvider(Schedulers.io(), AndroidSchedulers.mainThread())

    @Provides
    @Singleton
    fun getExecutor(): Executor {
        return Executors.newFixedThreadPool(2)
    }

    @Provides
    @Singleton
    fun getKjvDAO(database: MyDatabase): KjvDAO {
        return database.kjvDAO()
    }

    @Provides
    @Singleton
    fun getBooksDAO(database: MyDatabase): BooksDAO {
        return database.booksDAO()
    }

    @Provides
    @Singleton
    fun provideGson(): Gson {
        return GsonBuilder().setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
                .create()
    }

    @Provides
    @Singleton
    fun getMyDatabase(context: Context): MyDatabase {
        return Room.databaseBuilder(context,
                MyDatabase::class.java, "advent.db")
                .openHelperFactory(AssetSQLiteOpenHelperFactory())
                .allowMainThreadQueries()
                .build()
    }

    @Provides
    @Singleton
    fun getLocalRepo(kjvDAO: KjvDAO, booksDAO: BooksDAO, exec: Executor): LocalRepository {
        return LocalRepositoryImpl(kjvDAO, booksDAO, exec)
    }

    @Provides
    @Singleton
    fun getFirebaseRepo(firestore: FirebaseFirestore): FirebaseRepository {
        return FirebaseRepositoryImpl(firestore)
    }

    @Provides
    @Singleton
    @Named("activity")
    fun getCompositeDisposable(): CompositeDisposable {
        return CompositeDisposable()
    }

    @Provides
    @Singleton
    @Named("vm")
    fun getVMCompositeDisposable(): CompositeDisposable {
        return CompositeDisposable()
    }

}