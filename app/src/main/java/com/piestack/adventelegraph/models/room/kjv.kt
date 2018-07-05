package com.piestack.adventelegraph.models.room

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "kjv")
data class kjv(@PrimaryKey(autoGenerate = false) var id: Int?,
               @ColumnInfo(name = "b") var b: Int,
               @ColumnInfo(name = "c") var c: Int,
               @ColumnInfo(name = "v") var v: Int,
               @ColumnInfo(name = "t") var t: String

) {
    constructor() : this(0, 0, 0, 0, "")
}
