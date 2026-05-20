package com.leansoft.draw.drawart.data.repository

import com.leansoft.draw.drawart.data.source.remote.dto.CategoriesDto
import com.leansoft.draw.drawart.domain.repository.RemoteDataRepository
import javax.inject.Inject

class RemoteDataRepositoryImpl @Inject constructor(): RemoteDataRepository {
    override suspend fun getCategoryData(): List<CategoriesDto> {
        TODO("Not yet implemented")
    }
}