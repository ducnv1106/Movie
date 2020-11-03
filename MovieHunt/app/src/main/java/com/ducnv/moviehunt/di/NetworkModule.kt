package com.ducnv.moviehunt.di

import android.content.Context
import com.ducnv.moviehunt.constants.Constants
import com.ducnv.moviehunt.data.remote.ApiService
import okhttp3.Cache
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Protocol
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.core.qualifier.named
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

val networkModule = module {
    single { createOkHttpCache(get()) }
    single{ createHttpLoggingInterceptor() }
    single{ ApiInterceptor()}
    single{ createOkHttpClient(get(),get()) }
    single{ createAppRetrofit(get()) }
    single (named("api")){ createApiService(get())}
}
const val TIMEOUT = 10

fun createOkHttpCache(context: Context): Cache =
    Cache(context.cacheDir, (10 * 1024 * 1024).toLong())

fun createHttpLoggingInterceptor(): HttpLoggingInterceptor =
    HttpLoggingInterceptor().apply { level = HttpLoggingInterceptor.Level.BODY }

fun createOkHttpClient(
    loggingInterceptor: HttpLoggingInterceptor,
    apiInterceptor: Interceptor
): OkHttpClient =
    OkHttpClient.Builder()
        .writeTimeout(TIMEOUT.toLong(), TimeUnit.SECONDS)
        .connectTimeout(TIMEOUT.toLong(), TimeUnit.SECONDS)
        .readTimeout(TIMEOUT.toLong(), TimeUnit.SECONDS)
        .addInterceptor(apiInterceptor)
        .addInterceptor(loggingInterceptor)
        .build()

fun createAppRetrofit(okHttpClient: OkHttpClient): Retrofit = Retrofit.Builder()
    .baseUrl(Constants.API_ROOT)
    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
    .addConverterFactory(GsonConverterFactory.create())
    .client(okHttpClient)
    .build()


fun ApiInterceptor(): Interceptor = Interceptor {
    val request = it.request()
    val url =
        request.url().newBuilder().addQueryParameter("api_key", Constants.TMDB_API_KEY).build()
  val  newRequest = request.newBuilder().url(url).build()
    it.proceed(newRequest)

}

fun createApiService(retrofit: Retrofit): ApiService =
    retrofit.create<ApiService>(ApiService::class.java)
//
//class Mock(val isMock: Boolean)
