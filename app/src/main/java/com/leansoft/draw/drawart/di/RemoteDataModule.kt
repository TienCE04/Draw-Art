package com.leansoft.draw.drawart.di

import com.leansoft.draw.drawart.data.repository.RemoteDataRepositoryImpl
import com.leansoft.draw.drawart.domain.repository.RemoteDataRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RemoteDataModule {
    @Binds
    @Singleton
    abstract fun bindRemoteDataRepository(
        remoteDataRepositoryImpl: RemoteDataRepositoryImpl
    ): RemoteDataRepository
}