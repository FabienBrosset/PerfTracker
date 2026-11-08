package com.example.perftracker.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.BasicAlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.perftracker.model.MeasurementZone
import com.example.perftracker.viewmodel.PerfTrackerViewModel

/**
 * Vue (Compose) : Affiche la liste des zones de mesure.
 * S'abonne aux modifications du ViewModel (Contrôleur).
 */
@Composable
fun MeasurementsScreen(
    viewModel: PerfTrackerViewModel,
    modifier: Modifier = Modifier
) {
    // Équivalent du template Django qui consomme la donnée injectée par la vue
    val zones by viewModel.zones.collectAsState()
    var showModal by remember {mutableStateOf(false)}

    Column(modifier = modifier.padding(16.dp)) {
        Text(
            text = "Zones de Mensuration",
            style = MaterialTheme.typography.headlineMedium
        )
        Button(onClick = { showModal = true }) {
            Text("Ajouter une mesure")
        }

        Spacer(modifier = Modifier.height(16.dp))

        LazyColumn {
            items(zones, key = { it.id }) { zone ->
                ZoneCard(zone = zone)
                Spacer(modifier = Modifier.height(8.dp))
            }
        }
        if (showModal) {
            DialogAddMeasurement(onDismiss = {showModal = false})
        }
    }
}

@Composable
fun ZoneCard(zone: MeasurementZone) {
    Card(modifier = Modifier.fillMaxWidth()) {
        Column(modifier = Modifier.padding(12.dp)) {
            Row {
                Text(
                    text = zone.name,
                    style = MaterialTheme.typography.titleMedium,
                    modifier = Modifier.weight(1f)
                )
                Text(
                    text = zone.unit,
                    style = MaterialTheme.typography.labelMedium,
                    color = MaterialTheme.colorScheme.primary
                )
            }
            Text(
                text = "Catégorie : ${zone.category}",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.secondary
            )
            if (zone.description.isNotBlank()) {
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = zone.description,
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DialogAddMeasurement(onDismiss:()->Unit) {
    BasicAlertDialog(
        onDismissRequest = onDismiss,
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text("Ajouter une mensuration pour une zone donnée")
            Spacer(modifier = Modifier.height(24.dp))
            Button(
                onClick = { onDismiss() }
            ) {
                Text("Confirm")
            }
        }
    }
}