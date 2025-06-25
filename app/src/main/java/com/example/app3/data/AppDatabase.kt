package com.example.app3.data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.app3.data.dao.ClienteDao
import com.example.app3.data.dao.DetalleOrdenDao
import com.example.app3.data.dao.OrdenDao
import com.example.app3.data.dao.ProductoDao
import com.example.app3.data.entities.Cliente
import com.example.app3.data.entities.DetalleOrden
import com.example.app3.data.entities.Orden
import com.example.app3.data.entities.Producto

@Database(
    entities = [Cliente::class, Producto::class, Orden::class, DetalleOrden::class],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun clienteDao(): ClienteDao
    abstract fun productoDao(): ProductoDao
    abstract fun ordenDao(): OrdenDao
    abstract fun detalleOrdenDao(): DetalleOrdenDao
}
