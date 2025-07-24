package com.example.mycatatuang.model

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Delete


@Dao
interface TransaksiDao {
    @Delete
    suspend fun delete(transaksi: Transaksi)

    @Insert
    suspend fun insert(transaksi: Transaksi)

    @Query("SELECT * FROM transaksi ORDER BY tanggal DESC")
    suspend fun getAll(): List<Transaksi>
}
