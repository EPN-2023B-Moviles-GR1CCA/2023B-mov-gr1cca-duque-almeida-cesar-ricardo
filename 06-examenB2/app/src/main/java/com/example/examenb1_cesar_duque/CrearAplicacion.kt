package com.example.examenb1_cesar_duque

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText

class CrearAplicacion : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_crear_aplicacion)

        val idSO = intent.getIntExtra("id", 0)
        val nombreSo = intent.getStringExtra("nombre")

        val botonCrearAplicacion = findViewById<Button>(R.id.btn_crear_Aplicacion)
        botonCrearAplicacion
            .setOnClickListener {
                val nombre = findViewById<EditText>(R.id.input_nombre_aplicacion)
                val version = findViewById<EditText>(R.id.input_version_aplicacion)
                val tamanoMb = findViewById<EditText>(R.id.input_tamano)
                val fechaLanzamiento = findViewById<EditText>(R.id.input_fecha_aplicacion)
                val categoria = findViewById<EditText>(R.id.input_categoria)

                val respuesta = BaseDeDatos
                    .tablas!!.crearAplicacion(
                        nombre.text.toString(),
                        version.text.toString(),
                        tamanoMb.text.toString().toInt(),
                        fechaLanzamiento.text.toString(),
                        categoria.text.toString(),
                        idSO
                    )
                if (respuesta) {
                    val intent = Intent(this, AListView::class.java)
                    intent.putExtra("id", idSO)
                    intent.putExtra("nombre", nombreSo)
                    startActivity(intent)
                }
            }



    }

}