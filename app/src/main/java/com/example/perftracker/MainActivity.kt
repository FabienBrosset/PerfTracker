package com.example.perftracker

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.adaptive.navigationsuite.NavigationSuiteScaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import com.example.perftracker.ui.MeasurementsScreen
import com.example.perftracker.ui.theme.PerfTrackerTheme
import com.example.perftracker.viewmodel.PerfTrackerViewModel

class MainActivity : ComponentActivity() {
    private val viewModel: PerfTrackerViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            PerfTrackerTheme {
                PerfTrackerApp(viewModel = viewModel)
            }
        }
    }
}

@Composable
fun PerfTrackerApp(viewModel: PerfTrackerViewModel) {
    var currentDestination by rememberSaveable { mutableStateOf(AppDestinations.WORKOUTS) }

    NavigationSuiteScaffold(
        navigationSuiteItems = {
            AppDestinations.entries.forEach {
                item(
                    icon = {
                        Icon(
                            painterResource(it.icon),
                            contentDescription = it.label
                        )
                    },
                    label = { Text(it.label) },
                    selected = it == currentDestination,
                    onClick = { currentDestination = it }
                )
            }
        }
    ) {
        Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
            when (currentDestination) {
                AppDestinations.WORKOUTS -> Text(
                    text = "Écran des Séances (À venir)",
                    modifier = Modifier.padding(innerPadding)
                )
                AppDestinations.EXERCISES -> Text(
                    text = "Profil Utilisateur (À venir)",
                    modifier = Modifier.padding(innerPadding)
                )
                AppDestinations.MEASUREMENTS -> MeasurementsScreen(
                    viewModel = viewModel,
                    modifier = Modifier.padding(innerPadding)
                )
            }
        }
    }
}

enum class AppDestinations(
    val label: String,
    val icon: Int,
) {
    WORKOUTS("Séances", R.drawable.ic_favorite),
    MEASUREMENTS("Mensurations", R.drawable.ic_home),
    EXERCISES("Exercices/Machines", R.drawable.ic_account_box),
}
