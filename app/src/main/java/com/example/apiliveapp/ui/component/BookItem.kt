package com.example.apiliveapp.ui.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import coil3.compose.SubcomposeAsyncImage
import com.example.apiliveapp.data.model.Book

@Composable
fun BookItem(
    book: Book,
    modifier: Modifier = Modifier
) {


    OutlinedCard(
        modifier = modifier.fillMaxWidth(),
    ) {
        Row(
            modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.CenterVertically
        ) {
            book.cover_edition_key?.let {
                SubcomposeAsyncImage(
                    modifier = Modifier.size(100.dp),
                    model = "https://covers.openlibrary.org/b/OLID/${book.cover_edition_key}-M.jpg",
                    contentDescription = book.title
                )
            }

            Text(book.title)
        }
    }


}