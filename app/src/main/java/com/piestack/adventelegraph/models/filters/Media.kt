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

package com.piestack.adventelegraph.models.filters

import com.piestack.adventelegraph.app.Config

/**
 * Created by Jeffrey Nyauke on 7/19/2018.
 * Piestack.
 */
data class Media(var author: String = "", var category: String = "", var published: Boolean = true, var sort: String = "published_date", var limit: Long = Config.FETCH_LIMIT.toLong())