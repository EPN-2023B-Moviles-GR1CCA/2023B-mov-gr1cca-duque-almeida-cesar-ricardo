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

class SOListView : AppCompatActivity() {
    val arreglo = BaseDeDatos.tablas!!.obtenerTodosSistemasOperativos()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_solist_view)

        val botonListView = findViewById<Button>(R.id.btn_agregar)
        botonListView
            .setOnClickListener {
                irActividad(CrudSO::class.java)
            }

        val listView = findViewById<ListView>(R.id.lv_list_view)
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
    fun irActividad(
        clase: Class<*>
    ){
        val intent = Intent(this, clase)
        startActivity(intent)
    }



    override fun onCreateContextMenu(
        menu: ContextMenu?,
        v: View?,
        menuInfo: ContextMenu.ContextMenuInfo?
    ) {
        super.onCreateContextMenu(menu, v, menuInfo)
        // Llenamos las opciones del menu
        val inflater = menuInflater
        inflater.inflate(R.menu.menu_contextual, menu)
    }



    override fun onContextItemSelected(item: MenuItem): Boolean {
        return when (item.itemId){
            R.id.mi_editar ->{
                val intent = Intent(this, EditSO::class.java)

                // Obtén la posición del elemento seleccionado en el ListView
                val info = item.menuInfo as AdapterView.AdapterContextMenuInfo
                val posicion = info.position

                // Obtén el sistema operativo seleccionado
                val sistemaOperativoSeleccionado = arreglo[posicion]

                // Pasa los atributos a la actividad EditSO
                intent.putExtra("id", sistemaOperativoSeleccionado.id)
                intent.putExtra("nombre", sistemaOperativoSeleccionado.nombre)
                intent.putExtra("version", sistemaOperativoSeleccionado.version)
                intent.putExtra("fechaLanzamiento", sistemaOperativoSeleccionado.fechaLanzamiento)
                intent.putExtra("esGratis", sistemaOperativoSeleccionado.esGratis)
                intent.putExtra("desarrollador", sistemaOperativoSeleccionado.desarrollador)

                startActivity(intent)
                return true
            }
            R.id.mi_eliminar ->{
                return true
            }
            R.id.mi_ver_programas ->{
                irActividad(CrudSO::class.java)
                return true
            }
            else -> super.onContextItemSelected(item)
        }
    }
}