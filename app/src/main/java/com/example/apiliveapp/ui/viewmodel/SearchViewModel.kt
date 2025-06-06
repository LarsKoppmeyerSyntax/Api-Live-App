package com.example.apiliveapp.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.apiliveapp.data.api.OpenLibraryApi
import com.example.apiliveapp.data.model.Book
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

enum class ApiState {
    LOADING, IDLE, ERROR
}

@OptIn(FlowPreview::class)
class SearchViewModel : ViewModel() {

    private val _searchTerm = MutableStateFlow("")
    val searchTerm = _searchTerm.asStateFlow()

    private val _totalItemCount = MutableStateFlow(0)
    val totalItemCount = _totalItemCount.asStateFlow()

    private val _searchResults = MutableStateFlow<List<Book>>(emptyList())
    val searchResults: StateFlow<List<Book>> = _searchResults.asStateFlow()

    private val _apiState: MutableStateFlow<ApiState> = MutableStateFlow(ApiState.IDLE)
    val apiState = _apiState.asStateFlow()

    private val _errorMessage: MutableStateFlow<String> = MutableStateFlow("")
    val errorMessage = _errorMessage.asStateFlow()

    init {
        viewModelScope.launch {
            searchTerm
                .debounce(500)
                .distinctUntilChanged()
                .collect { term ->
                    _searchResults.value = emptyList()
                    _totalItemCount.value = 0
                    search()
                }
        }
    }

    fun updateSearchTerm(searchTerm: String) {
        _searchTerm.value = searchTerm
    }

    fun search() {
        val term = _searchTerm.value
        val offset = _searchResults.value.size

        viewModelScope.launch {
            _apiState.value = ApiState.LOADING
            try {
                val response = OpenLibraryApi.apiService.searchBooks(
                    searchTerm = term,
                    offset = offset
                )
                if (response.isSuccessful) {
                    val body = response.body()!!
                    _totalItemCount.value = body.num_found
                    _searchResults.value += body.docs
                    _apiState.value = ApiState.IDLE
                } else {
                    _errorMessage.value = "Fehler: ${response.code()}"
                    _apiState.value = ApiState.ERROR
                }
            } catch (ex: Exception) {
                _errorMessage.value = ex.toString()
                _apiState.value = ApiState.ERROR
            }
        }
    }
}
