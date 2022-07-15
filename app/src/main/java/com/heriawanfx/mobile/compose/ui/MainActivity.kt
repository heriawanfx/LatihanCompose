package com.heriawanfx.mobile.compose.ui

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.DeleteOutline
import androidx.compose.material.icons.filled.ExpandLess
import androidx.compose.material.icons.filled.ExpandMore
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.heriawanfx.mobile.compose.data.Note
import com.heriawanfx.mobile.compose.ui.theme.AMSComposeTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            HomePageView(
                onFabAddClick = {
                    val intent = Intent(this, DetailActivity::class.java)
                    startActivity(intent)
                },
                onNoteItemClick = {
                    val intent = Intent(this, DetailActivity::class.java)
                    intent.putExtra(KEY_NOTE_ID, it.id)
                    startActivity(intent)
                }
            )
        }
    }
}

@Composable
fun HomePageView(
    showOnBoarding: Boolean = true,
    onFabAddClick: () -> Unit = {},
    onNoteItemClick: (Note) -> Unit = {}
) {
    AMSComposeTheme {
        //Persisting state
        var shouldShowOnBoarding by rememberSaveable { mutableStateOf(showOnBoarding) }
        if (shouldShowOnBoarding) {
            OnBoardingScreen(onButtonClicked = { shouldShowOnBoarding = false })
        } else {
            NotesScreen(onFabAddClick = onFabAddClick, onNoteItemClick = onNoteItemClick)
        }
    }
}

@Composable
fun NotesScreen(
    modifier: Modifier = Modifier,
    mainViewModel: MainViewModel = viewModel(),
    onFabAddClick: () -> Unit,
    onNoteItemClick: (Note) -> Unit
) {

    var isShowAll by rememberSaveable { mutableStateOf(false) }

    Surface(
        color = MaterialTheme.colors.background,
    ) {
        Scaffold(
            topBar = {
                TopAppBar(title = { Text(text = "List of Notes") })
            },
            floatingActionButton = {
                FloatingActionButton(onClick = onFabAddClick) {
                    Icon(
                        imageVector = Icons.Filled.Add,
                        contentDescription = "Add New"
                    )
                }
            },
            floatingActionButtonPosition = FabPosition.End
        ) {
            Column(modifier = modifier.padding(horizontal = 16.dp)) {
                TotalView(list = mainViewModel.notes, onShowAll = { isShowAll = !isShowAll })
                NotesListView(
                    list = mainViewModel.notes,
                    onNoteItemClick = onNoteItemClick,
                    onExpandClicked = { note ->
                        mainViewModel.toggleExpandNote(note)
                    },
                    onCheckedChange = { note, isChecked ->
                        mainViewModel.checkNote(note, isChecked)
                    },
                    onDelete = {
                        mainViewModel.removeNote(it)
                    })
            }
        }

    }

}


@Composable
fun NotesListView(
    list: List<Note>,
    onNoteItemClick: (Note) -> Unit,
    onExpandClicked: (Note) -> Unit,
    onCheckedChange: (Note, Boolean) -> Unit,
    onDelete: (Note) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyColumn(modifier = modifier) {
        items(items = list, key = { it.id }) { item ->
            NoteItemView(
                item = item,
                onNoteItemClick = { onNoteItemClick(item) },
                isExpanded = item.isExpanded,
                onToggleExpand = { onExpandClicked(item) },
                isChecked = item.isChecked,
                onCheckedChange = { isChecked -> onCheckedChange(item, isChecked) },
                onDelete = { onDelete(item) },
                modifier = modifier
            )

        }
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun NoteItemView(
    item: Note,
    onNoteItemClick: () -> Unit,
    isExpanded: Boolean,
    onToggleExpand: () -> Unit,
    isChecked: Boolean,
    onCheckedChange: (Boolean) -> Unit,
    onDelete: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(modifier = modifier.padding(top = 8.dp), onClick = onNoteItemClick) {
        Column {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Checkbox(checked = isChecked, onCheckedChange = onCheckedChange)
                Text(text = item.title)
                Spacer(modifier = Modifier.weight(1f))
                IconButton(onClick = onToggleExpand) {
                    Icon(
                        imageVector = if (isExpanded) Icons.Filled.ExpandLess else Icons.Filled.ExpandMore,
                        contentDescription = if (isExpanded) "Hide Description" else "Show Description"
                    )
                }
                IconButton(onClick = onDelete) {
                    Icon(imageVector = Icons.Filled.DeleteOutline, contentDescription = "Delete")
                }
            }
            AnimatedVisibility(
                visible = isExpanded,
                Modifier.padding(start = 16.dp, bottom = 16.dp)
            ) {
                Text(text = item.description)
            }
        }
    }
}

@Composable
fun TotalView(list: List<Note>, onShowAll: () -> Unit, modifier: Modifier = Modifier) {
    Row(verticalAlignment = Alignment.CenterVertically, modifier = modifier) {
        Text(
            text = "Total: ${list.count()}",
            style = TextStyle(fontWeight = FontWeight.Bold)
        )
        Spacer(modifier = Modifier.weight(1f))
        Button(onClick = onShowAll) {
            Text(text = "Show All")
        }
    }
}


@Preview(showBackground = true, showSystemUi = true)
@Composable
fun MainPreview() {
    HomePageView(showOnBoarding = false)
}
