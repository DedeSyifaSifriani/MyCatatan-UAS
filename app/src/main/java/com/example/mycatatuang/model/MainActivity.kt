package com.example.mycatatuang.model

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mycatatuang.R
import android.util.Log
import com.example.mycatatuang.ui.theme.TransaksiViewModelFactory


class MainActivity : AppCompatActivity() {

    private val transaksiViewModel: TransaksiViewModel by viewModels {
        TransaksiViewModelFactory((application as MyApp).db.transaksiDao())
    }

    private lateinit var rvTransaksi: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        rvTransaksi = findViewById(R.id.rvTransaksi)
        rvTransaksi.layoutManager = LinearLayoutManager(this)

        findViewById<Button>(R.id.btnBukaForm).setOnClickListener {
            startActivity(Intent(this, FormTransaksiActivity::class.java))
        }

        loadData()
    }

    override fun onResume() {
        super.onResume()
        loadData()
    }

    private fun loadData() {
        transaksiViewModel.getAll { list ->
            runOnUiThread {
                rvTransaksi.adapter = TransaksiAdapter(list, transaksiViewModel) {
                    loadData()
                }
            }
        }
    }
}
