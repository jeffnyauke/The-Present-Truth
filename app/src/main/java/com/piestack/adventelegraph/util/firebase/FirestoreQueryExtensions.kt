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

import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.QuerySnapshot
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.ReceiveChannel
import kotlinx.coroutines.channels.sendBlocking
import kotlinx.coroutines.suspendCancellableCoroutine
import java.util.*
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException


/**
 * Created by Jeffrey Nyauke on 27,December,2018.
 * Piestack company.
 * Nairobi, KE.
 * hello@piestack.co.ke
 */
suspend fun <T : Any> Query.await(clazz: Class<T>): List<T> {
    return await { documentSnapshot -> documentSnapshot.toObject(clazz) as T }
}

suspend fun <T : Any> Query.awaitSingle(clazz: Class<T>): T {
    return awaitSingle { documentSnapshot -> documentSnapshot.toObject(clazz) as T }
}

suspend fun Query.await(): QuerySnapshot {
    return suspendCancellableCoroutine { continuation ->
        get().addOnCompleteListener {
            if (it.isSuccessful && it.result != null) {
                continuation.resume(it.result!!)
            } else if (it.exception != null){
                continuation.resumeWithException(it.exception!!)
            } else {
                continuation.resumeWithException(EmptyStackException())
            }
        }

        continuation.invokeOnCancellation {
            if (continuation.isCancelled)
                try {
                    //NonCancellable.cancel()
                } catch (ex: Throwable) {
                    //Ignore cancel exception
                }
        }
    }
}

suspend fun <T : Any> Query.await(parser: (documentSnapshot: DocumentSnapshot) -> T): List<T> {
    return suspendCancellableCoroutine { continuation ->
        get().addOnCompleteListener { task ->
            if (task.isSuccessful && task.result != null) {
                val list = arrayListOf<T>()
                task.result?.forEach { list.add(parser.invoke(it)) }

                continuation.resume(list)
            } else if (task.exception != null){
                continuation.resumeWithException(task.exception!!)
            } else {
                continuation.resumeWithException(EmptyStackException())
            }
        }

        continuation.invokeOnCancellation {
            if (continuation.isCancelled)
                try {
                    //NonCancellable.cancel()
                } catch (ex: Throwable) {
                    //Ignore cancel exception
                }
        }
    }
}

suspend fun <T : Any> Query.awaitSingle(parser: (documentSnapshot: DocumentSnapshot) -> T): T {
    return suspendCancellableCoroutine { continuation ->
        get().addOnCompleteListener {
            if (it.isSuccessful && it.result != null) {
                continuation.resume(parser.invoke(((it.result) as QuerySnapshot).documents[0]))
            } else if (it.exception != null){
                continuation.resumeWithException(it.exception!!)
            } else {
                continuation.resumeWithException(EmptyStackException())
            }
        }

        continuation.invokeOnCancellation {
            if (continuation.isCancelled)
                try {
                    //NonCancellable.cancel()
                } catch (ex: Throwable) {
                    //Ignore cancel exception
                }
        }
    }
}

fun <T : Any> Query.observe(clazz: Class<T>): ReceiveChannel<List<T>> = observe { documentSnapshot ->
    documentSnapshot.toObject(clazz) as T
}

fun <T : Any> Query.observe(parser: (documentSnapshot: DocumentSnapshot) -> T): ReceiveChannel<List<T>> {
    val channel = Channel<List<T>>()

    addSnapshotListener { querySnapshot, exception ->
        exception?.let {
            channel.close(it)
            return@addSnapshotListener
        }
        if (querySnapshot == null) {
            channel.close()
            return@addSnapshotListener
        }

        val list = arrayListOf<T>()
        querySnapshot.forEach {
            list.add(parser.invoke(it))
        }
        channel.sendBlocking(list)
    }

    return channel
}