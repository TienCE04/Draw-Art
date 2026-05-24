package com.leansoft.draw.drawart.data.source.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.leansoft.draw.drawart.data.source.local.database.dao.MyProjectAnimationDao
import com.leansoft.draw.drawart.data.source.local.database.entity.FrameImageEntity
import com.leansoft.draw.drawart.data.source.local.database.entity.ProjectEntity

@Database(entities = [ProjectEntity::class, FrameImageEntity::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun myProjectAnimation(): MyProjectAnimationDao
}