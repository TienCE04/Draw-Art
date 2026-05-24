package com.leansoft.draw.drawart.domain.model

data class ProjectModel(
    val idProject: Int,
    val isMovie: Boolean = false,
    val nameMovie: String = "",
    val thumbNail: String = "",
    val listFrame: List<FrameModel> = emptyList()
) {
}

