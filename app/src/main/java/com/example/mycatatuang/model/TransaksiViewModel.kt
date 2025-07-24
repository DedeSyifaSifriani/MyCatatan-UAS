package com.example.mycatatuang.model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class TransaksiViewModel(private val repository: TransaksiRepository) : ViewModel() {

    fun getAll(callback: (List<Transaksi>) -> Unit) {
        viewModelScope.launch(Dispatchers.IO) {
            val result = repository.getAll()
            callback(result)
        }
    }

    fun insert(transaksi: Transaksi) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.insert(transaksi)
        }
    }

    fun delete(transaksi: Transaksi) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.delete(transaksi)
        }
    }
}
