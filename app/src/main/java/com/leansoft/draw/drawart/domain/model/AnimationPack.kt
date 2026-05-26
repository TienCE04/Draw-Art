package com.leansoft.draw.drawart.domain.model

data class AnimationPack(
    val level: String,
    val category: String,
    val animation: List<AnimationDetail>
)

data class AnimationDetail(
    val nameAnim:String,
    val listFrame: List<String>
){

}