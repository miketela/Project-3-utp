package com.example.app3.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.app3.data.entities.Cliente
import com.example.app3.data.entities.Producto
import com.example.app3.data.entities.Orden
import com.example.app3.data.entities.DetalleOrden
import com.example.app3.viewmodel.ClienteViewModel
import com.example.app3.viewmodel.ProductoViewModel
import com.example.app3.viewmodel.OrdenViewModel
import java.text.SimpleDateFormat
import java.util.*

@Composable
fun CrearOrdenScreen(
    clienteViewModel: ClienteViewModel,
    productoViewModel: ProductoViewModel,
    ordenViewModel: OrdenViewModel,
    onOrdenCreada: () -> Unit
) {
    val clientes by clienteViewModel.clientes.collectAsState()
    val productos by productoViewModel.productos.collectAsState()
    var clienteSeleccionado by remember { mutableStateOf<Cliente?>(null) }
    var productoSeleccionado by remember { mutableStateOf<Producto?>(null) }
    var cantidad by remember { mutableStateOf("") }
    var detalles by remember { mutableStateOf(listOf<DetalleOrden>()) }

    Column(modifier = Modifier.padding(16.dp)) {
        Text("Crear Nueva Orden", style = MaterialTheme.typography.titleLarge)
        Spacer(Modifier.height(8.dp))
        // Selección de cliente
        ExposedDropdownMenuBox(
            expanded = false,
            onExpandedChange = {}
        ) {
            var expanded by remember { mutableStateOf(false) }
            OutlinedTextField(
                value = clienteSeleccionado?.nombre ?: "Seleccionar Cliente",
                onValueChange = {},
                readOnly = true,
                label = { Text("Cliente") },
                modifier = Modifier.menuAnchor().fillMaxWidth(),
                trailingIcon = {
                    ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded)
                },
                onClick = { expanded = !expanded }
            )
            ExposedDropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false }
            ) {
                clientes.forEach { cliente ->
                    DropdownMenuItem(
                        text = { Text(cliente.nombre) },
                        onClick = {
                            clienteSeleccionado = cliente
                            expanded = false
                        }
                    )
                }
            }
        }
        Spacer(Modifier.height(8.dp))
        // Selección de producto
        ExposedDropdownMenuBox(
            expanded = false,
            onExpandedChange = {}
        ) {
            var expandedProd by remember { mutableStateOf(false) }
            OutlinedTextField(
                value = productoSeleccionado?.nombreProducto ?: "Seleccionar Producto",
                onValueChange = {},
                readOnly = true,
                label = { Text("Producto") },
                modifier = Modifier.menuAnchor().fillMaxWidth(),
                trailingIcon = {
                    ExposedDropdownMenuDefaults.TrailingIcon(expanded = expandedProd)
                },
                onClick = { expandedProd = !expandedProd }
            )
            ExposedDropdownMenu(
                expanded = expandedProd,
                onDismissRequest = { expandedProd = false }
            ) {
                productos.forEach { producto ->
                    DropdownMenuItem(
                        text = { Text(producto.nombreProducto) },
                        onClick = {
                            productoSeleccionado = producto
                            expandedProd = false
                        }
                    )
                }
            }
        }
        Spacer(Modifier.height(8.dp))
        OutlinedTextField(
            value = cantidad,
            onValueChange = { cantidad = it },
            label = { Text("Cantidad") },
            modifier = Modifier.fillMaxWidth()
        )
        Button(
            onClick = {
                val prod = productoSeleccionado
                val cant = cantidad.toIntOrNull()
                if (prod != null && cant != null && cant > 0) {
                    detalles = detalles + DetalleOrden(
                        idOrden = 0L, // Se asignará después
                        idProducto = prod.idProducto,
                        cantidad = cant
                    )
                    productoSeleccionado = null
                    cantidad = ""
                }
            },
            modifier = Modifier.padding(top = 8.dp)
        ) {
            Text("Agregar Producto a Orden")
        }
        Spacer(Modifier.height(16.dp))
        Text("Productos en la Orden", style = MaterialTheme.typography.titleMedium)
        LazyColumn {
            items(detalles.size) { idx ->
                val detalle = detalles[idx]
                val prod = productos.find { it.idProducto == detalle.idProducto }
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
                            Text(prod?.nombreProducto ?: "", style = MaterialTheme.typography.bodyLarge)
                            Text("Cantidad: ${detalle.cantidad}", style = MaterialTheme.typography.bodyMedium)
                        }
                        Row {
                            IconButton(onClick = {
                                detalles = detalles.filterIndexed { i, _ -> i != idx }
                            }) {
                                Icon(Icons.Default.Delete, contentDescription = "Eliminar")
                            }
                        }
                    }
                }
            }
        }
        Spacer(Modifier.height(16.dp))
        Button(
            onClick = {
                val cliente = clienteSeleccionado
                if (cliente != null && detalles.isNotEmpty()) {
                    val fecha = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()).format(Date())
                    val orden = Orden(idCliente = cliente.idCliente, fecha = fecha)
                    ordenViewModel.insertOrdenConDetalles(orden, detalles) {
                        onOrdenCreada()
                    }
                }
            },
            enabled = clienteSeleccionado != null && detalles.isNotEmpty(),
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Finalizar Orden")
        }
    }
}
