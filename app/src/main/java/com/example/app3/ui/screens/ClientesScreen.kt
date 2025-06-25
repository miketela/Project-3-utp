package com.example.app3.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.app3.data.entities.Cliente
import com.example.app3.viewmodel.ClienteViewModel

@Composable
fun ClientesScreen(viewModel: ClienteViewModel) {
    val clientes by viewModel.clientes.collectAsState()
    var nombre by remember { mutableStateOf("") }
    var correo by remember { mutableStateOf("") }

    Column(modifier = Modifier.padding(16.dp)) {
        Text("Gestión de Clientes", style = MaterialTheme.typography.titleLarge)
        Spacer(Modifier.height(8.dp))
        OutlinedTextField(
            value = nombre,
            onValueChange = { nombre = it },
            label = { Text("Nombre") },
            modifier = Modifier.fillMaxWidth()
        )
        OutlinedTextField(
            value = correo,
            onValueChange = { correo = it },
            label = { Text("Correo") },
            modifier = Modifier.fillMaxWidth()
        )
        Button(
            onClick = {
                if (nombre.isNotBlank() && correo.isNotBlank()) {
                    viewModel.insert(Cliente(nombre = nombre, correo = correo))
                    nombre = ""
                    correo = ""
                }
            },
            modifier = Modifier.padding(top = 8.dp)
        ) {
            Text("Agregar Cliente")
        }
        Spacer(Modifier.height(16.dp))
        Text("Lista de Clientes", style = MaterialTheme.typography.titleMedium)
        LazyColumn {
            items(clientes.size) { idx ->
                val cliente = clientes[idx]
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 4.dp),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
                ) {
                    Row(
                        modifier = Modifier.padding(8.dp),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Column {
                            Text(cliente.nombre, style = MaterialTheme.typography.bodyLarge)
                            Text(cliente.correo, style = MaterialTheme.typography.bodyMedium)
                        }
                        Row {
                            IconButton(onClick = { viewModel.delete(cliente) }) {
                                Icon(Icons.Default.Delete, contentDescription = "Eliminar")
                            }
                        }
                    }
                }
            }
        }
    }
}
