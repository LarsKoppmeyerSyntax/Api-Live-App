package com.example.apiliveapp.ui.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.apiliveapp.data.api.OpenLibraryApi
import com.example.apiliveapp.data.model.Book
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

enum class ApiState {
    LOADING, SUCCESS, ERROR
}

class SearchViewModel : ViewModel() {

    private val _searchTerm = MutableStateFlow("")
    val searchTerm = _searchTerm.asStateFlow()

    private val _searchResults : MutableStateFlow<List<Book>> = MutableStateFlow(emptyList())
    val searchResult = _searchResults.asStateFlow()

    private val _apiState : MutableStateFlow<ApiState> = MutableStateFlow(ApiState.SUCCESS)
    val apiState = _apiState.asStateFlow()

    fun search(newSearchTerm : String) {
        _searchTerm.value = newSearchTerm

        //Vor dem API Call
        _apiState.value = ApiState.LOADING

        viewModelScope.launch {

            try {
                val result = OpenLibraryApi.apiService.searchBooks(newSearchTerm)

                //Daten "auspacken"
                _searchResults.value = result.docs

                //Nachdem der API Call erfolgreich ausgeführt
                _apiState.value = ApiState.SUCCESS
            } catch (ex: Exception) {
                Log.e("SearchViewModel", ex.toString())
                _apiState.value = ApiState.ERROR
            }
        }
    }
}