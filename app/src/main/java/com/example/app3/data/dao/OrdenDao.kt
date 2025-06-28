package com.example.app3.data.dao

import androidx.room.*
import com.example.app3.data.entities.Orden
import kotlinx.coroutines.flow.Flow

@Dao
interface OrdenDao {
    @Query("SELECT * FROM ordenes")
    fun getAll(): Flow<List<Orden>>

    @Query("SELECT * FROM ordenes WHERE idOrden = :idOrden")
    fun getById(idOrden: Long): Flow<Orden>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(orden: Orden): Long

    @Update
    suspend fun update(orden: Orden)

    @Delete
    suspend fun delete(orden: Orden)
}
