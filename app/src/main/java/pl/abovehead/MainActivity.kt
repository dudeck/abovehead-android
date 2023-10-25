@file:OptIn(ExperimentalMaterial3Api::class)

package pl.abovehead

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.List
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.rememberNavController
import pl.abovehead.ui.theme.AboveHeadTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AboveHeadTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    BottomNavBar()
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BottomNavBar() {
    val navController = rememberNavController()
    Scaffold(
        bottomBar = {
            NavigationBar {
                NavigationBarItem(
                    icon = { Icon(Icons.Filled.List, contentDescription = "News") },
                    label = { Text("News") },
                    onClick = {},
                    selected = true
                )
                NavigationBarItem(
                    icon = { Icon(Icons.Filled.AccountBox, contentDescription = "Pictures") },
                    label = { Text("Pictures") },
                    onClick = {},
                    selected = false
                )
                NavigationBarItem(
                    icon = { Icon(Icons.Filled.Check, contentDescription = "Cart") },
                    label = { Text("Cart") },
                    onClick = {},
                    selected = false
                )

            }
        }) { innerPadding ->
        Text(
            text = " LOL", modifier = Modifier.padding(innerPadding),
        )
    }
}


@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    AboveHeadTheme {
        BottomNavBar()
    }
}