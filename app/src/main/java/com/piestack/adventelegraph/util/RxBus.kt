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

package com.piestack.adventelegraph.util

import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject
import timber.log.Timber

class RxBus private constructor() {

    private val bus = PublishSubject.create<Any>()

    fun send(event: Any) {
        if (hasObservers()) { // No point in sending this event if nobody's subscribed.
            Timber.d("Firing RxBus event: %s", event.toString())
            bus.onNext(event)
        }
    }

    fun <T> toObservable(type: Class<T>): Observable<T> {
        return bus.filter(type::isInstance).cast(type)
    }

    fun hasObservers(): Boolean {
        return bus.hasObservers()
    }

    companion object {
        private var instance: RxBus? = null

        private val lock = Any()

        fun getInstance(): RxBus {
            synchronized(lock) {
                if (instance == null) {
                    instance = RxBus()
                }

                return instance as RxBus
            }
        }
    }
}