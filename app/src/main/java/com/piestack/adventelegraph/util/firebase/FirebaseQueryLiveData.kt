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

package com.piestack.adventelegraph.util.firebase

import android.os.Handler
import androidx.annotation.Nullable
import androidx.lifecycle.MutableLiveData
import com.google.firebase.firestore.*
import timber.log.Timber

class FirebaseQueryLiveData(private val query: Query) : MutableLiveData<QuerySnapshot>() {
    private val listener = MyValueEventListener()
    private var listenerRegistration: ListenerRegistration? = null

    private var listenerRemovePending = false
    private val handler = Handler()

    private val removeListener = Runnable {
        listenerRegistration!!.remove()
        listenerRemovePending = false
    }

    override fun onActive() {
        super.onActive()

        Timber.d("onActive")
        if (listenerRemovePending) {
            handler.removeCallbacks(removeListener)
        } else {
            listenerRegistration = query.addSnapshotListener(listener)
        }
        listenerRemovePending = false
    }

    override fun onInactive() {
        super.onInactive()

        Timber.d("onInactive: ")
        // Listener removal is schedule on a two second delay
        handler.postDelayed(removeListener, 2000)
        listenerRemovePending = true
    }

    private inner class MyValueEventListener : EventListener<QuerySnapshot> {
        override fun onEvent(@Nullable querySnapshot: QuerySnapshot?, @Nullable e: FirebaseFirestoreException?) {
            if (e != null) {
                Timber.e(e, "Can't listen to query snapshots: %s", querySnapshot)
                return
            }
            value = querySnapshot
        }
    }
}