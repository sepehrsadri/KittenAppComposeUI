package com.sadri.network

import com.sadri.network.datasource.KittenDataSource
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {
  @Provides
  @Singleton
  @Named("withoutToken")
  fun provideOkHttpClientWithoutToken(): OkHttpClient {
    return okHttpClientBuilder.build()
  }

  @Provides
  @Singleton
  fun provideOkHttpClient(): OkHttpClient {
    return okHttpClientBuilder.build()
  }

  @Provides
  @Singleton
  fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
    return getRetrofit(okHttpClient)
  }

  @Provides
  @Singleton
  fun provideKittenDataSource(retrofit: Retrofit): KittenDataSource {
    return provideService(retrofit = retrofit, KittenDataSource::class.java)
  }

  private val okHttpClientBuilder by lazy {
    val httpClient = OkHttpClient.Builder()
      .readTimeout(25_000, TimeUnit.MILLISECONDS)
      .writeTimeout(25_000, TimeUnit.MILLISECONDS)
      .connectTimeout(25_000, TimeUnit.MILLISECONDS)

    getLoggingInterceptor().let { loggerInterceptor ->
      httpClient.addInterceptor(loggerInterceptor)
    }
  }

  private const val API_BASE_URL = "https://api.thecatapi.com/v1/"

  private fun getLoggingInterceptor(): Interceptor {
    val logger = HttpLoggingInterceptor()
    logger.level = HttpLoggingInterceptor.Level.BODY

    return logger
  }

  private fun getRetrofit(okHttpClient: OkHttpClient): Retrofit {
    val builder = Retrofit.Builder()
      .baseUrl(
        API_BASE_URL
      ).addConverterFactory(
        GsonConverterFactory.create()
      )
      .client(
        okHttpClient
      )

    return builder.build()
  }

  private fun <T> provideService(
    retrofit: Retrofit,
    clazz: Class<T>
  ): T {
    return retrofit.create(clazz)
  }
}
