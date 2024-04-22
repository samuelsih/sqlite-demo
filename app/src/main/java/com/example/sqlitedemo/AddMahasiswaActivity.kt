package com.example.sqlitedemo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.sqlitedemo.databinding.ActivityAddMahasiswaBinding
import com.example.sqlitedemo.datasource.Mahasiswa
import com.example.sqlitedemo.datasource.MahasiswaDatabase

class AddMahasiswaActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAddMahasiswaBinding
    private lateinit var db: MahasiswaDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddMahasiswaBinding.inflate(layoutInflater)
        setContentView(binding.root)

        db = MahasiswaDatabase(this)

        binding.saveButton.setOnClickListener {
            val nama = binding.namaMahasiswaInput.text.toString()
            val nrp = binding.nrpMahasiswaInput.text.toString()
            val mahasiswa = Mahasiswa(nama, nrp)
            db.save(mahasiswa)
            finish()
            Toast
                .makeText(this, "Mahasiswa ditambahkan", Toast.LENGTH_SHORT)
                .show()
        }
    }
}