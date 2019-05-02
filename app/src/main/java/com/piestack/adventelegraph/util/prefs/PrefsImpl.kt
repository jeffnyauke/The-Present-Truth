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

import android.content.Context
import com.piestack.adventelegraph.util.prefs.PreferenceHelper.get
import com.piestack.adventelegraph.util.prefs.PreferenceHelper.set

class PrefsImpl constructor(val context: Context) : Prefs {

    private val prefs = PreferenceHelper.defaultPrefs(context)

    override fun getLanguage(): String = prefs[PREF_LANGUAGE, "eng"]

    override fun setLanguage(language: String) {
        prefs[PREF_LANGUAGE] = language
    }

    override fun getLastHymnNumber(): Int = prefs[PREF_LAST_NUMBER, 0]

    override fun setLastHymnNumber(number: Int) {
        prefs[PREF_LAST_NUMBER] = number
    }

    override fun isNightMode(): Boolean = prefs[PREF_NIGHT_MODE, false]

    override fun setNightMode(isNight: Boolean) {
        prefs[PREF_NIGHT_MODE] = isNight
    }

    override fun isFirstStart(): Boolean = prefs[PREF_FIRST_START, true]

    override fun setFirstStart(isFirst: Boolean) {
        prefs[PREF_NIGHT_MODE] = isFirst
    }


    companion object {
        private const val PREF_LANGUAGE = "LANGUAGE"
        private const val PREF_LAST_NUMBER = "LAST_NUMBER"
        private const val PREF_NIGHT_MODE = "NIGHT_MODE"
        private const val PREF_FIRST_START = "FIRST_START"
    }
}