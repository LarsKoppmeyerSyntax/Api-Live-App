package com.example.apiliveapp.data.api

import com.example.apiliveapp.data.model.BookSearchResponse
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

//Endpunkt: https://openlibrary.org/search.json

const val BASE_URL = "https://openlibrary.org/"

val loggingInterceptor = HttpLoggingInterceptor().apply {
    level = HttpLoggingInterceptor.Level.HEADERS
}

val client = OkHttpClient.Builder()
    .addInterceptor(loggingInterceptor)
    .build()

val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()

val retrofit = Retrofit.Builder()
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .baseUrl(BASE_URL)
    .client(client)
    .build()

interface OpenLibraryApiService {

    @GET("search.json")
    suspend fun searchBooks(@Query("q") searchTerm: String) : Response<BookSearchResponse>

}

object OpenLibraryApi{
    val apiService : OpenLibraryApiService by lazy { retrofit.create(OpenLibraryApiService::class.java) }
}
