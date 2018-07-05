package com.piestack.adventelegraph.util

import com.google.firebase.analytics.FirebaseAnalytics

class AnalyticsTools(//private final MixpanelAPI mixpanelAPI;
        //private final GoogleTracker googleTracker;
        private val firebaseAnalytics: FirebaseAnalytics)//this.mixpanelAPI = mixpanelAPI;
//this.googleTracker = googleTracker;
{

    fun logScreenLaunch(screenName: String) {

        val eventName = "screenLaunch_$screenName"

        //Log screen launch in your favourite analytics tools

        //mixpanelAPI.trackMap(eventName, new HashMap<>());

        //googleTracker.send(eventName);
        //firebaseAnalytics.logEvent(eventName, ...);

        //...

    }


    fun logButtonClick(screenName: String, buttonLabel: String) {

        val eventName = "click_" + screenName + "_" + buttonLabel

        //Log button click in your favourite analytics tools

        //...

    }


    fun logCustomEvent(screenName: String, eventLabel: String, additionalProperties: Map<String, String>?) {

        val eventName = "custom_" + screenName + "_" + eventLabel

        /*Bundle bundle = new Bundle();
        bundle.putString(FirebaseAnalytics.Param.ITEM_ID, id);
        bundle.putString(FirebaseAnalytics.Param.ITEM_NAME, name);
        bundle.putString(FirebaseAnalytics.Param.CONTENT_TYPE, "image");
        firebaseAnalytics.logEvent(FirebaseAnalytics.Event.SELECT_CONTENT, bundle);*/

    }

}