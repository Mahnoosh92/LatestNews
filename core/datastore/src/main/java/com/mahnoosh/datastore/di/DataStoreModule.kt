package com.mahnoosh.datastore.di

import android.content.Context
import androidx.datastore.core.DataStore
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton
import androidx.datastore.preferences.preferencesDataStore
import androidx.datastore.preferences.core.Preferences
import com.mahnoosh.datastore.repository.UserPreferenceRepository

private val Context.dataStoreInstance: DataStore<Preferences> by preferencesDataStore(name = "app_preferences")

@Module
@InstallIn(SingletonComponent::class) // This module lives as long as the application
object DataStoreModule {

    @Singleton
    @Provides
    fun providePreferencesDataStore(@ApplicationContext context: Context): DataStore<Preferences> {
        return context.dataStoreInstance
    }

    @Singleton // Ensures only one instance of the repository
    @Provides
    fun provideAppPreferencesRepository(dataStore: DataStore<Preferences>): UserPreferenceRepository {
        return UserPreferenceRepository(dataStore)
    }
}