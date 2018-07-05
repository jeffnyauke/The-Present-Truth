package com.piestack.adventelegraph.models.room

/**
 * Created by Jeffrey Nyauke on 7/19/2018.
 * Piestack.
 */
data class VerseRef(var book: Int = 1, var bookNameFull: String = "Genesis", var bookNameShort: String = "Gen", var chapter: Int = 0, var verse: Int = 1)