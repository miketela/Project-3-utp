package com.example.app3.ui.screens

import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.app3.viewmodel.ClienteViewModel
import com.example.app3.viewmodel.ProductoViewModel
import com.example.app3.viewmodel.OrdenViewModel
import com.example.app3.ui.screens.ClientesScreen
import com.example.app3.ui.screens.ProductosScreen
import com.example.app3.ui.screens.OrdenesScreen
import com.example.app3.ui.screens.CrearOrdenScreen
import androidx.compose.ui.Modifier

@Composable
fun MainScreen(
    clienteViewModel: ClienteViewModel,
    productoViewModel: ProductoViewModel,
    ordenViewModel: OrdenViewModel
) {
    val navController = rememberNavController()
    Scaffold(
        topBar = {
            TopAppBar(title = { Text("Gestión de Clientes, Productos y Órdenes") })
        }
    ) { padding ->
        NavHost(
            navController = navController,
            startDestination = "clientes",
            modifier = Modifier.padding(padding)
        ) {
            composable("clientes") { ClientesScreen(clienteViewModel) }
            composable("productos") { ProductosScreen(productoViewModel) }
            composable("ordenes") { OrdenesScreen(ordenViewModel) }
            composable("crear_orden") {
                CrearOrdenScreen(
                    clienteViewModel = clienteViewModel,
                    productoViewModel = productoViewModel,
                    ordenViewModel = ordenViewModel,
                    onOrdenCreada = { navController.navigate("ordenes") }
                )
            }
        }
    }
}
