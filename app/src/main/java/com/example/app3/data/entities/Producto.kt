package com.example.app3.data.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "productos")
data class Producto(
    @PrimaryKey(autoGenerate = true) val idProducto: Long = 0,
    val nombreProducto: String,
    val precio: Double
)
