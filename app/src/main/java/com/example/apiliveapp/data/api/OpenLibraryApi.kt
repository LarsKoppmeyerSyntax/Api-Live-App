package com.example.apiliveapp.data.api

import com.example.apiliveapp.data.model.BookSearchResponse
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

//Endpunkt: https://openlibrary.org/search.json

const val BASE_URL = "https://openlibrary.org/"

val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()

val retrofit = Retrofit.Builder()
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .baseUrl(BASE_URL)
    .build()

interface OpenLibraryApiService {

    @GET("search.json")
    suspend fun searchBooks(@Query("q") searchTerm: String) : BookSearchResponse

}

object OpenLibraryApi{
    val apiService : OpenLibraryApiService by lazy { retrofit.create(OpenLibraryApiService::class.java) }
}
