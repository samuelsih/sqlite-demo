package com.example.sqlitedemo.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.RecyclerView
import com.example.sqlitedemo.EditMahasiswaActivity
import com.example.sqlitedemo.R
import com.example.sqlitedemo.datasource.Mahasiswa
import com.example.sqlitedemo.datasource.MahasiswaDatabase

class MahasiswaViewAdapter(
    private var allMahasiswa: List<Mahasiswa>,
    private var ctx: Context
) : RecyclerView.Adapter<MahasiswaViewAdapter.ViewHolder>() {
    private val db: MahasiswaDatabase = MahasiswaDatabase(ctx)

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val namaTextView = view.findViewById<TextView>(R.id.namaTextView)
        val nrpTextView = view.findViewById<TextView>(R.id.nrpTextView)
        val editButton = view.findViewById<ImageView>(R.id.editButton)
        val deleteButton = view.findViewById<ImageView>(R.id.deleteButton)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater
            .from(parent.context)
            .inflate(R.layout.mahasiswa_item, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return allMahasiswa.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val mahasiswa = allMahasiswa[position]
        holder.namaTextView.text = mahasiswa.nama
        holder.nrpTextView.text = mahasiswa.nrp

        holder.editButton.setOnClickListener {
            val intent = Intent(
                holder.itemView.context,
                EditMahasiswaActivity::class.java).apply {
                    putExtra("nrp", mahasiswa.nrp)
                }

            holder.itemView.context.startActivity(intent)
        }

        holder.deleteButton.setOnClickListener{
            onConfirmationDeleteDialog {
                db.delete(mahasiswa.nrp)
                refresh(db.all())
                Toast.makeText(
                    holder.itemView.context,
                    "Mahasiswa dihapus",
                    Toast.LENGTH_LONG
                )
            }
        }
    }

    fun refresh(newData: List<Mahasiswa>) {
        allMahasiswa = newData
        notifyDataSetChanged()
    }

    private fun onConfirmationDeleteDialog(action: () -> Unit) {
        val builder = AlertDialog.Builder(ctx)
        builder.setTitle("Konfirmasi")
        builder.setMessage("Apakah Anda yakin ingin menghapus ini?")
        builder.setPositiveButton("Ya") { _, _ ->
            action()
        }

        builder.setNegativeButton("Batal") { dialog, _ ->
            dialog.dismiss()
        }

        val dialog = builder.create()
        dialog.show()
    }
}