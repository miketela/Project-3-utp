package com.example.app3.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.app3.data.dao.ProductoDao
import com.example.app3.data.entities.Producto
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class ProductoViewModel(private val productoDao: ProductoDao) : ViewModel() {
    val productos: StateFlow<List<Producto>> = productoDao.getAll()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    fun insert(producto: Producto) = viewModelScope.launch {
        Log.d("ProductoViewModel", "Insertando producto: $producto")
        productoDao.insert(producto)
    }

    fun update(producto: Producto) = viewModelScope.launch {
        Log.d("ProductoViewModel", "Actualizando producto: $producto")
        productoDao.update(producto)
    }

    fun delete(producto: Producto) = viewModelScope.launch {
        Log.d("ProductoViewModel", "Eliminando producto: $producto")
        productoDao.delete(producto)
    }
}
