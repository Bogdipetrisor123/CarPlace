package com.carplace.di

import com.carplace.data.repository.AuthRepository
import com.carplace.data.repository.AuthRepositoryImpl
import com.carplace.data.repository.CarsRepository
import com.carplace.data.repository.CarsRepositoryImpl
import com.carplace.data.repository.SearchRepository
import com.carplace.data.repository.SearchRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface DataModule {

    @Binds
    fun bindsHomeRepository(
        homeRepository: CarsRepositoryImpl
    ): CarsRepository

    @Binds
    fun bindsSearchRepository(
        searchRepository: SearchRepositoryImpl
    ): SearchRepository

    @Binds
    fun bindAuthRepository(
        authRepository: AuthRepositoryImpl
    ): AuthRepository
}