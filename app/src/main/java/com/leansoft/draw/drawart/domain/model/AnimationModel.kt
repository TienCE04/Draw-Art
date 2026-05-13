package com.leansoft.draw.drawart.domain.model

data class AnimationModel(
    val idAnim: String,
    val nameAnim: String,
    val thumbnail: String,
    val urlGif: String,
    val level: String,
    val numberFrame: Int,
    val listFrame: List<FrameModel> = emptyList()
) {
}