/*
 * Copyright (c) 2019. Jeffrey Nyauke.
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

package com.piestack.adventelegraph.util

import android.text.format.DateUtils
import java.util.*

/**
 * Created by jinn on 4/3/17.
 */

object TimeUtils {
    fun relativeTime(date: Date): CharSequence {
        val xx = DateUtils.getRelativeTimeSpanString(date.time,  System.currentTimeMillis(), DateUtils.SECOND_IN_MILLIS)
        return xx.toString()
    }
}
