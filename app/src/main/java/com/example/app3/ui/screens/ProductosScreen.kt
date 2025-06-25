package com.example.app3.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.app3.data.entities.Producto
import com.example.app3.viewmodel.ProductoViewModel

@Composable
fun ProductosScreen(viewModel: ProductoViewModel) {
    val productos by viewModel.productos.collectAsState()
    var nombre by remember { mutableStateOf("") }
    var precio by remember { mutableStateOf("") }

    Column(modifier = Modifier.padding(16.dp)) {
        Text("Gestión de Productos", style = MaterialTheme.typography.titleLarge)
        Spacer(Modifier.height(8.dp))
        OutlinedTextField(
            value = nombre,
            onValueChange = { nombre = it },
            label = { Text("Nombre del Producto") },
            modifier = Modifier.fillMaxWidth()
        )
        OutlinedTextField(
            value = precio,
            onValueChange = { precio = it },
            label = { Text("Precio") },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true
        )
        Button(
            onClick = {
                val precioDouble = precio.toDoubleOrNull()
                if (nombre.isNotBlank() && precioDouble != null) {
                    viewModel.insert(Producto(nombreProducto = nombre, precio = precioDouble))
                    nombre = ""
                    precio = ""
                }
            },
            modifier = Modifier.padding(top = 8.dp)
        ) {
            Text("Agregar Producto")
        }
        Spacer(Modifier.height(16.dp))
        Text("Lista de Productos", style = MaterialTheme.typography.titleMedium)
        LazyColumn {
            items(productos.size) { idx ->
                val producto = productos[idx]
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
                            Text(producto.nombreProducto, style = MaterialTheme.typography.bodyLarge)
                            Text("$${producto.precio}", style = MaterialTheme.typography.bodyMedium)
                        }
                        Row {
                            IconButton(onClick = { viewModel.delete(producto) }) {
                                Icon(Icons.Default.Delete, contentDescription = "Eliminar")
                            }
                        }
                    }
                }
            }
        }
    }
}
