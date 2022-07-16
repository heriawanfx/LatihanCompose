package com.heriawanfx.mobile.compose.ui

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Save
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.heriawanfx.mobile.compose.ui.theme.AMSComposeTheme
import dagger.hilt.android.AndroidEntryPoint


internal const val KEY_NOTE_ID = "KEY_NOTE_ID"

@AndroidEntryPoint
class DetailActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AMSComposeTheme {
                DetailScreen()
            }
        }
    }
}

@Composable
fun DetailScreen(modifier: Modifier = Modifier, detailViewModel: DetailViewModel = viewModel()) {
    Surface {
        Scaffold(
            topBar = {
                TopAppBar(title = { Text("Note Detail") })
            },
            floatingActionButton = {
                ExtendedFloatingActionButton(text = {
                    Text(text = "SAVE")
                }, onClick = {
                    Log.e("DetailScreen", "SAVE: ${detailViewModel.getNote()}")
                }, icon = {
                    Icon(imageVector = Icons.Filled.Save, contentDescription = "SAVE")
                })
            }
        ) {
            NoteFormView(
                modifier = modifier
                    .padding(horizontal = 16.dp)
                    .padding(top = 16.dp),
                titleValue = detailViewModel.getNote()?.title,
                onTitleValueChange = { detailViewModel.title = it },
                descriptionValue = detailViewModel.getNote()?.description,
                onDescriptionValueChange = { detailViewModel.description = it },
            )
        }
    }
}

@Composable
fun NoteFormView(
    modifier: Modifier = Modifier,
    titleValue: String?,
    onTitleValueChange: (String) -> Unit,
    descriptionValue: String?,
    onDescriptionValueChange: (String) -> Unit
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        TextField(value = titleValue ?: "", onValueChange = onTitleValueChange, label = {
            Text(text = "Title")
        })
        Spacer(modifier = Modifier.height(8.dp))
        TextField(
            value = descriptionValue ?: "",
            onValueChange = onDescriptionValueChange,
            label = {
                Text(text = "Description")
            })
    }
}

@Preview(showBackground = true)
@Composable
fun DetailPreview() {
    AMSComposeTheme {
        DetailScreen()
    }
}