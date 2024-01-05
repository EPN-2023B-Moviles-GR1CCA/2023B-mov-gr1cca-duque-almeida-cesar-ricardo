import java.io.Serializable
import java.util.Date

data class SistemaOperativo(
    var nombre: String,
    var version: String,
    var fechaLanzamiento: Date,
    var esGratis: Boolean,
    var desarrollador: String,
    var aplicaciones: MutableList<Aplicacion> = mutableListOf()
) : Serializable
