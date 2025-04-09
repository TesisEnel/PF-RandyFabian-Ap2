
package edu.ucne.fluentpath

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import dagger.hilt.android.AndroidEntryPoint
import edu.ucne.fluentpath.ui.theme.FluentPathTheme
import edu.ucne.fluentpath.ui.theme.MainApp

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        Thread.setDefaultUncaughtExceptionHandler { _, throwable ->
            android.util.Log.e("APP_CRASH", "Crash detectado", throwable)
            recreate()
        }

        setContent {
            FluentPathTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = Color(0xFF38CC74)
                ) {
                    MainApp()
                }
            }
        }
    }
}

