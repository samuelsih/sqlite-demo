package com.example.sqlitedemo

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.sqlitedemo.databinding.ActivityEditMahasiswaBinding
import com.example.sqlitedemo.datasource.Mahasiswa
import com.example.sqlitedemo.datasource.MahasiswaDatabase

class EditMahasiswaActivity: AppCompatActivity() {
    private lateinit var binding: ActivityEditMahasiswaBinding
    private lateinit var db: MahasiswaDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditMahasiswaBinding.inflate(layoutInflater)
        setContentView(binding.root)

        db = MahasiswaDatabase(this)

        val nrpParam = intent.getStringExtra("nrp") ?: ""
        if(nrpParam == "") {
            finish()
            return
        }

        val detailMahasiswa = db.findByNRP(nrpParam)

        binding.namaMahasiswaEditInput.setText(detailMahasiswa.nama)
        binding.nrpMahasiswaEditInput.setText(detailMahasiswa.nrp)

        binding.saveButton.setOnClickListener {
            val nama = binding.namaMahasiswaEditInput.text.toString()
            val nrp = binding.nrpMahasiswaEditInput.text.toString()
            val mahasiswa = Mahasiswa(nama, nrp)
            db.update(detailMahasiswa.nrp, mahasiswa)
            finish()
            Toast
                .makeText(this, "Mahasiswa diedit", Toast.LENGTH_SHORT)
                .show()
        }
    }
}