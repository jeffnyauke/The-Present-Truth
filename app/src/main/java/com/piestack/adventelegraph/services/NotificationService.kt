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

package com.piestack.adventelegraph.services

import android.annotation.SuppressLint
import android.util.Log

import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import timber.log.Timber

class NotificationService : FirebaseMessagingService() {

    @SuppressLint("LogNotTimber")
    override fun onMessageReceived(remoteMessage: RemoteMessage?) {
        super.onMessageReceived(remoteMessage)

        //Only for the foreground background handles automatically

        for ((key, value) in remoteMessage!!.data) {
            Log.e("Test", "Key = $key, Value = $value")
        }
        Timber.e("collapsekey: %s", remoteMessage.collapseKey!!)
        Timber.e("from: %s", remoteMessage.from!!)
        Timber.e("message id: %s", remoteMessage.messageId!!)
        Timber.e("message type:: %s", remoteMessage.messageType!!)
        Timber.e("to: %s", remoteMessage.to!!)
        Timber.e("send time: %s", remoteMessage.sentTime)
        Timber.e("ttl: %s", remoteMessage.ttl)
        Timber.e("title: %s", remoteMessage.notification!!.title!!)
        Timber.e("body: %s", remoteMessage.notification!!.body!!)
        Timber.e("click action: %s", remoteMessage.notification!!.clickAction!!)
        Timber.e("color: %s", remoteMessage.notification!!.color!!)
        Timber.e("icon: %s", remoteMessage.notification!!.icon!!)


        /*NotificationCompat.Builder builder = new NotificationCompat.Builder(this);
        builder.setContentTitle(remoteMessage.getNotification().getTitle());
        builder.setSmallIcon(R.mipmap.ic_launcher);
        builder.setContentText(remoteMessage.getNotification().getBody());
        NotificationManagerCompat.from(this).notify(0,builder.build());*/
    }


}