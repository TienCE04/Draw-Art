package com.leansoft.draw.drawart.data.source.local.pref

import android.content.Context
import com.leansoft.draw.drawart.utils.Utils.PREF_NAME
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PreferenceHelper @Inject constructor(
    @ApplicationContext context: Context
): Preferences(context,PREF_NAME){

}