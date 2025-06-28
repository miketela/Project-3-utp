// Contenido para MainScreen.kt
package com.example.app3.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.ui.Modifier
import androidx.compose.ui.Alignment
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import androidx.navigation.NavType
import com.example.app3.viewmodel.ClienteViewModel
import com.example.app3.viewmodel.OrdenViewModel
import com.example.app3.viewmodel.ProductoViewModel

@Composable
fun MenuScreen(navController: androidx.navigation.NavController) {
    Column(
        modifier = Modifier.fillMaxSize().padding(all = 16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Gestión de Tienda", style = MaterialTheme.typography.headlineMedium)
        Spacer(Modifier.height(height = 32.dp))
        Button(onClick = { navController.navigate("clientes") }, modifier = Modifier.fillMaxWidth()) {
            Text("Gestionar Clientes")
        }
        Spacer(Modifier.height(height = 16.dp))
        Button(onClick = { navController.navigate("productos") }, modifier = Modifier.fillMaxWidth()) {
            Text("Gestionar Productos")
        }
        Spacer(Modifier.height(height = 16.dp))
        Button(onClick = { navController.navigate("ordenes") }, modifier = Modifier.fillMaxWidth()) {
            Text("Gestionar Órdenes")
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(
    clienteViewModel: ClienteViewModel,
    productoViewModel: ProductoViewModel,
    ordenViewModel: OrdenViewModel
) {
    val navController = rememberNavController()
    Scaffold(
        topBar = {
            val currentRoute = navController.currentBackStackEntryAsState().value?.destination?.route
            if (currentRoute != "menu") {
                TopAppBar(
                    title = { Text("Apple Store Management") },
                    navigationIcon = {
                        IconButton(onClick = { navController.navigate("menu") {
                            popUpTo("menu") { inclusive = true }
                        } }) {
                            Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back to Menu")
                        }
                    }
                )
            } else {
                TopAppBar(title = { Text("Apple Store Management") })
            }
        }
    ) { padding ->
        NavHost(
            navController = navController,
            startDestination = "menu", // Empezamos en el menú
            modifier = Modifier.padding(padding)
        ) {
            composable("menu") { MenuScreen(navController) }
            composable("clientes") { ClientesScreen(clienteViewModel) }
            composable("productos") { ProductosScreen(productoViewModel) }
            composable("ordenes") { OrdenesScreen(ordenViewModel, navController) } // Pasamos el navController
            composable("crear_orden") {
                CrearOrdenScreen(
                    clienteViewModel = clienteViewModel,
                    productoViewModel = productoViewModel,
                    ordenViewModel = ordenViewModel,
                    onOrdenCreada = { navController.popBackStack() }, // Regresamos a la pantalla anterior
                    navController = navController
                )
            }
            composable(
                route = "detalle_orden/{idOrden}",
                arguments = listOf(navArgument("idOrden") { type = NavType.LongType })
            ) { backStackEntry ->
                val idOrden = backStackEntry.arguments?.getLong("idOrden")
                if (idOrden != null) {
                    DetalleOrdenScreen(
                        idOrden = idOrden,
                        ordenViewModel = ordenViewModel,
                        productoViewModel = productoViewModel,
                        clienteViewModel = clienteViewModel
                    )
                }
            }
        }
    }
}