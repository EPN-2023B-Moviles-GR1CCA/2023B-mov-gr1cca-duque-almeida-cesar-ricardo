package com.example.examenb1_cesar_duque

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import com.google.android.material.snackbar.Snackbar

class CrudSO : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_crud_so)

        val botonCrearSO = findViewById<Button>(R.id.btn_crear_so)
        botonCrearSO
            .setOnClickListener {
                val nombre = findViewById<EditText>(R.id.input_nombre)
                val descripcion = findViewById<EditText>(R.id.input_version)
                val fechaLanzamiento = findViewById<EditText>(R.id.input_fechaLanzamiento)
                val esGratis = findViewById<EditText>(R.id.input_esGratis)
                val desarrollador = findViewById<EditText>(R.id.input_desarrollador)

                val esGratisInt = if (esGratis.text.toString().equals("si", ignoreCase = true)) 1 else 0


                val respuesta = BaseDeDatos
                    .tablas!!.crearSO(
                        nombre.text.toString(),
                        descripcion.text.toString(),
                        fechaLanzamiento.text.toString(),
                        esGratisInt, // Convierte el valor de texto a Int
                        desarrollador.text.toString()
                    )
                if (respuesta) mostrarSnackbar("Ent. Creado")
                irActividad(SOListView::class.java)

            }

    }
    fun mostrarSnackbar(texto: String) {
        Snackbar
            .make(
                findViewById(R.id.cl_crudso), // view
                texto, // texto
                Snackbar.LENGTH_LONG // tiempo
            )
            .show()
    }
    fun irActividad(
        clase: Class<*>
    ){
        val intent = Intent(this, clase)
        startActivity(intent)
    }
}