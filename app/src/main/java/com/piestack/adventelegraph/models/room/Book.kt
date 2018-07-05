package com.piestack.adventelegraph.models.room

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Books")
data class Book(@PrimaryKey(autoGenerate = false) var BookID: Int,
                @ColumnInfo(name = "BookName") var BookName: String,
                @ColumnInfo(name = "NumOfChapters") var NumOfChapters: Int,
                @ColumnInfo(name = "BookDiv") var BookDiv: String,
                @ColumnInfo(name = "ShortName") var ShortName: String,
                @ColumnInfo(name = "OsisName") var OsisName: String

) {
    constructor() : this(0, "", 0, "", "", "")
}
