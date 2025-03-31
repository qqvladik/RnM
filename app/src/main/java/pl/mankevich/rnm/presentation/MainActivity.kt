package pl.mankevich.rnm.presentation

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Modifier
import androidx.core.splashscreen.SplashScreen
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import pl.mankevich.dependencies.LocalDependenciesProvider
import pl.mankevich.dependencies.dependenciesProvider
import pl.mankevich.designsystem.theme.RnmTheme

class MainActivity : ComponentActivity() {
    private lateinit var navController: NavHostController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        installSplashScreen().keepDoing(300)
        enableEdgeToEdge()

        setContent {
            RnmTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    CompositionLocalProvider(
                        LocalDependenciesProvider provides application.dependenciesProvider
                    ) {
                        navController = rememberNavController()
                        Navigation(navController)
                    }
                }
            }
        }
    }

    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
        val data = intent.data
        if (intent.action == Intent.ACTION_VIEW && data is Uri) {
            navController.navigate(data)
        }
    }
}

fun SplashScreen.keepDoing(timeMillis: Long) {
    setKeepOnScreenCondition {
        // I just want to show splash screen without loading in background, so i just stop main thread for some time. Better not to do that!
        runBlocking {
            delay(timeMillis)
        }
        false
    }
}
