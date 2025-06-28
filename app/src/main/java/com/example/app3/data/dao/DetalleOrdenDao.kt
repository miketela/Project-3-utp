package com.example.app3.data.dao

import androidx.room.*
import com.example.app3.data.entities.DetalleOrden
import kotlinx.coroutines.flow.Flow

@Dao
interface DetalleOrdenDao {
    @Query("SELECT * FROM detalle_orden WHERE idOrden = :ordenId")
    fun getByOrdenId(ordenId: Long): Flow<List<DetalleOrden>>

    @Query("SELECT * FROM detalle_orden WHERE idOrden = :ordenId")
    fun getDetallesForOrden(ordenId: Long): Flow<List<DetalleOrden>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(detalle: DetalleOrden): Long

    @Update
    suspend fun update(detalle: DetalleOrden)

    @Delete
    suspend fun delete(detalle: DetalleOrden)
}
