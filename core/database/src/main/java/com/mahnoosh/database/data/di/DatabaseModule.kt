package com.mahnoosh.database.data.di

import android.content.Context
import androidx.room.Room
import com.mahnoosh.database.data.NewsDatabase
import com.mahnoosh.database.data.dao.HeadlineDao
import com.mahnoosh.database.data.localdatasource.DefaultHeadlineLocalDatasource
import com.mahnoosh.database.data.localdatasource.HeadlineLocalDatasource
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton
@Module
@InstallIn(SingletonComponent::class)
internal abstract class DatabaseModule {

    @Binds
    @Singleton
    abstract fun bindHeadlineLocalDatasource(
        headlineLocalDatasource: DefaultHeadlineLocalDatasource
    ): HeadlineLocalDatasource

    companion object {
        @Provides
        @Singleton
        fun providesNiaDatabase(
            @ApplicationContext context: Context,
        ): NewsDatabase = Room.databaseBuilder(
            context,
            NewsDatabase::class.java,
            "news-database",
        ).build()

        @Provides
        fun providesTopicsDao(
            database: NewsDatabase,
        ): HeadlineDao = database.headlineDao()
    }
}