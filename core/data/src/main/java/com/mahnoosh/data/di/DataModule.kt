package com.mahnoosh.data.di

import com.mahnoosh.data.repository.DefaultHeadlineRepository
import com.mahnoosh.data.repository.HeadlineRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal abstract class DataModule {

    @Binds
    @Singleton
    internal abstract fun bindsHeadlineRepository(
        headlineRepository: DefaultHeadlineRepository,
    ): HeadlineRepository
}