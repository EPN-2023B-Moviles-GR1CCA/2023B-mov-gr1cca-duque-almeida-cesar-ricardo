package com.example.examenb1_cesar_duque

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class ESqliteHelperExamen (
    contexto: Context?, // THIS
) : SQLiteOpenHelper(
    contexto,
    "Examen", // nombre BDD
    null,
    1
) {
    override fun onCreate(db: SQLiteDatabase?) {
        val scriptSQLCrearTablaSO =
            """
               CREATE TABLE SistemaOperativo(
               id INTEGER PRIMARY KEY AUTOINCREMENT,
               nombre VARCHAR(50),
               version VARCHAR(50),
               fechaLanzamiento VARCHAR(50), 
               esGratis INTEGER,
               desarrollador VARCHAR(50)
               ) 
            """.trimIndent()
        db?.execSQL(scriptSQLCrearTablaSO)

        val scriptSQLCrearTablaAplicacion =
                """
                   CREATE TABLE Aplicacion(
            id INTEGER PRIMARY KEY AUTOINCREMENT,
            nombre VARCHAR(50),
            version VARCHAR(50),
            tamanoMb INTEGER,
            fechaLanzamiento VARCHAR(50),
            categoria VARCHAR(50),
            sistemaOperativoId INTEGER,
            FOREIGN KEY (sistemaOperativoId) REFERENCES SistemaOperativo(id)
            ) 
                """.trimIndent()
        db?.execSQL(scriptSQLCrearTablaAplicacion)
    }

    override fun onUpgrade(p0: SQLiteDatabase?,
                           p1: Int,
                           p2: Int) {}

    fun crearSO(
        nombre: String,
        version: String,
        fechaLanzamiento: String,
        esGratis: Int,
        desarrollador: String
    ): Pair<Boolean, Long>{
        val basedatosEscritura = writableDatabase
        val valoresAGuardar = ContentValues().apply {
            put("nombre", nombre)
            put("version", version)
            put("fechaLanzamiento", fechaLanzamiento)
            put("esGratis", esGratis)
            put("desarrollador", desarrollador)
        }

        val resultadoGuardar = basedatosEscritura.insert(
            "SistemaOperativo",
            null,
            valoresAGuardar
        )

        basedatosEscritura.close()
        val exitoso = resultadoGuardar.toInt() != -1
        val idDatoInsertado = if (exitoso) resultadoGuardar else -1L // Obtiene el ID del último dato insertado
        return Pair(exitoso, idDatoInsertado)
    }


    fun crearAplicacion(
        nombre: String,
        version: String,
        tamanoMb: Int,
        fechaLanzamiento: String,
        categoria: String,
        sistemaOperativoId: Int // Deberías pasar el ID del sistema operativo asociado
    ): Boolean {
        val basedatosEscritura = writableDatabase
        val valoresAGuardar = ContentValues().apply {
            put("nombre", nombre)
            put("version", version)
            put("tamanoMb", tamanoMb)
            put("fechaLanzamiento", fechaLanzamiento)
            put("categoria", categoria)
            put("sistemaOperativoId", sistemaOperativoId)
        }

        val resultadoGuardar = basedatosEscritura.insert(
            "Aplicacion",
            null,
            valoresAGuardar
        )

        basedatosEscritura.close()
        return resultadoGuardar.toInt() != -1
    }

    fun eliminarSistemaOperativo(id: Int): Boolean {
        val conexionEscritura = writableDatabase
        val parametrosConsultaDelete = arrayOf(id.toString())
        val resultadoEliminacion = conexionEscritura
            .delete(
                "SistemaOperativo",
                "id=?",
                parametrosConsultaDelete
            )
        conexionEscritura.close()
        return resultadoEliminacion != -1
    }
    fun eliminarAplicacion(id: Int): Boolean {
        val conexionEscritura = writableDatabase
        val parametrosConsultaDelete = arrayOf(id.toString())
        val resultadoEliminacion = conexionEscritura
            .delete(
                "Aplicacion",
                "id=?",
                parametrosConsultaDelete
            )
        conexionEscritura.close()
        return resultadoEliminacion != -1
    }
    fun actualizarSistemaOperativo(
        nombre: String,
        version: String,
        fechaLanzamiento: String,
        esGratis: Int,
        desarrollador: String,
        id: Int
    ): Boolean {
        val conexionEscritura = writableDatabase
        val valoresAActualizar = ContentValues().apply {
            put("nombre", nombre)
            put("version", version)
            put("fechaLanzamiento", fechaLanzamiento)
            put("esGratis", esGratis)
            put("desarrollador", desarrollador)
        }

        val parametrosConsultaActualizar = arrayOf(id.toString())
        val resultadoActualizacion = conexionEscritura
            .update(
                "SistemaOperativo",
                valoresAActualizar,
                "id=?",
                parametrosConsultaActualizar
            )
        conexionEscritura.close()
        return resultadoActualizacion != -1
    }




    fun actualizarAplicacion(
        nombre: String,
        version: String,
        tamanoMb: Int,
        fechaLanzamiento: String,
        categoria: String,
        sistemaOperativoId: Int,
        id: Int
    ): Boolean {
        val conexionEscritura = writableDatabase
        val valoresAActualizar = ContentValues().apply {
            put("nombre", nombre)
            put("version", version)
            put("tamanoMb", tamanoMb)
            put("fechaLanzamiento", fechaLanzamiento)
            put("categoria", categoria)
            put("sistemaOperativoId", sistemaOperativoId)
        }

        val parametrosConsultaActualizar = arrayOf(id.toString())
        val resultadoActualizacion = conexionEscritura
            .update(
                "Aplicacion",
                valoresAActualizar,
                "id=?",
                parametrosConsultaActualizar
            )
        conexionEscritura.close()
        return resultadoActualizacion != -1
    }





    fun consultarSOPorID(id: Int): SistemaOperativo {
        val baseDatosLectura = readableDatabase
        val scriptConsultaLectura = """
        SELECT * FROM SistemaOperativo WHERE id = ?
    """.trimIndent()
        val parametrosConsultaLectura = arrayOf(id.toString())
        val resultadoConsultaLectura = baseDatosLectura.rawQuery(
            scriptConsultaLectura, // Consulta
            parametrosConsultaLectura // Parámetros
        )

        val existeSistemaOperativo = resultadoConsultaLectura.moveToFirst()
        val sistemaOperativoEncontrado = SistemaOperativo(0, "", "", Date(), false, "") // Asegúrate de ajustar el constructor según tu clase SistemaOperativo

        if (existeSistemaOperativo) {
            do {
                val id = resultadoConsultaLectura.getInt(0)
                val nombre = resultadoConsultaLectura.getString(1)
                val version = resultadoConsultaLectura.getString(2)
                val fechaLanzamientoString = resultadoConsultaLectura.getString(3)
                val esGratis = resultadoConsultaLectura.getInt(4) == 1
                val desarrollador = resultadoConsultaLectura.getString(5)

                // Convertir la fecha de lanzamiento de String a Date
                val fechaLanzamiento = SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).parse(fechaLanzamientoString)

                sistemaOperativoEncontrado.id = id
                sistemaOperativoEncontrado.nombre = nombre
                sistemaOperativoEncontrado.version = version
                sistemaOperativoEncontrado.fechaLanzamiento = fechaLanzamiento
                sistemaOperativoEncontrado.esGratis = esGratis
                sistemaOperativoEncontrado.desarrollador = desarrollador

            } while (resultadoConsultaLectura.moveToNext())
        }

        resultadoConsultaLectura.close()
        baseDatosLectura.close()
        return sistemaOperativoEncontrado
    }

    fun consultarAplicacionPorID(id: Int): Aplicacion {
        val baseDatosLectura = readableDatabase
        val scriptConsultaLectura = """
        SELECT * FROM Aplicacion WHERE id = ?
    """.trimIndent()
        val parametrosConsultaLectura = arrayOf(id.toString())
        val resultadoConsultaLectura = baseDatosLectura.rawQuery(
            scriptConsultaLectura, // Consulta
            parametrosConsultaLectura // Parámetros
        )

        val existeAplicacion = resultadoConsultaLectura.moveToFirst()
        val aplicacionEncontrada = Aplicacion(0, "", "", 0, Date(), "", 0) // Asegúrate de ajustar el constructor según tu clase Aplicacion

        if (existeAplicacion) {
            do {
                val id = resultadoConsultaLectura.getInt(0)
                val nombre = resultadoConsultaLectura.getString(1)
                val version = resultadoConsultaLectura.getString(2)
                val tamanoMb = resultadoConsultaLectura.getInt(3)
                val fechaLanzamientoString = resultadoConsultaLectura.getString(4)
                val categoria = resultadoConsultaLectura.getString(5)
                val sistemaOperativoId = resultadoConsultaLectura.getInt(6)

                // Convertir la fecha de lanzamiento de String a Date
                val fechaLanzamiento = SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).parse(fechaLanzamientoString)

                aplicacionEncontrada.id = id
                aplicacionEncontrada.nombre = nombre
                aplicacionEncontrada.version = version
                aplicacionEncontrada.tamanoMb = tamanoMb
                aplicacionEncontrada.fechaLanzamiento = fechaLanzamiento
                aplicacionEncontrada.categoria = categoria
                aplicacionEncontrada.sistemaOperativoId = sistemaOperativoId

            } while (resultadoConsultaLectura.moveToNext())
        }

        resultadoConsultaLectura.close()
        baseDatosLectura.close()
        return aplicacionEncontrada
    }

    fun obtenerTodosSistemasOperativos(): ArrayList<SistemaOperativo> {
        val sistemasOperativos = ArrayList<SistemaOperativo>()
        val baseDatosLectura = readableDatabase
        val scriptConsultaLectura = "SELECT * FROM SistemaOperativo"
        val resultadoConsultaLectura = baseDatosLectura.rawQuery(scriptConsultaLectura, null)

        if (resultadoConsultaLectura.moveToFirst()) {
            do {
                val id = resultadoConsultaLectura.getInt(0)
                val nombre = resultadoConsultaLectura.getString(1)
                val version = resultadoConsultaLectura.getString(2)
                val fechaLanzamientoString = resultadoConsultaLectura.getString(3)
                val esGratis = resultadoConsultaLectura.getInt(4) == 1
                val desarrollador = resultadoConsultaLectura.getString(5)

                // Convertir la fecha de lanzamiento de String a Date
                val fechaLanzamiento = SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).parse(fechaLanzamientoString)

                val sistemaOperativo = SistemaOperativo(id, nombre, version, fechaLanzamiento, esGratis, desarrollador)
                sistemasOperativos.add(sistemaOperativo)

            } while (resultadoConsultaLectura.moveToNext())
        }

        resultadoConsultaLectura.close()
        baseDatosLectura.close()

        return sistemasOperativos
    }

    fun obtenerAplicacionesPorSistemaOperativoId(sistemaOperativoId: Int): ArrayList<Aplicacion> {
        val aplicaciones = ArrayList<Aplicacion>()
        val baseDatosLectura = readableDatabase

        val scriptConsultaLectura = "SELECT * FROM Aplicacion WHERE sistemaOperativoId = ?"
        val parametrosConsultaLectura = arrayOf(sistemaOperativoId.toString())

        val resultadoConsultaLectura = baseDatosLectura.rawQuery(scriptConsultaLectura, parametrosConsultaLectura)

        if (resultadoConsultaLectura.moveToFirst()) {
            do {
                val id = resultadoConsultaLectura.getInt(0)
                val nombre = resultadoConsultaLectura.getString(1)
                val version = resultadoConsultaLectura.getString(2)
                val tamanoMb = resultadoConsultaLectura.getInt(3)
                val fechaLanzamientoString = resultadoConsultaLectura.getString(4)
                val categoria = resultadoConsultaLectura.getString(5)

                // Convertir la fecha de lanzamiento de String a Date
                val fechaLanzamiento = SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).parse(fechaLanzamientoString)

                val aplicacion = Aplicacion(id, nombre, version, tamanoMb, fechaLanzamiento, categoria, sistemaOperativoId)
                aplicaciones.add(aplicacion)

            } while (resultadoConsultaLectura.moveToNext())
        }

        resultadoConsultaLectura.close()
        baseDatosLectura.close()

        return aplicaciones
    }














}