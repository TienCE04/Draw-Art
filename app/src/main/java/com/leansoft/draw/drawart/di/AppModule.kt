package com.leansoft.draw.drawart.di

import android.content.Context
import androidx.room.Room
import com.leansoft.draw.drawart.data.source.local.database.AppDatabase
import com.leansoft.draw.drawart.data.source.local.database.dao.MyProjectAnimationDao
import com.leansoft.draw.drawart.data.source.local.pref.PreferenceHelper
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    @Singleton
    fun providePreferenceHelper(@ApplicationContext context: Context): PreferenceHelper =
        PreferenceHelper(context)
    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): AppDatabase =
        Room.databaseBuilder(context, AppDatabase::class.java, "my_collection_db").build()

    @Provides
    fun provideMyCollectionDao(db: AppDatabase): MyProjectAnimationDao = db.myProjectAnimation()

    @Provides
    fun provideContext(@ApplicationContext context: Context): Context = context

}