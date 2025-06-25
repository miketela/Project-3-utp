package com.example.app3.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.app3.data.entities.Orden
import com.example.app3.viewmodel.OrdenViewModel

@Composable
fun OrdenesScreen(viewModel: OrdenViewModel) {
    val ordenes by viewModel.ordenes.collectAsState()

    Column(modifier = Modifier.padding(16.dp)) {
        Text("Órdenes de Compra", style = MaterialTheme.typography.titleLarge)
        Spacer(Modifier.height(16.dp))
        LazyColumn {
            items(ordenes.size) { idx ->
                val orden = ordenes[idx]
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 4.dp),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
                ) {
                    Column(modifier = Modifier.padding(8.dp)) {
                        Text("ID: ${orden.idOrden}", style = MaterialTheme.typography.bodyLarge)
                        Text("Cliente: ${orden.idCliente}", style = MaterialTheme.typography.bodyMedium)
                        Text("Fecha: ${orden.fecha}", style = MaterialTheme.typography.bodyMedium)
                        // Aquí puedes agregar un botón para ver detalles
                    }
                }
            }
        }
    }
}
