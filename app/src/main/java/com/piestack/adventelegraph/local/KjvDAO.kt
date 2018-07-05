package com.piestack.adventelegraph.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query
import com.piestack.adventelegraph.models.room.CountResult
import com.piestack.adventelegraph.models.room.kjv
import io.reactivex.Flowable

@Dao
interface KjvDAO {

    @Query("SELECT * from kjv")
    fun getAll(): Flowable<List<kjv>>

    @Query("SELECT * from kjv WHERE id = :id")
    fun getVerse(id: Int): Flowable<kjv>

    @Query("SELECT * from kjv WHERE b = :book AND c = :chapter")
    fun getChapter(chapter: Int, book: Int): Flowable<List<kjv>>

    @Query("SELECT * from kjv WHERE id BETWEEN :start AND :end")
    fun getRange(start: Int, end: Int): Flowable<List<kjv>>

    @Query("SELECT COUNT(*) AS 'result' from Books WHERE BookID =:book")
    fun getNoOfChapters(book: Int): Flowable<CountResult>

    @Query("SELECT COUNT(*) AS 'result' from kjv WHERE b = :book AND c =:chapter")
    fun getNoOfVerses(chapter: Int, book: Int): Flowable<CountResult>

    @Query("SELECT * from kjv WHERE t LIKE :query")
    fun search(query: String): Flowable<List<kjv>>

    @Insert(onConflict = REPLACE)
    fun insert(bible: ArrayList<kjv>)

    @Query("DELETE from kjv")
    fun deleteAll()

}