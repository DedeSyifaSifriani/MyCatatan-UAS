package com.example.mycatatuang.model

import android.app.DatePickerDialog
import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.mycatatuang.databinding.ActivityFormTransaksiBinding
import com.example.mycatatuang.ui.theme.TransaksiViewModelFactory
import java.text.SimpleDateFormat
import java.util.*
import android.content.Intent


class FormTransaksiActivity : AppCompatActivity() {

    private lateinit var binding: ActivityFormTransaksiBinding
    private val calendar = Calendar.getInstance()
    private val transaksiViewModel: TransaksiViewModel by viewModels {
        TransaksiViewModelFactory((application as MyApp).db.transaksiDao())
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFormTransaksiBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // ✅ Setup kategori dropdown (MaterialAutoCompleteTextView)
        val kategoriList = listOf("Skincare","Wishlist", "Transportasi", "Gaji", "Self-healing", "Lainnya")
        val kategoriAdapter = ArrayAdapter(this, android.R.layout.simple_dropdown_item_1line, kategoriList)
        binding.spinnerKategori.setAdapter(kategoriAdapter)


        binding.btnHapus.setOnClickListener {
            // Pastikan kamu punya data transaksi yang ingin dihapus
            val transaksi = intent.getParcelableExtra<Transaksi>("data_transaksi")
            if (transaksi != null) {
                transaksiViewModel.delete(transaksi)
                Toast.makeText(this, "Transaksi dihapus", Toast.LENGTH_SHORT).show()
                finish()
            } else {
                Toast.makeText(this, "Tidak ada data untuk dihapus", Toast.LENGTH_SHORT).show()
            }
        }

        // ✅ TOMBOL LIHAT RIWAYAT
        binding.btnRiwayat.setOnClickListener {
            val intent = Intent(this, RiwayatActivity::class.java)
            startActivity(intent)
        }
        // ✅ Setup Tanggal Picker
        val dateFormat = SimpleDateFormat("dd-MM-yyyy", Locale.getDefault())
        binding.etTanggal.setText(dateFormat.format(calendar.time))
        binding.etTanggal.setOnClickListener {
            DatePickerDialog(this, { _, year, month, day ->
                calendar.set(year, month, day)
                binding.etTanggal.setText(dateFormat.format(calendar.time))
            }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH)).show()
        }

        // ✅ Tombol Simpan
        binding.btnSimpan.setOnClickListener {
            val judul = binding.etJudul.text.toString()
            val nominal = binding.etNominal.text.toString()
            val tanggal = binding.etTanggal.text.toString()
            val tipe = if (binding.rbPemasukan.isChecked) "pemasukan" else "pengeluaran"
            val kategori = binding.spinnerKategori.text.toString()


            if (judul.isBlank() || nominal.isBlank() || tanggal.isBlank()) {
                Toast.makeText(this, "Semua field wajib diisi", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val nominalInt = nominal.toIntOrNull()
            if (nominalInt == null || nominalInt <= 0) {
                Toast.makeText(this, "Nominal harus angka > 0", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val transaksi = Transaksi(
                judul = judul,
                nominal = nominalInt,
                tanggal = tanggal,
                kategori = kategori,
                tipe = tipe
            )

            transaksiViewModel.insert(transaksi)
            Log.d("FormTransaksi", "Transaksi disimpan: $transaksi")
            Toast.makeText(this, "Transaksi berhasil disimpan", Toast.LENGTH_SHORT).show()
            finish()
        }
    }
}
