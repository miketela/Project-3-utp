package com.example.app3.data.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.ForeignKey

@Entity(
    tableName = "detalle_orden",
    foreignKeys = [
        ForeignKey(
            entity = Orden::class,
            parentColumns = ["idOrden"],
            childColumns = ["idOrden"],
            onDelete = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = Producto::class,
            parentColumns = ["idProducto"],
            childColumns = ["idProducto"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class DetalleOrden(
    @PrimaryKey(autoGenerate = true) val idDetalle: Long = 0,
    val idOrden: Long,
    val idProducto: Long,
    val cantidad: Int
)
