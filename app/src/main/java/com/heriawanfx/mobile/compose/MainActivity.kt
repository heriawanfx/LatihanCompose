package com.heriawanfx.mobile.compose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
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
import com.heriawanfx.mobile.compose.ui.theme.AMSComposeTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            HomePageView()
        }
    }
}

@Composable
fun HomePageView(showOnBoarding: Boolean = true) {
    AMSComposeTheme {

        //Persisting state
        var shouldShowOnBoarding by rememberSaveable { mutableStateOf(showOnBoarding) }
        if (shouldShowOnBoarding) {
            OnBoardingScreen(onButtonClicked = { shouldShowOnBoarding = false })
        } else {
            NumberScreen()
        }
    }
}

@Composable
fun NumberScreen(modifier: Modifier = Modifier, mainViewModel: MainViewModel = viewModel() ) {

    var isShowAll by remember { mutableStateOf(false) }

    Surface(
        color = MaterialTheme.colors.background,
    ) {
        Scaffold(
            topBar = {
                TopAppBar(title = { Text(text = "Home")})
            },
        ){
            Column(modifier = modifier.padding(horizontal = 16.dp)) {
                TotalView(list = mainViewModel.numbers, onShowAll = { isShowAll = !isShowAll })
                NumberListView(list = mainViewModel.numbers)
            }
        }

    }


}

@Composable
fun TotalView(list: List<String>, onShowAll: () -> Unit, modifier: Modifier = Modifier){
    Row(verticalAlignment = Alignment.CenterVertically) {
        Text(
            text = "Total: ${list.count()}",
            style = TextStyle(fontWeight = FontWeight.Bold)
        )
        Spacer(modifier = modifier.weight(1f))
        Button(onClick = onShowAll) {
            Text(text = "Show All")
        }
    }
}

@Composable
fun NumberListView(list: List<String>, modifier: Modifier = Modifier) {
    LazyColumn(modifier = modifier) {
        items(items = list, key = { it }  ) { item ->
            NumberItemView(value = item)
        }
    }
}

@Composable
fun NumberItemView(value: String, modifier: Modifier = Modifier) {
    //Persisting state
    var isShowDetail by rememberSaveable { mutableStateOf(false) }

    Card(modifier = modifier.padding(top = 8.dp)) {
        Column(modifier = modifier.padding(vertical = 4.dp, horizontal = 8.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(text = "Data $value")
                Spacer(modifier = modifier.weight(1f))
                TextButton(onClick = {
                    isShowDetail = !isShowDetail
                }) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(text = if (!isShowDetail) "Show Detail" else "Hide Detail")
                        IconButton(onClick = { isShowDetail = !isShowDetail }) {
                            Icon(imageVector = if(isShowDetail) Icons.Filled.ExpandLess else Icons.Filled.ExpandMore , contentDescription = if (!isShowDetail) "Show Detail" else "Hide Detail")
                        }
                    }
                }
            }
            AnimatedVisibility(visible = isShowDetail) {
                Text(text = "Lorem ipsum dolor sit amet")
            }
        }
    }



}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun OnBoardingPreview() {
    HomePageView(showOnBoarding = false)
}
