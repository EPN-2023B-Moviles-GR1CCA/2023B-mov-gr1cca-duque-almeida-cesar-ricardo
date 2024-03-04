package com.example.examenb1_cesar_duque

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import java.text.SimpleDateFormat
import java.util.Date

class EditAplicacion : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_aplicacion)
        val nombreSo = intent.getStringExtra("nombreso")
        val id = intent.getIntExtra("id", 0)
        val nombre = intent.getStringExtra("nombre")
        val version = intent.getStringExtra("version")
        val tamanoMb = intent.getIntExtra("tamanoMb", 0)
        val fechaLanzamientoDate = intent.getSerializableExtra("fechaLanzamiento") as Date
        val categoria = intent.getStringExtra("categoria") ?: ""
        val sistemaOperativoId = intent.getIntExtra("sistemaOperativoId", 0)

        // Convierte la fecha a un formato de String
        val formatoFecha = SimpleDateFormat("dd-MM-yyyy") // Puedes ajustar el formato según tus necesidades
        val fechaLanzamientoString = formatoFecha.format(fechaLanzamientoDate)

        // Llena los EditText con la información obtenida
        val editTextNombre = findViewById<EditText>(R.id.input_nombre_aplicacion2)
        editTextNombre.setText(nombre)

        val editTextVersion = findViewById<EditText>(R.id.input_version_aplicacion2)
        editTextVersion.setText(version)

        val editTextFechaLanzamiento = findViewById<EditText>(R.id.input_fecha_aplicacion2)
        editTextFechaLanzamiento.setText(fechaLanzamientoString)

        val editTextTamano = findViewById<EditText>(R.id.input_tamano2)
        editTextTamano.setText(tamanoMb.toString()) // Convertir a String antes de asignar

        val editTextCategoria = findViewById<EditText>(R.id.input_categoria2)
        editTextCategoria.setText(categoria)

        val botonActualizarBDD = findViewById<Button>(R.id.btn_editar_aplicacion)
        botonActualizarBDD.setOnClickListener {
            val nombre = findViewById<EditText>(R.id.input_nombre_aplicacion2)
            val version = findViewById<EditText>(R.id.input_version_aplicacion2)
            val tamanoMb = findViewById<EditText>(R.id.input_tamano2)
            val fechaLanzamiento = findViewById<EditText>(R.id.input_fecha_aplicacion2)
            val categoria = findViewById<EditText>(R.id.input_categoria2)

            // Convierte el texto de tamanoMb a un valor entero
            val tamanoMbInt = tamanoMb.text.toString().toInt()

            // Actualiza la Aplicación en la base de datos
            val respuesta = BaseDeDatos.tablas!!.actualizarAplicacion(
                nombre.text.toString(),
                version.text.toString(),
                tamanoMbInt,
                fechaLanzamiento.text.toString(), // Asegúrate de manejar correctamente la fecha
                categoria.text.toString(),
                sistemaOperativoId,
                id
            )

            if (respuesta){
                val intent = Intent(this, AListView::class.java)
                intent.putExtra("id", sistemaOperativoId)
                intent.putExtra("nombre", nombreSo)
                startActivity(intent)}


        }

    }
    fun irActividad(
        clase: Class<*>
    ){
        val intent = Intent(this, clase)
        startActivity(intent)
    }


}