package com.example.examenb1_cesar_duque

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

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

                val (exito,idAplicacion)  = BaseDeDatos
                    .tablas!!.crearAplicacion(

                        nombre.text.toString(),
                        version.text.toString(),
                        tamanoMb.text.toString().toInt(),
                        fechaLanzamiento.text.toString(),
                        categoria.text.toString(),
                        idSO
                    )


                if (exito) {

                    crearAplicacion(
                        idAplicacion.toString(),
                        idSO.toString(),
                        nombre.text.toString(),
                        version.text.toString(),
                        tamanoMb.text.toString().toInt(),
                        fechaLanzamiento.text.toString(),
                        categoria.text.toString())

                    val intent = Intent(this, AListView::class.java)
                    intent.putExtra("id", idSO)
                    intent.putExtra("nombre", nombreSo)
                    startActivity(intent)
                }
            }



    }

    fun crearAplicacion(
        id:String,
        idSO: String,
        nombre: String,
        version: String,
        tamanoMb: Int,
        fechaLanzamiento: String,
        categoria: String
    ){
        val db = Firebase.firestore
        val referenciaAplicacion = db.collection("SistemaOperativo").document(idSO)
            .collection("Aplicacion")

        val datosAplicacion = hashMapOf(
            "nombre" to nombre,
            "version" to version,
            "tamanoMb" to tamanoMb,
            "fechaLanzamiento" to fechaLanzamiento,
            "categoria" to categoria
        )

        // identificador quemado (crear/actualizar)
        referenciaAplicacion
            .document(id)
            .set(datosAplicacion)
            .addOnSuccessListener {  }
            .addOnFailureListener {  }
    }

}