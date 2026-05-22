package com.leansoft.draw.drawart.data.source.local.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "my_project")
data class ProjectEntity(
    @PrimaryKey(autoGenerate = true) var id: Int = 0,
    val nameProject: String,
    val thumbNailFirstFrame: String,
    val createdAt: Long,
    val updatedAt: Long
)

//load project lay anh dau tien trong list
//ve lan dau tien tao luon project, sau do add cac image edit