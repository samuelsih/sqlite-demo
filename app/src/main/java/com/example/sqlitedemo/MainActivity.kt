package com.example.sqlitedemo

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.sqlitedemo.adapter.MahasiswaViewAdapter
import com.example.sqlitedemo.databinding.ActivityMainBinding
import com.example.sqlitedemo.datasource.MahasiswaDatabase

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var db: MahasiswaDatabase
    private lateinit var mahasiswaViewAdapter: MahasiswaViewAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        db = MahasiswaDatabase(this)
        mahasiswaViewAdapter = MahasiswaViewAdapter(db.all(), this)

        binding.mahasiswaRecyclerView.layoutManager = LinearLayoutManager(this)
        binding.mahasiswaRecyclerView.adapter = mahasiswaViewAdapter

        binding.addMahasiswaButton.setOnClickListener {
            val intent = Intent(this, AddMahasiswaActivity::class.java)
            startActivity(intent)
        }
    }

    override fun onResume() {
        super.onResume()
        mahasiswaViewAdapter.refresh(db.all())
    }

    override fun onStop() {
        super.onStop()
        db.terminate()
    }
}