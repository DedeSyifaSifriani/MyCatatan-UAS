package com.example.mycatatuang.ui.theme

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.mycatatuang.model.TransaksiDao
import com.example.mycatatuang.model.TransaksiRepository
import com.example.mycatatuang.model.TransaksiViewModel

class TransaksiViewModelFactory(
    private val transaksiDao: TransaksiDao
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(TransaksiViewModel::class.java)) {
            val repository = TransaksiRepository(transaksiDao)
            @Suppress("UNCHECKED_CAST")
            return TransaksiViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}

