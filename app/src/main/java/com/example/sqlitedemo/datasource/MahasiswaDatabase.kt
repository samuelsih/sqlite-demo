package com.example.sqlitedemo.datasource

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class MahasiswaDatabase(ctx: Context) : SQLiteOpenHelper(
    ctx,
    DB_NAME,
    null,
    DB_VERSION
) {
    companion object {
        private const val DB_NAME = "mahasiswa.db"
        private const val DB_VERSION = 1
        private const val TABLE_NAME = "mahasiswa"
        private const val COLUMN_NAMA = "nama"
        private const val COLUMN_NRP = "nrp"
    }

    override fun onCreate(db: SQLiteDatabase?) {
        val query = "CREATE TABLE IF NOT EXISTS $TABLE_NAME($COLUMN_NAMA TEXT, $COLUMN_NRP TEXT);"
        db?.execSQL(query)
    }

    override fun onUpgrade(db: SQLiteDatabase?, p1: Int, p2: Int) {
        val query = "DROP TABLE IF EXISTS $TABLE_NAME"
        db?.execSQL(query)
        onCreate(db)
    }

    fun terminate() {
        val db = writableDatabase
        db.close()
    }

    fun save(mahasiswa: Mahasiswa) {
        val contents = ContentValues()
        contents.put(COLUMN_NAMA, mahasiswa.nama)
        contents.put(COLUMN_NRP, mahasiswa.nrp)

        val db = writableDatabase
        db.insert(TABLE_NAME, null, contents)
        db.close()
    }

    fun update(nrp: String, mahasiswa: Mahasiswa) {
        val contents = ContentValues()
        contents.put(COLUMN_NAMA, mahasiswa.nama)
        contents.put(COLUMN_NRP, mahasiswa.nrp)

        val db = writableDatabase
        db.update(
            TABLE_NAME,
            contents,
            "$COLUMN_NRP = ?",
            arrayOf(nrp)
        )
        db.close()
    }

    fun delete(nrp: String) {
        val db = writableDatabase
        db.delete(
            TABLE_NAME,
            "$COLUMN_NRP = ?",
            arrayOf(nrp)
        )
        db.close()
    }

    fun all(): List<Mahasiswa> {
        val db = writableDatabase
        val allMahasiswa = ArrayList<Mahasiswa>()
        val query = "SELECT $COLUMN_NAMA, $COLUMN_NRP FROM $TABLE_NAME"
        var cursor = db.rawQuery(query, null)

        if(cursor.moveToFirst()) {
           do {
               val nama = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_NAMA))
               val nrp = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_NRP))
               val mahasiswa = Mahasiswa(nama, nrp)
               allMahasiswa.add(mahasiswa)
           } while (cursor.moveToNext())
        }

        cursor.close()
        db.close()

        return allMahasiswa
    }

    fun findByNRP(nrp: String): Mahasiswa {
        val db = writableDatabase
        val query = "SELECT $COLUMN_NAMA, $COLUMN_NRP FROM $TABLE_NAME WHERE nrp = ?"
        var cursor = db.rawQuery(query, arrayOf(nrp))
        cursor.moveToFirst()

        val nama = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_NAMA))
        val nrp = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_NRP))
        val mahasiswa = Mahasiswa(nama, nrp)

        cursor.close()
        db.close()

        return mahasiswa
    }
}