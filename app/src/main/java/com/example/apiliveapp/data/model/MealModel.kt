package com.example.apiliveapp.data.model


//Für jede {} brauchen wir eine Datenklasse
//Jede [] steht für eine List/Array

data class MealResponse(
    val meals: List<Meal> = emptyList()
)


data class Meal(
    val idMeal : Int,
    val strMeal : String = "",
)