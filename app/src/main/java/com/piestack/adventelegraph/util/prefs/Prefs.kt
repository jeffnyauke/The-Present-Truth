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

package com.piestack.adventelegraph.util.prefs

interface Prefs {

    fun getLanguage(): String

    fun setLanguage(language: String)

    fun getLastHymnNumber(): Int

    fun setLastHymnNumber(number: Int)

    fun isNightMode(): Boolean

    fun setNightMode(isNight: Boolean)

    fun isFirstStart(): Boolean

    fun setFirstStart(isFirst: Boolean)
}