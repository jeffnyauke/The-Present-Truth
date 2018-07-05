package com.piestack.adventelegraph.local

import androidx.room.RoomDatabase
import com.piestack.adventelegraph.models.room.Book
import com.piestack.adventelegraph.models.room.kjv

@androidx.room.Database(entities = [(kjv::class), (Book::class)], version = 1)
abstract class MyDatabase : RoomDatabase() {
    abstract fun kjvDAO(): KjvDAO
    abstract fun booksDAO(): BooksDAO
}
