package com.example.examenb1_cesar_duque

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
// Base de datos sqlite
        BaseDeDatos.tablas = ESqliteHelperExamen(this)

        val botonListView = findViewById<Button>(R.id.btn_so)
        botonListView
            .setOnClickListener {
                irActividad(SOListView::class.java)
            }

        }

    fun irActividad(
        clase: Class<*>
    ){
        val intent = Intent(this, clase)
        startActivity(intent)
    }
}
