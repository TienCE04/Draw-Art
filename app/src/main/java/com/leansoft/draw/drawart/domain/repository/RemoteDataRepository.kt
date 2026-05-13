package com.leansoft.draw.drawart.domain.repository

import com.leansoft.draw.drawart.data.source.remote.dto.CategoriesDto

interface RemoteDataRepository {
    suspend fun getCategoryData(): List<CategoriesDto>
}