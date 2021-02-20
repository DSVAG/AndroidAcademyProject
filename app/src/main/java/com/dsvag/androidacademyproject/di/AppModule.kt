package com.dsvag.androidacademyproject.di

import android.content.Context
import androidx.room.Room
import com.dsvag.androidacademyproject.BuildConfig
import com.dsvag.androidacademyproject.data.local.AppDatabase
import com.dsvag.androidacademyproject.data.remote.ApiMovieService
import com.dsvag.androidacademyproject.data.remote.ApiPersonService
import com.dsvag.androidacademyproject.data.repositories.MovieRepository
import com.dsvag.androidacademyproject.data.repositories.PersonRepository
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    fun provideInterceptor(): Interceptor {
        return Interceptor { chain ->

            val url = chain
                .request()
                .url
                .newBuilder()
                .addQueryParameter("api_key", BuildConfig.API_KEY)
                .build()

            val request = chain
                .request()
                .newBuilder()
                .url(url)
                .build()

            chain.proceed(request)
        }
    }

    @Provides
    fun provideOkHttpClient(requestInterceptor: Interceptor): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(requestInterceptor)
            .addNetworkInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
            .build()
    }

    @Provides
    fun provideMoshi(): Moshi {
        return Moshi.Builder().addLast(KotlinJsonAdapterFactory()).build()
    }

    @Provides
    fun provideRetrofit(moshi: Moshi, okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .client(okHttpClient)
            .build()
    }

    @Provides
    fun provideApiMovieData(retrofit: Retrofit): ApiMovieService {
        return retrofit.create(ApiMovieService::class.java)
    }

    @Provides
    fun provideApiPersonData(retrofit: Retrofit): ApiPersonService {
        return retrofit.create(ApiPersonService::class.java)
    }

    @Provides
    fun provideAppDatabase(@ApplicationContext context: Context): AppDatabase {
        return Room
            .databaseBuilder(context, AppDatabase::class.java, "MoviesAndPersons.db")
            .build()
    }

    @Provides
    fun provideMovieRepository(
        apiMovieService: ApiMovieService,
        appDatabase: AppDatabase,
    ): MovieRepository {
        return MovieRepository(
            apiMovieService,
            appDatabase.movieDao(),
        )
    }

    @Provides
    fun providePersonRepository(
        apiMovieService: ApiMovieService,
        apiPersonService: ApiPersonService,
        appDatabase: AppDatabase,
    ): PersonRepository {
        return PersonRepository(
            apiPersonService,
            apiMovieService,
            appDatabase.personDao(),
            appDatabase.movieDao()
        )
    }
}