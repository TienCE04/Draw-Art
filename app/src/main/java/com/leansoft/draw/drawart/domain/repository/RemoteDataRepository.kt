package com.leansoft.draw.drawart.domain.repository

import com.leansoft.draw.drawart.data.source.remote.dto.CategoriesDto
import com.leansoft.draw.drawart.domain.model.CategoryGroupModel
import com.leansoft.draw.drawart.domain.model.FrameModel
import com.leansoft.draw.drawart.utils.Either
import com.leansoft.draw.drawart.utils.Failure

interface RemoteDataRepository {
    suspend fun getCategoryData(): Either<Failure, List<CategoryGroupModel>>

    suspend fun getListFrameTemp(): Either<Failure, List<FrameModel>>
}