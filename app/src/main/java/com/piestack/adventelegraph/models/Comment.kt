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

package com.piestack.adventelegraph.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

/**
 * Created by Jeffrey Nyauke on 7/19/2018.
 * Piestack.
 */
@Parcelize
data class Comment(
        var comment_id: String = "",
        var article_id: String = "",
        var user_id: String = "",
        var user_name: String = "",
        var user_dp: String = "",
        var body: String = "",
        var date_created: Number = 0,
        var date_edited: Number = 0) : Parcelable