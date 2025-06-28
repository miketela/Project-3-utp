package com.example.app3.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.clickable
import com.example.app3.data.entities.Orden
import com.example.app3.viewmodel.OrdenViewModel

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.navigation.NavController

@Composable
fun OrdenesScreen(viewModel: OrdenViewModel, navController: NavController) {
    val ordenes by viewModel.ordenes.collectAsState()

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(onClick = { navController.navigate("crear_orden") }) {
                Icon(Icons.Default.Add, contentDescription = "Crear Orden")
            }
        }
    ) {
        Column(modifier = Modifier.padding(it).padding(16.dp)) {
            Text("Órdenes de Compra", style = MaterialTheme.typography.titleLarge)
            Spacer(Modifier.height(16.dp))
            Button(onClick = { navController.navigate("clientes") }) {
                Text("Agregar Cliente Nuevo")
            }
            Spacer(Modifier.height(16.dp))
            LazyColumn {
                items(ordenes.size) { idx ->
                    val orden = ordenes[idx]
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 4.dp)
                            .clickable { navController.navigate("detalle_orden/${orden.idOrden}") },
                        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
                    ) {
                        Column(modifier = Modifier.padding(8.dp)) {
                            Text("ID: ${orden.idOrden}", style = MaterialTheme.typography.bodyLarge)
                            Text("Cliente ID: ${orden.idCliente}", style = MaterialTheme.typography.bodyMedium) // Corregido para claridad
                            Text("Fecha: ${orden.fecha}", style = MaterialTheme.typography.bodyMedium)
                            // Aquí puedes agregar un botón para ver detalles
                        }
                    }
                }
            }
        }
    }
}
