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
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import java.util.Date

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
        // Obtén la posición del elemento seleccionado en el ListView
        val info = item.menuInfo as AdapterView.AdapterContextMenuInfo
        val posicion = info.position

        // Obtén el sistema operativo seleccionado
        val sistemaOperativoSeleccionado = arreglo[posicion]

        return when (item.itemId){
            R.id.mi_editar ->{
                val intent = Intent(this, EditSO::class.java)



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
                val respuesta = BaseDeDatos
                    .tablas!!
                    .eliminarSistemaOperativo(
                        sistemaOperativoSeleccionado.id
                    )
                if (respuesta) mostrarSnackbar("Sistema Operativo Eliminado")
                eliminarSO(sistemaOperativoSeleccionado.id.toString())
                irActividad(SOListView::class.java)
                return true
            }
            R.id.mi_ver_programas ->{
                val intent = Intent(this, AListView::class.java)

                intent.putExtra("id", sistemaOperativoSeleccionado.id)
                intent.putExtra("nombre", sistemaOperativoSeleccionado.nombre)

                startActivity(intent)
                return true
            }
            else -> super.onContextItemSelected(item)
        }
    }
    fun mostrarSnackbar(texto: String) {
        Snackbar
            .make(
                findViewById(R.id.cl_soList), // view
                texto, // texto
                Snackbar.LENGTH_LONG // tiempo
            )
            .show()
    }
    fun eliminarSO(id:String){
        val db = Firebase.firestore
        val referenciaSO = db.collection("SistemaOperativo");

            referenciaSO.document(id).collection("Aplicacion").get()
            .addOnSuccessListener {
                // it => eso (lo que llegue)
                for (aplicacion in it) {
                    referenciaSO.document(id).collection("Aplicacion").document(aplicacion.id)
                        .delete() // elimina
                        .addOnCompleteListener { /* Si todo salio bien*/ }
                        .addOnFailureListener { /* Si algo salio mal*/ }
                }
            }


        referenciaSO
            .document(id)
            .delete() // elimina
            .addOnCompleteListener { /* Si todo salio bien*/ }
            .addOnFailureListener { /* Si algo salio mal*/ }
    }

}