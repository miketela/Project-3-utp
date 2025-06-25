package com.example.app3.data.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.ForeignKey

@Entity(
    tableName = "ordenes",
    foreignKeys = [
        ForeignKey(
            entity = Cliente::class,
            parentColumns = ["idCliente"],
            childColumns = ["idCliente"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class Orden(
    @PrimaryKey(autoGenerate = true) val idOrden: Long = 0,
    val idCliente: Long,
    val fecha: String
)
