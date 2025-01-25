package com.cmapp.network

import com.cmapp.model.domain.unsplash.UnsplashData
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.http.GET
import retrofit2.http.Query

private const val BASE_URL = "https://api.unsplash.com/"
private val ACCESS_KEY = System.getenv("UNSPLASH_ACCESS_KEY")?.toString() ?: throw IllegalArgumentException("Invalid Unsplash Access Ley")

val client = OkHttpClient.Builder()
    .addInterceptor { chain ->
        val request = chain.request().newBuilder()
            .addHeader("Accept-Version", "v1")
            //.addHeader("Authorization", "Client-ID $ACCESS_KEY")
            .build()
        chain.proceed(request)
    }
    .build()
private val json = Json {
    ignoreUnknownKeys = true
}
private val unsplashRetrofit = Retrofit.Builder()
    .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
    .baseUrl(BASE_URL)
    .client(client)
    .build()

/**
 * Retrofit service object for creating api calls
 */
interface UnsplashApiService {
    // we need to download the image otherwise they will block the API key

    // profile pictures
    @GET("/search/photos")
    suspend fun getProfileImages(
        @Query("client_id") apiKey: String = ACCESS_KEY,
        @Query("query") query: String = "Harry+Potter",
    ): UnsplashData
}

object UnsplashApi {
    val retrofitService: UnsplashApiService by lazy {
        unsplashRetrofit.create(UnsplashApiService::class.java)
    }
}