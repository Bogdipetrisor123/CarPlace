package com.carplace.di

import com.carplace.data.repository.ChatGptApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit

@Module
@InstallIn(SingletonComponent::class)
class TaskModule {
    @Provides
    fun provideTaskApi(retrofit: Retrofit): ChatGptApi =
        retrofit.create(ChatGptApi::class.java)
}