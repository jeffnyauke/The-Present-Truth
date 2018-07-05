package com.piestack.adventelegraph.di.module

import android.content.Context
import androidx.room.Room
import com.piestack.adventelegraph.di.qualifiers.BibleDataScope
import com.piestack.adventelegraph.local.BooksDAO
import com.piestack.adventelegraph.local.KjvDAO
import com.piestack.adventelegraph.local.MyDatabase
import com.piestack.adventelegraph.local.sqlAsset.AssetSQLiteOpenHelperFactory
import com.piestack.adventelegraph.repository.LocalRepository
import com.piestack.adventelegraph.repository.LocalRepositoryImpl
import dagger.Module
import dagger.Provides
import java.util.concurrent.Executor

@Module
class DatabaseModule constructor(private var context: Context) {

    @Provides
    @BibleDataScope
    fun getKjvDAO(database: MyDatabase): KjvDAO {
        return database.kjvDAO()
    }

    @Provides
    @BibleDataScope
    fun getBooksDAO(database: MyDatabase): BooksDAO {
        return database.booksDAO()
    }

    @BibleDataScope
    @Provides
    fun getMyDatabase(): MyDatabase {
        return Room.databaseBuilder(context,
                MyDatabase::class.java, "advent.db")
                .openHelperFactory(AssetSQLiteOpenHelperFactory())
                .allowMainThreadQueries()
                .build()
    }

    @Provides
    @BibleDataScope
    fun getLocalRepo(kjvDAO: KjvDAO, booksDAO: BooksDAO, exec: Executor): LocalRepository {
        return LocalRepositoryImpl(kjvDAO, booksDAO, exec)
    }

}