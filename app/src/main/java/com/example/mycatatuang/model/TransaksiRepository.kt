package com.example.mycatatuang.model

class TransaksiRepository(private val transaksiDao: TransaksiDao) {
    suspend fun getAll(): List<Transaksi> = transaksiDao.getAll()
    suspend fun insert(transaksi: Transaksi) = transaksiDao.insert(transaksi)
    suspend fun delete(transaksi: Transaksi) = transaksiDao.delete(transaksi)
}

