package com.heriawanfx.mobile.compose

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.heriawanfx.mobile.compose.ui.theme.AMSComposeTheme

@Composable
fun OnBoardingScreen(onButtonClicked: () -> Unit) { //hoisting
    AMSComposeTheme {
        Surface(color = MaterialTheme.colors.primaryVariant) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(text = "Welcome to Notes Compose App")
                    Button(onClick = onButtonClicked) {
                        Text(text = "Continue")
                    }
                }
            }
        }

    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    OnBoardingScreen(onButtonClicked = ({}))
}