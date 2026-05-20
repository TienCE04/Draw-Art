package com.leansoft.draw.drawart.data.source.remote

import com.google.firebase.Firebase
import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FirebaseMgr @Inject constructor(

) {

    val remoteConfig by lazy{
        FirebaseRemoteConfig.getInstance()
    }
}