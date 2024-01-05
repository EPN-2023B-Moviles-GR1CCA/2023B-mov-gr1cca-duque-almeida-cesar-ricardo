import java.io.Serializable
import java.util.Date

data class Aplicacion(
    var nombre: String,
    var version: String,
    var tamanoMb: Int,
    var fechaLanzamiento: Date,
    var categoria: String
) : Serializable