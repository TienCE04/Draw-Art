package com.leansoft.draw.drawart.data.source.local.database.entity

import androidx.room.Embedded
import androidx.room.Relation

data class ProjectWithImages(
    @Embedded
    val project: ProjectEntity,
    @Relation(
        parentColumn = "id",
        entityColumn = "projectId"
    )
    val images: List<FrameImageEntity>
)

//lay de query 1 lan lay luon cac frame image, chưa sử dụng đến