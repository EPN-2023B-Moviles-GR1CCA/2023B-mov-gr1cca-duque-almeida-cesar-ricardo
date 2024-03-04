package com.example.examenb1_cesar_duque

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import java.util.Date

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


                val (exito, idSO)  = BaseDeDatos
                    .tablas!!.crearSO(
                        nombre.text.toString(),
                        descripcion.text.toString(),
                        fechaLanzamiento.text.toString(),
                        esGratisInt, // Convierte el valor de texto a Int
                        desarrollador.text.toString()
                    )
                if (exito) mostrarSnackbar("Ent. Creado")
                // Guardar en Firebase Firestore
                crearSO(
                    idSO.toString(),
                    nombre.text.toString(),
                    descripcion.text.toString(),
                    fechaLanzamiento.text.toString(),
                    esGratisInt,
                    desarrollador.text.toString()
                )
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
    fun crearSO(
        id: String,
        nombre: String,
        descripcion: String,
        fechaLanzamiento: String,
        esGratis: Int,
        desarrollador: String
    ){
        val db = Firebase.firestore
        val referenciaSistemaOperativo = db
            .collection("SistemaOperativo")
        // .document("id_hijo")
        // .collection("estudiante")
        val datosSO = hashMapOf(
            "nombre" to nombre,
            "descripcion" to descripcion,
            "fechaLanzamiento" to fechaLanzamiento,
            "esGratis" to esGratis,
            "desarrollador" to desarrollador
        )

        // identificador quemado (crear/actualizar)
        referenciaSistemaOperativo
            .document(id)
            .set(datosSO)
            .addOnSuccessListener {  }
            .addOnFailureListener {  }
    }
}