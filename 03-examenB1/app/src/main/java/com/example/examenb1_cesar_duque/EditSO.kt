package com.example.examenb1_cesar_duque

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText

class EditSO : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_so)

        val id = intent.getIntExtra("id", 0) // el segundo parámetro es el valor por defecto
        val nombre = intent.getStringExtra("nombre")
        val version = intent.getStringExtra("version")
        val fechaLanzamiento = intent.getStringExtra("fechaLanzamiento")
        val esGratis = intent.getBooleanExtra("esGratis", false)
        val desarrollador = intent.getStringExtra("desarrollador")

        // Llena los EditText con la información obtenida
        val editTextNombre = findViewById<EditText>(R.id.input_nombre2)
        editTextNombre.setText(nombre)

        val editTextVersion = findViewById<EditText>(R.id.input_version2)
        editTextVersion.setText(version)

        val editTextFechaLanzamiento = findViewById<EditText>(R.id.input_fechaLanzamiento2)
        editTextFechaLanzamiento.setText(fechaLanzamiento)

        val editTextEsGratis = findViewById<EditText>(R.id.input_esGratis2)
        editTextEsGratis.setText(esGratis.toString())

        val editTextDesarrollador = findViewById<EditText>(R.id.input_desarrollador2)
        editTextDesarrollador.setText(desarrollador)

    }
}