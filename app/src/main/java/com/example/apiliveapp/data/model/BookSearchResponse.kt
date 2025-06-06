package com.example.apiliveapp.data.model

data class BookSearchResponse(
    val docs: List<Book>,
    val num_found: Int
)