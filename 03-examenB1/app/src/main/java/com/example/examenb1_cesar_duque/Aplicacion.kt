package com.example.examenb1_cesar_duque

import java.util.Date

class Aplicacion(
    var id: Int,
    var nombre: String,
    var version: String,
    var tamanoMb: Int,
    var fechaLanzamiento: Date,
    var categoria: String,
    var sistemaOperativoId: Int
) {
    override fun toString(): String {
        return "Aplicacion(id=$id, nombre='$nombre', version='$version', tamanoMb=$tamanoMb, fechaLanzamiento=$fechaLanzamiento, categoria='$categoria', SistemaOperativoid=${sistemaOperativoId})"
    }
}