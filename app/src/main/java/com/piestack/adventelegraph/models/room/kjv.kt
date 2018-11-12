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
