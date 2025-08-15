package com.mahnoosh.network.data.di

import android.content.Context
import coil.ImageLoader
import coil.decode.SvgDecoder
import coil.util.DebugLogger
import com.mahnoosh.network.BuildConfig
import com.mahnoosh.network.data.ApiService
import com.mahnoosh.network.data.remotedatasource.DefaultHeadlineRemoteDatasource
import com.mahnoosh.network.data.remotedatasource.HeadlineRemoteDatasource
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.Call
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class NetworkModule {

    @Binds
    @Singleton
    abstract fun bindHeadlineRemoteDatasource(
        headlineRemoteDatasource: DefaultHeadlineRemoteDatasource
    ): HeadlineRemoteDatasource

    companion object {
        @Provides
        @Singleton
        fun provideHttpLoggingInterceptor(): HttpLoggingInterceptor {
            return HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY
            }
        }

        @Provides
        @Singleton
        fun provideOkHttpClient(loggingInterceptor: HttpLoggingInterceptor): OkHttpClient {
            return OkHttpClient.Builder()
                .addInterceptor(loggingInterceptor)
                .connectTimeout(30, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .writeTimeout(30, TimeUnit.SECONDS)
                .build()
        }

        @Provides
        @Singleton
        fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
            return Retrofit.Builder()
                .baseUrl(BuildConfig.BASE_URL) // Use BuildConfig for the base URL
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        }

        @Provides
        @Singleton
        fun provideApiService(retrofit: Retrofit): ApiService {
            return retrofit.create(ApiService::class.java)
        }

        @Provides
        @Singleton
        fun imageLoader(
            // We specifically request dagger.Lazy here, so that it's not instantiated from Dagger.
            okHttpCallFactory: dagger.Lazy<Call.Factory>,
            @ApplicationContext application: Context,
        ): ImageLoader {
            return ImageLoader.Builder(application)
                .callFactory { okHttpCallFactory.get() }
                .components { add(SvgDecoder.Factory()) }
                // Assume most content images are versioned urls
                // but some problematic images are fetching each time
                .respectCacheHeaders(false)
                .apply {
                    if (BuildConfig.DEBUG) {
                        logger(DebugLogger())
                    }
                }
                .build()
        }
    }
}