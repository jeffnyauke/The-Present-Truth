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

package com.piestack.adventelegraph.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query
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

    @Query("SELECT COUNT(*) from Books WHERE BookID =:book")
    fun getNoOfChapters(book: Int): Flowable<Int>

    @Query("SELECT COUNT(*) from kjv WHERE b = :book AND c =:chapter")
    fun getNoOfVerses(chapter: Int, book: Int): Flowable<Int>

    @Query("SELECT * from kjv WHERE t LIKE :query")
    fun search(query: String): Flowable<List<kjv>>

    @Insert(onConflict = REPLACE)
    fun insert(bible: ArrayList<kjv>)

    @Query("DELETE from kjv")
    fun deleteAll()

}