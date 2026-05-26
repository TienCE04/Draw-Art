package com.leansoft.draw.drawart.data.source.local.pref

import android.content.Context
import com.leansoft.draw.drawart.utils.Utils.PREF_NAME
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PreferenceHelper @Inject constructor(
    @ApplicationContext context: Context
) : Preferences(context, PREF_NAME) {

    var animList by stringSetPref("anim_list", emptySet())

    fun checkAnimName(name: String): Boolean {
        if (name.isBlank()) return false
        return animList.contains(name)
    }

    fun addAnim(name: String) {
        val updated = animList.toMutableSet()
        updated.add(name)
        animList = updated
    }

    fun removeAnim(name: String) {
        val updated = animList.toMutableSet()
        updated.remove(name)
        animList = updated
    }
}