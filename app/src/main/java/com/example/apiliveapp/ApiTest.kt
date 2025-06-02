package com.example.apiliveapp

import com.example.apiliveapp.data.api.OpenLibraryApi
import kotlinx.coroutines.runBlocking


fun main() = runBlocking {

    println("Starte API Test")

    val daten = OpenLibraryApi.apiService.searchBooks("Herr der Ringe")

    println("Daten: $daten")
}

