package com.dsvag.androidacademyproject.di

import com.dsvag.androidacademyproject.BuildConfig
import com.dsvag.androidacademyproject.data.remote.ApiMovieService
import com.dsvag.androidacademyproject.data.remote.ApiPersonService
import com.dsvag.androidacademyproject.data.repositories.MovieRepository
import com.dsvag.androidacademyproject.data.repositories.PersonRepository
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideInterceptor(): Interceptor {
        return Interceptor { chain ->

            val url = chain
                .request()
                .url
                .newBuilder()
                .build()

            val request = chain
                .request()
                .newBuilder()
                .url(url)
                .build()

            chain.proceed(request)
        }
    }

    @Singleton
    @Provides
    fun provideOkHttpClient(requestInterceptor: Interceptor): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(requestInterceptor)
            .build()
    }

    @Singleton
    @Provides
    fun provideMoshi(): Moshi {
        return Moshi.Builder().addLast(KotlinJsonAdapterFactory()).build()
    }

    @Singleton
    @Provides
    fun provideRetrofit(moshi: Moshi, okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .client(okHttpClient)
            .build()
    }

    @Singleton
    @Provides
    fun provideApiMovieData(retrofit: Retrofit): ApiMovieService {
        return retrofit.create(ApiMovieService::class.java)
    }

    @Singleton
    @Provides
    fun provideApiPersonData(retrofit: Retrofit): ApiPersonService {
        return retrofit.create(ApiPersonService::class.java)
    }

    @Singleton
    @Provides
    fun provideMovieRepository(apiMovieService: ApiMovieService): MovieRepository {
        return MovieRepository(apiMovieService)
    }

    @Singleton
    @Provides
    fun providePersonRepository(apiPersonService: ApiPersonService): PersonRepository {
        return PersonRepository(apiPersonService)
    }
}