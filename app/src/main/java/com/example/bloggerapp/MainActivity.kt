package com.example.bloggerapp

import android.os.Bundle
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import com.example.bloggerapp.ui.theme.BloggerAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            BloggerAppTheme {
                WebViewGridApp()
            }
        }
    }
}

@Composable
fun WebViewGridApp() {
    var url by remember { mutableStateOf("https://top-earning-tips-a-to-z.blogspot.com/2025/05/how-to-make-money-smart-strategies-for.html") }
    var submittedUrl by remember { mutableStateOf("") }

    Column(modifier = Modifier
        .padding(16.dp)
        .fillMaxSize()) {

        // URL input + Button
        Row(modifier = Modifier.fillMaxWidth()) {
            TextField(
                value = url,
                onValueChange = { url = it },
                modifier = Modifier.weight(1f),
                placeholder = { Text("Enter URL") },
                keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Uri),
                singleLine = true,
            )
            Spacer(modifier = Modifier.width(8.dp))
            Button(onClick = { submittedUrl = url }) {
                Text("Go")
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Grid of WebViews
        LazyVerticalGrid(
            columns = GridCells.Fixed(1),
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            contentPadding = PaddingValues(4.dp)
        ) {
            items(30) { _ ->
                WebViewItem(url = submittedUrl)
            }
        }
    }
}

@Composable
fun WebViewItem(url: String) {
    val context = LocalContext.current
    AndroidView(
        factory = {
            WebView(context).apply {
                settings.javaScriptEnabled = true
                settings.domStorageEnabled = true

                // Force mobile user agent
                settings.userAgentString =
                    "Mozilla/5.0 (Linux; Android 10; Mobile) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/91.0.4472.77 Mobile Safari/537.36"

                webViewClient = WebViewClient()
                if (url.isNotBlank()) {
                    loadUrl(url)
                }
            }
        },
        update = {
            if (url.isNotBlank()) {
                it.loadUrl(url)
            }
        },
        modifier = Modifier
            .height(150.dp)
            .fillMaxWidth()
    )
}