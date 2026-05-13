package com.leansoft.draw.drawart.data.source.remote.dto

data class AnimationDto(
    val id: String,
    val name: String,
    val thumbnail: String,
    val urlGif: String,
    val level: String,
    val numberFrame: Int,
    val listFrame: List<FrameDto>?
) {
}