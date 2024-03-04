package com.example.examenb1_cesar_duque

import java.util.Date

class SistemaOperativo (
    var id: Int,
    var nombre: String,
    var version: String,
    var fechaLanzamiento: Date,
    var esGratis: Boolean,
    var desarrollador: String
){
    override fun toString(): String {
                            return "$nombre - $version"
    }
}