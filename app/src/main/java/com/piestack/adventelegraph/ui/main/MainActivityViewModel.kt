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

package com.piestack.adventelegraph.ui.main

import com.piestack.adventelegraph.util.prefs.Prefs
import com.piestack.adventelegraph.ui.base.ScopedViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject


class MainActivityViewModel @Inject constructor(private val prefs: Prefs) : ScopedViewModel() {

    fun checkFirstStart() {
        launch {
            withContext(Dispatchers.Main) {
                val isFirstStart = prefs.isFirstStart()

                //if the activity has never started before
                if (isFirstStart) {

                    //Launch app intro
                    /*val i = Intent(this@MainActivity, IntroActivity::class.java)
                    startActivity(i)*/

                    //Make a new preference editor
                    prefs.setFirstStart(false)
                }
            }
        }
    }

}