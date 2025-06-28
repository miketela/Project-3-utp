package com.example.app3.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.app3.data.entities.Producto
import kotlinx.coroutines.flow.Flow

@Dao
interface ProductoDao {
    @Query("SELECT * FROM productos ORDER BY nombreProducto ASC")
    fun getAll(): Flow<List<Producto>>

    @Query("SELECT * FROM productos WHERE idProducto = :idProducto")
    fun getById(idProducto: Long): Flow<Producto>

    @Query("SELECT * FROM productos WHERE nombreProducto LIKE :query || '%' ORDER BY nombreProducto ASC")
    fun search(query: String): Flow<List<Producto>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertAll(productos: List<Producto>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(producto: Producto): Long

    @Update
    suspend fun update(producto: Producto)

    @Delete
    suspend fun delete(producto: Producto)
}
