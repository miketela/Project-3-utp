package com.example.app3

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.room.Room
import com.example.app3.data.AppDatabase
import com.example.app3.ui.screens.MainScreen
import com.example.app3.viewmodel.ClienteViewModel
import com.example.app3.viewmodel.ProductoViewModel
import com.example.app3.viewmodel.OrdenViewModel
import com.example.app3.ui.theme.App3Theme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        val db = Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java,
            "app_database"
        ).build()
        val clienteViewModel = ClienteViewModel(db.clienteDao())
        val productoViewModel = ProductoViewModel(db.productoDao())
        val ordenViewModel = OrdenViewModel(db.ordenDao(), db.detalleOrdenDao())
        setContent {
            App3Theme {
                MainScreen(
                    clienteViewModel = clienteViewModel,
                    productoViewModel = productoViewModel,
                    ordenViewModel = ordenViewModel
                )
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    App3Theme {
        Greeting("Android")
    }
}