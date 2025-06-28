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
import androidx.navigation.NavController
import java.text.SimpleDateFormat
import java.util.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CrearOrdenScreen(
    clienteViewModel: ClienteViewModel,
    productoViewModel: ProductoViewModel,
    ordenViewModel: OrdenViewModel,
    onOrdenCreada: () -> Unit,
    navController: NavController
) {
    val clientes by clienteViewModel.clientes.collectAsState()
    val productos by productoViewModel.productos.collectAsState()
    var clienteSeleccionado by remember { mutableStateOf<Cliente?>(null) }
    var productoSeleccionado by remember { mutableStateOf<Producto?>(null) }
    var cantidad by remember { mutableStateOf("") }
    var detalles by remember { mutableStateOf(listOf<Pair<DetalleOrden, Producto>>()) }

    val totalOrden = detalles.sumOf { (detalle, producto) -> producto.precio * detalle.cantidad }

    Column(modifier = Modifier.padding(16.dp)) {
        Text("Crear Nueva Orden", style = MaterialTheme.typography.titleLarge)
        Spacer(Modifier.height(8.dp))
        // Selección de cliente
        var expanded by remember { mutableStateOf(false) }
        ExposedDropdownMenuBox(
            expanded = expanded,
            onExpandedChange = { expanded = it }
        ) {
            OutlinedTextField(
                value = clienteSeleccionado?.nombre ?: "Seleccionar Cliente",
                onValueChange = {},
                readOnly = true,
                label = { Text("Cliente") },
                modifier = Modifier.menuAnchor().fillMaxWidth(),
                trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
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
        Button(onClick = { navController.navigate("clientes") }, modifier = Modifier.fillMaxWidth()) {
            Text("Agregar Cliente")
        }
        Spacer(Modifier.height(8.dp))
        // Selección de producto
        var expandedProd by remember { mutableStateOf(false) }
        ExposedDropdownMenuBox(
            expanded = expandedProd,
            onExpandedChange = { expandedProd = it }
        ) {
            OutlinedTextField(
                value = productoSeleccionado?.nombreProducto ?: "Seleccionar Producto",
                onValueChange = {},
                readOnly = true,
                label = { Text("Producto") },
                modifier = Modifier.menuAnchor().fillMaxWidth(),
                trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expandedProd) },
            )
            ExposedDropdownMenu(
                expanded = expandedProd,
                onDismissRequest = { expandedProd = false }
            ) {
                productos.forEach { producto ->
                    DropdownMenuItem(
                        text = { Text("${producto.nombreProducto} - ${producto.precio}") },
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
                    val nuevoDetalle = DetalleOrden(
                        idOrden = 0L, // Se asignará después
                        idProducto = prod.idProducto,
                        cantidad = cant
                    )
                    detalles = detalles + (nuevoDetalle to prod)
                    productoSeleccionado = null
                    cantidad = ""
                }
            },
            modifier = Modifier.padding(top = 8.dp)
        ) {
            Text("Agregar Producto a Orden")
        }
        Spacer(Modifier.height(16.dp))
        Text("Resumen de la Orden", style = MaterialTheme.typography.titleMedium)
        LazyColumn(modifier = Modifier.weight(1f)) {
            items(detalles.size) { idx ->
                val (detalle, prod) = detalles[idx]
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
                            Text(prod.nombreProducto, style = MaterialTheme.typography.bodyLarge)
                            Text("Cantidad: ${detalle.cantidad} x ${prod.precio}", style = MaterialTheme.typography.bodyMedium)
                        }
                        IconButton(onClick = {
                            detalles = detalles.filterIndexed { i, _ -> i != idx }
                        }) {
                            Icon(Icons.Default.Delete, contentDescription = "Eliminar")
                        }
                    }
                }
            }
        }
        Spacer(Modifier.height(16.dp))
        Text("Total: ${String.format("%.2f", totalOrden)}", style = MaterialTheme.typography.headlineSmall)
        Spacer(Modifier.height(8.dp))
        Button(
            onClick = {
                val cliente = clienteSeleccionado
                if (cliente != null && detalles.isNotEmpty()) {
                    val fecha = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()).format(Date())
                    val orden = Orden(idCliente = cliente.idCliente, fecha = fecha)
                    ordenViewModel.insertOrdenConDetalles(orden, detalles.map { it.first }) {
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
