package com.carplace.di

import com.carplace.data.repository.AuthRepository
import com.carplace.data.repository.AuthRepositoryImpl
import com.carplace.data.repository.CarsRepository
import com.carplace.data.repository.CarsRepositoryImpl
import com.carplace.data.repository.ChatGptRepository
import com.carplace.data.repository.ChatGptRepositoryImpl
import com.carplace.network.NetworkDataSource
import com.carplace.network.retrofit.RetrofitNetwork
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface DataModule {

    @Binds
    fun bindsCarsRepository(
        carsRepository: CarsRepositoryImpl
    ): CarsRepository

    @Binds
    fun bindsAuthRepository(
        authRepository: AuthRepositoryImpl
    ): AuthRepository

    @Binds
    fun bindsChatGptRepository(
        chatGptRepository: ChatGptRepositoryImpl
    ): ChatGptRepository

    @Binds
    fun provideNetworkDataSource(
        retrofitNetwork: RetrofitNetwork
    ): NetworkDataSource
}