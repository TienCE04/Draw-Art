package com.leansoft.draw.drawart.data.mapper

import com.leansoft.draw.drawart.data.source.local.database.entity.FrameImageEntity
import com.leansoft.draw.drawart.data.source.remote.dto.AnimationDto
import com.leansoft.draw.drawart.data.source.remote.dto.CategoriesDto
import com.leansoft.draw.drawart.data.source.remote.dto.FrameDto
import com.leansoft.draw.drawart.domain.model.AnimationModel
import com.leansoft.draw.drawart.domain.model.CategoryGroupModel
import com.leansoft.draw.drawart.domain.model.FrameModel

fun CategoriesDto.toDomain(): CategoryGroupModel {
    val nameCate = this.category
    val list = this.data ?: emptyList()
    return CategoryGroupModel(nameCate, list.map { it.toDomain() })
}

fun AnimationDto.toDomain(): AnimationModel {
    val list = this.listFrame ?: emptyList()
    val id = this.idAnim
    val name = this.name
    val thumbnail = this.thumbnail
    val urlGif = this.urlGif
    val level = this.level
    val numberFrame = this.numberFrame
    return AnimationModel(
        id,
        name,
        thumbnail,
        urlGif,
        level,
        numberFrame,
        list.map { it.toDomain() })
}

fun FrameDto.toDomain(): FrameModel {
    val id = this.id
    val url = this.url
    return FrameModel(
        id, url
    )
}

fun FrameImageEntity.toDomain(): FrameModel {
    return FrameModel(
        id, imagePath
    )
}
