package com.example.examenb1_cesar_duque

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.ContextMenu
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ListView
import android.widget.TextView
import com.google.android.material.snackbar.Snackbar

class AListView : AppCompatActivity() {
    lateinit var arreglo:ArrayList<Aplicacion>;
    lateinit var nombreSo:String;
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_alist_view)

        // Obtén el ID y otros atributos de la Intent
        val idSO = intent.getIntExtra("id", 0)
        nombreSo = intent.getStringExtra("nombre") ?:""

        val TextNombre = findViewById<TextView>(R.id.nombreSO)
        TextNombre.setText(nombreSo)

        arreglo = BaseDeDatos.tablas!!.obtenerAplicacionesPorSistemaOperativoId(idSO)

        val botonListView = findViewById<Button>(R.id.btn_agregar2)
        botonListView
            .setOnClickListener {
                val intent = Intent(this, CrearAplicacion::class.java)
                intent.putExtra("id", idSO)
                intent.putExtra("nombre", nombreSo)
                startActivity(intent)
            }


        val listView = findViewById<ListView>(R.id.lv_list_view2)
        val adaptador = ArrayAdapter(
            this, // Contexto
            // como se va a ver (XML)
            android.R.layout.simple_list_item_1,
            arreglo
        )
        listView.adapter = adaptador


        adaptador.notifyDataSetChanged()

        registerForContextMenu(listView)
    }

    override fun onCreateContextMenu(
        menu: ContextMenu?,
        v: View?,
        menuInfo: ContextMenu.ContextMenuInfo?
    ) {
        super.onCreateContextMenu(menu, v, menuInfo)
        // Llenamos las opciones del menu
        val inflater = menuInflater
        inflater.inflate(R.menu.menu_contextual_aplicacion, menu)
    }

    override fun onContextItemSelected(item: MenuItem): Boolean {
        // Obtén la posición del elemento seleccionado en el ListView
        val info = item.menuInfo as AdapterView.AdapterContextMenuInfo
        val posicion = info.position

        // Obtén el programa seleccionado
        val programaSeleccionado = arreglo[posicion]

        return when (item.itemId) {
            R.id.mi_editar2 -> {
                val intent = Intent(this, EditAplicacion::class.java)

                // Pasa los atributos a la actividad EditarPrograma
                intent.putExtra("id", programaSeleccionado.id)
                intent.putExtra("nombre", programaSeleccionado.nombre)
                intent.putExtra("version", programaSeleccionado.version)
                intent.putExtra("tamanoMb", programaSeleccionado.tamanoMb)
                intent.putExtra("fechaLanzamiento", programaSeleccionado.fechaLanzamiento)
                intent.putExtra("categoria", programaSeleccionado.categoria)
                intent.putExtra("sistemaOperativoId", programaSeleccionado.sistemaOperativoId)
                intent.putExtra("nombreso", nombreSo)

                startActivity(intent)
                return true
            }
            R.id.mi_eliminar2 -> {
                val respuesta = BaseDeDatos.tablas!!.eliminarAplicacion(programaSeleccionado.id)

                val intent = Intent(this, AListView::class.java)
                intent.putExtra("id",programaSeleccionado.sistemaOperativoId )
                intent.putExtra("nombre", nombreSo)
                startActivity(intent)
                return true
            }
            else -> super.onContextItemSelected(item)
        }
    }

    fun mostrarSnackbar(texto: String) {
        Snackbar
            .make(
                findViewById(R.id.cl_alist), // view
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