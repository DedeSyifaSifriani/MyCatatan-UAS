package com.example.mycatatuang.model

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.RecyclerView
import com.example.mycatatuang.R

class TransaksiAdapter(
    private val list: List<Transaksi>,
    private val transaksiViewModel: TransaksiViewModel,
    private val onRefresh: () -> Unit = {}
) : RecyclerView.Adapter<TransaksiAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tvJudul: TextView = view.findViewById(R.id.tvJudul)
        val tvNominal: TextView = view.findViewById(R.id.tvNominal)
        val tvKategori: TextView = view.findViewById(R.id.tvKategori)
        val tvTanggal: TextView = view.findViewById(R.id.tvTanggal)
        val tvTipe: TextView = view.findViewById(R.id.tvTipe)
        val btnHapus: ImageButton = view.findViewById(R.id.btnHapus)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_transaksi, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int = list.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val transaksi = list[position]
        holder.tvJudul.text = transaksi.judul
        holder.tvNominal.text = "Rp${transaksi.nominal}"
        holder.tvKategori.text = transaksi.kategori
        holder.tvTanggal.text = transaksi.tanggal
        holder.tvTipe.text = transaksi.tipe

        holder.btnHapus.setOnClickListener {
            AlertDialog.Builder(holder.itemView.context)
                .setTitle("Hapus Transaksi")
                .setMessage("Yakin ingin menghapus transaksi ini?")
                .setPositiveButton("Hapus") { _, _ ->
                    transaksiViewModel.delete(transaksi)
                    onRefresh() // ini buat refresh list setelah dihapus
                }
                .setNegativeButton("Batal", null)
                .show()
        }
    }
}
