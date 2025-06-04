package com.example.apiliveapp.ui.screen

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.content.MediaType.Companion.Text
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.apiliveapp.ui.component.BookItem
import com.example.apiliveapp.ui.viewmodel.ApiState
import com.example.apiliveapp.ui.viewmodel.SearchViewModel

@Composable
fun SearchScreen(
    viewModel: SearchViewModel = viewModel(),
    modifier: Modifier = Modifier
) {

    val searchFieldValue by viewModel.searchTerm.collectAsState()
    val searchResult by viewModel.searchResult.collectAsState()
    val apiState by viewModel.apiState.collectAsState()
    val errorMessage by viewModel.errorMessage.collectAsState()


    Column(
        modifier = modifier.fillMaxSize()
    ) {
        OutlinedTextField(
            modifier = modifier
                .fillMaxWidth()
                .padding(4.dp),
            value = searchFieldValue,
            onValueChange = {
                viewModel.search(it)
            }
        )

        when (apiState) {
            ApiState.SUCCESS -> {
                LazyColumn(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    items(searchResult) { book ->
                        BookItem(
                            modifier = Modifier.padding(4.dp),
                            book = book
                        )
                    }
                }
            }
            ApiState.LOADING -> {
                CircularProgressIndicator(
                    modifier = Modifier.fillMaxSize()
                )
            }
            ApiState.ERROR -> {
                Text(
                    text = errorMessage,
                    color = Color.Red
                )
            }
        }








    }


}