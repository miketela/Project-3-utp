package com.example.app3.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.app3.data.dao.OrdenDao
import com.example.app3.data.dao.DetalleOrdenDao
import com.example.app3.data.entities.Orden
import com.example.app3.data.entities.DetalleOrden
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class OrdenViewModel(
    private val ordenDao: OrdenDao,
    private val detalleOrdenDao: DetalleOrdenDao
) : ViewModel() {
    val ordenes: StateFlow<List<Orden>> = ordenDao.getAll()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    fun insertOrden(orden: Orden) = viewModelScope.launch {
        ordenDao.insert(orden)
    }

    fun insertDetalle(detalle: DetalleOrden) = viewModelScope.launch {
        detalleOrdenDao.insert(detalle)
    }

    fun getDetallesByOrdenId(ordenId: Long) = detalleOrdenDao.getByOrdenId(ordenId)

    fun updateOrden(orden: Orden) = viewModelScope.launch {
        ordenDao.update(orden)
    }

    fun deleteOrden(orden: Orden) = viewModelScope.launch {
        ordenDao.delete(orden)
    }

    fun insertOrdenConDetalles(orden: Orden, detalles: List<DetalleOrden>, onComplete: () -> Unit) = viewModelScope.launch {
        val ordenId = ordenDao.insert(orden)
        detalles.forEach { detalle ->
            detalleOrdenDao.insert(detalle.copy(idOrden = ordenId))
        }
        onComplete()
    }
}
