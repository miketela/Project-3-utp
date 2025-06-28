package com.example.app3.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.lazy.items
import com.example.app3.viewmodel.ClienteViewModel
import com.example.app3.viewmodel.OrdenViewModel
import com.example.app3.viewmodel.ProductoViewModel

@Composable
fun DetalleOrdenScreen(
    idOrden: Long,
    ordenViewModel: OrdenViewModel,
    productoViewModel: ProductoViewModel,
    clienteViewModel: ClienteViewModel
) {
    val orden by ordenViewModel.getOrdenById(idOrden).collectAsState(initial = null)
    val detallesOrden by ordenViewModel.getDetallesOrdenForOrder(idOrden)
        .collectAsState(initial = emptyList())
    val clientes by clienteViewModel.clientes.collectAsState(initial = emptyList())
    val allProductos by productoViewModel.productos.collectAsState(
        initial = emptyList()
    )

    Column(modifier = Modifier.padding(16.dp)) {
        Text("Detalle de la Orden", style = MaterialTheme.typography.titleLarge)
        Spacer(Modifier.height(16.dp))

        orden?.let { currentOrden ->
            val cliente = clientes.find { c -> c.idCliente == currentOrden.idCliente }
            Text("ID de Orden: ${currentOrden.idOrden}", style = MaterialTheme.typography.bodyLarge)
            Text(
                "Cliente: ${cliente?.nombre ?: "Desconocido"}",
                style = MaterialTheme.typography.bodyLarge
            )
            Text("Fecha: ${currentOrden.fecha}", style = MaterialTheme.typography.bodyLarge)
            Spacer(Modifier.height(16.dp))

            Text("Productos:", style = MaterialTheme.typography.titleMedium)
            LazyColumn {
                items(detallesOrden) { detalle ->
                    val producto = allProductos.find { p -> p.idProducto == detalle.idProducto }
                    if (producto != null) {
                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 4.dp),
                            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
                        ) {
                            Column(modifier = Modifier.padding(8.dp)) {
                                Text(
                                    "Producto: ${producto.nombreProducto}",
                                    style = MaterialTheme.typography.bodyMedium
                                )
                                Text(
                                    "Cantidad: ${detalle.cantidad}",
                                    style = MaterialTheme.typography.bodyMedium
                                )
                                Text(
                                    "Precio Unitario: ${producto.precio}",
                                    style = MaterialTheme.typography.bodyMedium
                                )
                                Text(
                                    "Subtotal: ${producto.precio * detalle.cantidad}",
                                    style = MaterialTheme.typography.bodyMedium
                                )
                            }
                        }
                    }
                }
            }
        } ?: run {
            Text("Cargando detalles de la orden...", style = MaterialTheme.typography.bodyLarge)
        }
    }
}
