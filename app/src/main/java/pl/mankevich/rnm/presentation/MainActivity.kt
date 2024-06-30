package pl.mankevich.rnm.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Modifier
import pl.mankevich.dependencies.LocalDependenciesProvider
import pl.mankevich.dependencies.dependenciesProvider
import pl.mankevich.rnm.ui.theme.RnMTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            RnMTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    CompositionLocalProvider(
                        LocalDependenciesProvider provides application.dependenciesProvider
                    ) {
                        Navigation()
                    }
                }
            }
        }
    }
}
