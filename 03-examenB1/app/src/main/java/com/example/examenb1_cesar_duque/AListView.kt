package com.example.examenb1_cesar_duque

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import android.widget.TextView

class AListView : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_alist_view)

        // Obtén el ID y otros atributos de la Intent
        val idSO = intent.getIntExtra("id", 0)
        val nombreSo = intent.getStringExtra("nombre")

        val TextNombre = findViewById<TextView>(R.id.nombreSO)
        TextNombre.setText(nombreSo)





    }
}