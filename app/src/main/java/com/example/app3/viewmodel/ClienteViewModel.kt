package com.example.app3.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.app3.data.dao.ClienteDao
import com.example.app3.data.entities.Cliente
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class ClienteViewModel(private val clienteDao: ClienteDao) : ViewModel() {
    val clientes: StateFlow<List<Cliente>> = clienteDao.getAll()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    fun insert(cliente: Cliente) = viewModelScope.launch {
        clienteDao.insert(cliente)
    }

    fun update(cliente: Cliente) = viewModelScope.launch {
        clienteDao.update(cliente)
    }

    fun delete(cliente: Cliente) = viewModelScope.launch {
        clienteDao.delete(cliente)
    }
}
