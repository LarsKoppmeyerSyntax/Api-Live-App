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

    private val _errorMessage : MutableStateFlow<String> = MutableStateFlow("")
    val errorMessage = _errorMessage.asStateFlow()

    fun search(newSearchTerm : String) {
        _searchTerm.value = newSearchTerm

        //Vor dem API Call
        _apiState.value = ApiState.LOADING

        viewModelScope.launch {

            try {
                val response = OpenLibraryApi.apiService.searchBooks(newSearchTerm)


                if(response.isSuccessful) {
                    //Daten "auspacken"
                    _searchResults.value = response.body()!!.docs

                    //Nachdem der API Call erfolgreich ausgeführt
                    _apiState.value = ApiState.SUCCESS

                } else {
                    Log.e("SearchViewModel", "Fehler beim API Call, Status Code: ${response.code()}", )
                    _errorMessage.value = "Fehler beim API Call, Status Code: ${response.code()}"
                    _apiState.value = ApiState.ERROR
                }
            } catch (ex: Exception) {
                Log.e("SearchViewModel", ex.toString())
                _errorMessage.value = ex.toString()
                _apiState.value = ApiState.ERROR
            }
        }
    }
}