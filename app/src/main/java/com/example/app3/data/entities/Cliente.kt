package com.example.app3.data.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "clientes")
data class Cliente(
    @PrimaryKey(autoGenerate = true) val idCliente: Long = 0,
    val nombre: String,
    val correo: String
)
