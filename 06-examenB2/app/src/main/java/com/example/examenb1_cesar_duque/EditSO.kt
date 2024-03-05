package com.example.examenb1_cesar_duque

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import java.text.SimpleDateFormat
import java.util.Date

class EditSO : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_so)

        // Obtén el ID y otros atributos de la Intent
        val id = intent.getIntExtra("id", 0)
        val nombre = intent.getStringExtra("nombre")
        val version = intent.getStringExtra("version")
        val fechaLanzamientoDate = intent.getSerializableExtra("fechaLanzamiento") as Date
        val esGratis = intent.getBooleanExtra("esGratis", false)
        val desarrollador = intent.getStringExtra("desarrollador")
        val textoEsGratis = if (esGratis) "Si" else "No"

        // Convierte la fecha a un formato de String
        val formatoFecha = SimpleDateFormat("dd-MM-yyyy") // Puedes ajustar el formato según tus necesidades
        val fechaLanzamientoString = formatoFecha.format(fechaLanzamientoDate)

        // Llena los EditText con la información obtenida
        val editTextNombre = findViewById<EditText>(R.id.input_nombre2)
        editTextNombre.setText(nombre)

        val editTextVersion = findViewById<EditText>(R.id.input_version2)
        editTextVersion.setText(version)

        val editTextFechaLanzamiento = findViewById<EditText>(R.id.input_fechaLanzamiento2)
        editTextFechaLanzamiento.setText(fechaLanzamientoString)

        val editTextEsGratis = findViewById<EditText>(R.id.input_esGratis2)
        editTextEsGratis.setText(textoEsGratis)

        val editTextDesarrollador = findViewById<EditText>(R.id.input_desarrollador2)
        editTextDesarrollador.setText(desarrollador)


        val botonActualizarBDD = findViewById<Button>(R.id.btn_editar_so)
        botonActualizarBDD.setOnClickListener {
            val nombre = findViewById<EditText>(R.id.input_nombre2)
            val version = findViewById<EditText>(R.id.input_version2)
            val fechaLanzamiento = findViewById<EditText>(R.id.input_fechaLanzamiento2)
            val esGratis = findViewById<EditText>(R.id.input_esGratis2)
            val desarrollador = findViewById<EditText>(R.id.input_desarrollador2)

            // Obtén el ID y otros atributos de la Intent
            val idSO = intent.getIntExtra("id", 0)

            // Convierte el texto de esGratis a un valor booleano
            val esGratisBoolean = esGratis.text.toString().equals("si", ignoreCase = true)
            // Convierte el valor booleano a un entero (1 si es true, 0 si es false)
            val esGratisInt = if (esGratisBoolean) 1 else 0

            // Actualiza el Sistema Operativo en la base de datos
            val respuesta = BaseDeDatos.tablas!!.actualizarSistemaOperativo(
                nombre.text.toString(),
                version.text.toString(),
                fechaLanzamiento.text.toString(),
                esGratisInt,
                desarrollador.text.toString(),
                idSO
            )

            if (respuesta) mostrarSnackbar("SO Actualizado")
            editarSO(
                idSO.toString(),
                nombre.text.toString(),
                version.text.toString(),
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
                findViewById(R.id.cl_editso), // view
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
    fun editarSO(
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
        val datosSO = hashMapOf(
            "nombre" to nombre,
            "version" to descripcion,
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