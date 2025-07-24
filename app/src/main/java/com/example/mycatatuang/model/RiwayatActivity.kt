package com.example.mycatatuang.model

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mycatatuang.databinding.ActivityRiwayatBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class RiwayatActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRiwayatBinding
    private lateinit var database: AppDatabase
    private lateinit var transaksiViewModel: TransaksiViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRiwayatBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Inisialisasi database & ViewModel dengan Repository
        database = AppDatabase.getDatabase(this)
        val repository = TransaksiRepository(database.transaksiDao())
        transaksiViewModel = TransaksiViewModel(repository)

        // Inisialisasi layout RecyclerView
        binding.recyclerRiwayat.layoutManager = LinearLayoutManager(this)

        // Ambil data transaksi dan tampilkan
        ambilSemuaData()
    }

    private fun ambilSemuaData() {
        transaksiViewModel.getAll { data ->
            runOnUiThread {
                val adapter = TransaksiAdapter(data, transaksiViewModel) {
                    ambilSemuaData() // refresh saat hapus
                }
                binding.recyclerRiwayat.adapter = adapter
            }
        }
    }
}
