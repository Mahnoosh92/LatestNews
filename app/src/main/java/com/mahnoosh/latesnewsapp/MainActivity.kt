package com.mahnoosh.latesnewsapp

import android.content.res.Configuration
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.util.Consumer
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.mahnoosh.designsystem.ui.theme.LatesNewsAppTheme
import com.mahnoosh.search.navigation.navigateToSearch
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.conflate
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        var themeSettings by mutableStateOf(
            ThemeSettings(
                darkTheme = resources.configuration.isSystemInDarkTheme
            ),
        )
        // Mode
        lifecycleScope.launch {
            lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                isSystemInDarkTheme()
                    .distinctUntilChanged()
                    .onEach {
                        themeSettings = ThemeSettings(darkTheme = it)
                    }
                    .collect()

            }
        }
        enableEdgeToEdge()
        setContent {
            val appState = rememberNewsAppState()
            val snackbarHostState = remember { SnackbarHostState() }
            LatesNewsAppTheme(darkTheme = themeSettings.darkTheme) {
                NewsApp(appState = appState, snackbarHostState = snackbarHostState)
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    LatesNewsAppTheme {
        Greeting("Android")
    }
}

data class ThemeSettings(
    val darkTheme: Boolean
)

val Configuration.isSystemInDarkTheme
    get() = (uiMode and Configuration.UI_MODE_NIGHT_MASK) == Configuration.UI_MODE_NIGHT_YES

fun ComponentActivity.isSystemInDarkTheme(): Flow<Boolean> = callbackFlow {
    channel.trySend(resources.configuration.isSystemInDarkTheme)

    val listener = Consumer<Configuration> {
        channel.trySend(it.isSystemInDarkTheme)
    }

    addOnConfigurationChangedListener(listener)

    awaitClose { removeOnConfigurationChangedListener(listener) }
}
    .distinctUntilChanged()
    .conflate()