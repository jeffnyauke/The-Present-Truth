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

package com.piestack.adventelegraph.ui.hymnal

import com.piestack.adventelegraph.ui.base.RxViewModel
import com.piestack.adventelegraph.util.SchedulerProvider
import javax.inject.Inject

class HymnalActivityViewModel @Inject constructor(private val schedulerProvider: SchedulerProvider) : RxViewModel() {
    override fun subscribe() {
    }
    /*fun showDataFromApi(): Single<IpAddress> = repository.getDataFromApi()
            .compose(schedulerProvider.getSchedulersForSingle())*/
}