package com.example.projek_praktikum

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.core.view.WindowCompat
import androidx.navigation.compose.rememberNavController
import com.example.projek_praktikum.model.viewModel.ViewModelDana
import com.example.projek_praktikum.navigation.AppNavGraph
import com.example.projek_praktikum.ui.theme.ProjekpraktikumTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // ⛔ GANTI INI: enableEdgeToEdge()
        WindowCompat.setDecorFitsSystemWindows(window, false)

        setContent {
            ProjekpraktikumTheme {
                Surface(color = MaterialTheme.colorScheme.background) {
                    val navController = rememberNavController()
                    AppNavGraph(navController = navController, viewModelDana = ViewModelDana())
                }
            }
        }
    }
}
