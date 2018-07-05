package com.piestack.adventelegraph.local

import androidx.room.Dao
import androidx.room.Query
import com.piestack.adventelegraph.models.room.Book
import io.reactivex.Flowable

@Dao
interface BooksDAO {

    @Query("SELECT * from Books")
    fun getBooks(): Flowable<List<Book>>
}