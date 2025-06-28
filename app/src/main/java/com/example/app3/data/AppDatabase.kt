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
import androidx.sqlite.db.SupportSQLiteDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

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

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: android.content.Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = androidx.room.Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "app_database"
                )
                .addCallback(object : Callback() {
                    override fun onCreate(db: SupportSQLiteDatabase) {
                        super.onCreate(db)
                        // Precargar datos en una corutina
                        INSTANCE?.let {
                            CoroutineScope(Dispatchers.IO).launch {
                                it.productoDao().insertAll(PREPOPULATE_DATA)
                            }
                        }
                    }
                })
                .build()
                INSTANCE = instance
                instance
            }
        }

        // Lista de productos Apple para precargar
        private val PREPOPULATE_DATA = listOf(
            Producto(nombreProducto = "iPhone 15", precio = 799.0),
            Producto(nombreProducto = "iPhone 15 Plus", precio = 899.0),
            Producto(nombreProducto = "iPhone 15 Pro", precio = 999.0),
            Producto(nombreProducto = "iPhone 15 Pro Max", precio = 1199.0),
            Producto(nombreProducto = "MacBook Air 13\" M1", precio = 649.0),
            Producto(nombreProducto = "MacBook Air 13\" M3", precio = 1200.0),
            Producto(nombreProducto = "MacBook Air 15\" M3", precio = 1619.0),
            Producto(nombreProducto = "iPad Pro 11\" M4", precio = 999.0),
            Producto(nombreProducto = "iPad Pro 13\" M4", precio = 1977.0),
            Producto(nombreProducto = "Apple Watch SE", precio = 319.95),
            Producto(nombreProducto = "Apple Watch Series 9", precio = 519.96),
            Producto(nombreProducto = "Apple Watch Ultra 2", precio = 979.96)
        )
    }
}
