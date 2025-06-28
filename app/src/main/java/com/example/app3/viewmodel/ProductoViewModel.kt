package com.example.app3.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.app3.data.dao.ProductoDao
import com.example.app3.data.entities.Producto
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class ProductoViewModel(private val productoDao: ProductoDao) : ViewModel() {

    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery

    val productos: StateFlow<List<Producto>> = _searchQuery
        .flatMapLatest { query ->
            if (query.isBlank()) {
                productoDao.getAll()
            } else {
                productoDao.search(query)
            }
        }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    fun onSearchQueryChange(newQuery: String) {
        _searchQuery.value = newQuery
    }

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

    fun getProductoById(idProducto: Long): Flow<Producto> {
        return productoDao.getById(idProducto)
    }
}
