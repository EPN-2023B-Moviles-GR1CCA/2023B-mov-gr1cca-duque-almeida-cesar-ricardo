import java.io.*
import java.text.SimpleDateFormat
import java.util.*

fun main() {
    val scanner = Scanner(System.`in`)
    val sistemasOperativos = cargarSistemasOperativos("sistemas_operativos.txt")
    val aplicaciones = cargarAplicaciones("aplicacion.txt")

    loop@ while (true) {
        println("\n--- Menú Principal ---")
        println("1. Crear")
        println("2. Leer")
        println("3. Actualizar")
        println("4. Eliminar")
        println("5. Salir")
        print("Ingrese su opción: ")

        when (scanner.nextInt()) {
            1 -> submenuCrear(sistemasOperativos, aplicaciones, scanner)
            2 -> submenuLeer(sistemasOperativos, aplicaciones, scanner)
            3 -> submenuActualizar(sistemasOperativos, aplicaciones, scanner)
            4 -> submenuEliminar(sistemasOperativos, aplicaciones, scanner)
            5 -> {
                guardarSistemasOperativos(sistemasOperativos, "sistemas_operativos.txt")
                guardarAplicaciones(aplicaciones, "aplicacion.txt")
                break@loop
            }
            else -> println("Opción no válida. Intente de nuevo.")
        }
    }

    println("¡Programa finalizado!")
}





//cargas y guardados en los archivos
fun cargarSistemasOperativos(nombreArchivo: String): MutableList<SistemaOperativo> {
    val file = File(nombreArchivo)
    if (file.length() == 0L) {
        return mutableListOf()
    }

    return try {
        ObjectInputStream(FileInputStream(file)).use { stream ->
            stream.readObject() as MutableList<SistemaOperativo>
        }
    } catch (e: Exception) {
        e.printStackTrace()
        mutableListOf()
    }
}

fun guardarSistemasOperativos(sistemasOperativos: MutableList<SistemaOperativo>, nombreArchivo: String) {
    try {
        ObjectOutputStream(FileOutputStream(nombreArchivo)).use { stream ->
            stream.writeObject(sistemasOperativos)
        }
    } catch (e: IOException) {
        e.printStackTrace()
    }
}

fun cargarAplicaciones(nombreArchivo: String): MutableList<Aplicacion> {
    val file = File(nombreArchivo)
    if (file.length() == 0L) {
        return mutableListOf()
    }

    return try {
        ObjectInputStream(FileInputStream(file)).use { stream ->
            stream.readObject() as MutableList<Aplicacion>
        }
    } catch (e: Exception) {
        e.printStackTrace()
        mutableListOf()
    }
}

fun guardarAplicaciones(aplicaciones: MutableList<Aplicacion>, nombreArchivo: String) {
    try {
        ObjectOutputStream(FileOutputStream(nombreArchivo)).use { stream ->
            stream.writeObject(aplicaciones)
        }
    } catch (e: IOException) {
        e.printStackTrace()
    }
}








//SUBMENUS
fun submenuCrear(sistemasOperativos: MutableList<SistemaOperativo>, aplicaciones: MutableList<Aplicacion>, scanner: Scanner) {
    println("\n--- Submenú Crear ---")
    println("1. Crear Aplicación")
    println("2. Crear Sistema Operativo")
    print("Ingrese su opción: ")

    when (scanner.nextInt()) {
        1 -> {
            crearAplicacion(aplicaciones, scanner)
        }
        2 -> {
            val nuevoSistemaOperativo = crearSistemaOperativo(sistemasOperativos, aplicaciones, scanner)
            sistemasOperativos.add(nuevoSistemaOperativo)
            println("Sistema Operativo creado con éxito.")
        }
        else -> println("Opción no válida. Intente de nuevo.")
    }
}

fun submenuLeer(sistemasOperativos: MutableList<SistemaOperativo>, aplicaciones: MutableList<Aplicacion>, scanner: Scanner) {
    println("\n--- Submenú Leer Información ---")
    println("1. Leer Información de un Sistema Operativo")
    println("2. Leer Información de Todas las Aplicaciones")
    print("Ingrese su opción: ")

    when (scanner.nextInt()) {
        1 -> {
            println("\n--- Submenú Leer Información de un Sistema Operativo ---")
            println("Seleccione un Sistema Operativo:")
            imprimirNombresSistemasOperativos(sistemasOperativos)

            print("Ingrese el índice del Sistema Operativo a leer: ")
            val indiceSistemaOperativo = scanner.nextInt()

            if (indiceSistemaOperativo in 0 until sistemasOperativos.size) {
                val sistemaOperativoSeleccionado = sistemasOperativos[indiceSistemaOperativo]
                imprimirInformacionSistemaOperativo(sistemaOperativoSeleccionado)
            } else {
                println("Índice no válido. No se realizó ninguna lectura.")
            }
        }
        2 -> {
            println("\n--- Submenú Leer Información de una Aplicación ---")
            println("Seleccione una Aplicación:")
            imprimirNombresAplicaciones(aplicaciones)

            print("Ingrese el índice de la Aplicación a leer: ")
            val indiceAplicacion = scanner.nextInt()

            if (indiceAplicacion in 0 until aplicaciones.size) {
                val aplicacionSeleccionada = aplicaciones[indiceAplicacion]
                imprimirInformacionAplicacion(aplicacionSeleccionada)
            } else {
                println("Índice no válido. No se realizó ninguna lectura.")
            }
        }
        else -> println("Opción no válida. Intente de nuevo.")
    }
}


fun submenuActualizar(sistemasOperativos: MutableList<SistemaOperativo>, aplicaciones: MutableList<Aplicacion>, scanner: Scanner) {
    println("\n--- Submenú Actualizar ---")
    println("1. Actualizar Información de una Aplicación")
    println("2. Actualizar Información de un Sistema Operativo")
    print("Ingrese su opción: ")

    when (scanner.nextInt()) {
        1 -> {
            println("\n--- Submenú Actualizar Información de una Aplicación ---")
            println("Seleccione una aplicación para actualizar:")
            imprimirNombresAplicaciones(aplicaciones)

            print("Ingrese el índice de la aplicación a actualizar: ")
            val indiceAplicacion = scanner.nextInt()

            if (indiceAplicacion in 0 until aplicaciones.size) {
                val aplicacionSeleccionada = aplicaciones[indiceAplicacion]
                actualizarInformacionAplicacion(aplicacionSeleccionada, scanner)
            } else {
                println("Índice no válido. No se realizó ninguna actualización.")
            }
        }
        2 -> {
            println("\n--- Submenú Actualizar Información de un Sistema Operativo ---")
            println("Seleccione un Sistema Operativo para actualizar:")
            imprimirNombresSistemasOperativos(sistemasOperativos)

            print("Ingrese el índice del Sistema Operativo a actualizar: ")
            val indiceSistemaOperativo = scanner.nextInt()

            if (indiceSistemaOperativo in 0 until sistemasOperativos.size) {
                val sistemaOperativoSeleccionado = sistemasOperativos[indiceSistemaOperativo]
                actualizarInformacionSistemaOperativo(sistemaOperativoSeleccionado, aplicaciones, scanner)
            } else {
                println("Índice no válido. No se realizó ninguna actualización.")
            }
        }
        else -> println("Opción no válida. Intente de nuevo.")
    }
}


fun submenuEliminar(sistemasOperativos: MutableList<SistemaOperativo>, aplicaciones: MutableList<Aplicacion>, scanner: Scanner) {
    println("\n--- Submenú Eliminar ---")
    println("1. Eliminar Aplicación")
    println("2. Eliminar Sistema Operativo")
    print("Ingrese su opción: ")

    when (scanner.nextInt()) {
        1 -> {
            println("\n--- Submenú Eliminar Aplicación ---")
            println("Seleccione una aplicación para eliminar:")
            imprimirNombresAplicaciones(aplicaciones)

            print("Ingrese el índice de la aplicación a eliminar: ")
            val indiceAplicacion = scanner.nextInt()

            if (indiceAplicacion in 0 until aplicaciones.size) {
                eliminarAplicacion(aplicaciones, indiceAplicacion)
                println("Aplicación eliminada con éxito.")
            } else {
                println("Índice no válido. No se realizó ninguna eliminación.")
            }
        }
        2 -> {
            println("\n--- Submenú Eliminar Sistema Operativo ---")
            println("Seleccione un Sistema Operativo para eliminar:")
            imprimirNombresSistemasOperativos(sistemasOperativos)

            print("Ingrese el índice del Sistema Operativo a eliminar: ")
            val indiceSistemaOperativo = scanner.nextInt()

            if (indiceSistemaOperativo in 0 until sistemasOperativos.size) {
                eliminarSistemaOperativo(sistemasOperativos, indiceSistemaOperativo)
                println("Sistema Operativo eliminado con éxito.")
            } else {
                println("Índice no válido. No se realizó ninguna eliminación.")
            }
        }
        else -> println("Opción no válida. Intente de nuevo.")
    }
}



//CRUD

//CREATE

fun crearSistemaOperativo(sistemasOperativos: MutableList<SistemaOperativo>, aplicaciones: MutableList<Aplicacion>, scanner: Scanner): SistemaOperativo {
    println("\n--- Crear Sistema Operativo ---")
    print("Nombre: ")
    val nombre = scanner.next()
    print("Versión: ")
    val version = scanner.next()
    print("Fecha de Lanzamiento (yyyy-MM-dd): ")
    val fechaLanzamientoStr = scanner.next()
    val fechaLanzamiento = SimpleDateFormat("yyyy-MM-dd").parse(fechaLanzamientoStr)
    print("Es Gratis (true/false): ")
    val esGratis = scanner.nextBoolean()
    print("Desarrollador: ")
    val desarrollador = scanner.next()

    // Mostrar lista de aplicaciones disponibles
    println("\nAplicaciones Disponibles:")
    imprimirNombresAplicaciones(aplicaciones)

    print("Ingrese los nombres de las aplicaciones que desea agregar (separados por comas): ")
    val aplicacionesSeleccionadas = scanner.next().split(",").map { it.trim() }

    // Filtrar las aplicaciones seleccionadas de la lista completa
    val aplicacionesAAgregar = aplicaciones.filter { it.nombre in aplicacionesSeleccionadas }.toMutableList()

    // Crear el nuevo sistema operativo con las aplicaciones seleccionadas
    val nuevoSistemaOperativo = SistemaOperativo(nombre, version, fechaLanzamiento, esGratis, desarrollador, aplicacionesAAgregar)

    return nuevoSistemaOperativo
}

fun imprimirNombresAplicaciones(aplicaciones: MutableList<Aplicacion>) {
    aplicaciones.forEachIndexed { index, aplicacion ->
        println("$index. ${aplicacion.nombre}")
    }
}

fun crearAplicacion(aplicaciones: MutableList<Aplicacion>, scanner: Scanner) {
    println("\n--- Crear Aplicación ---")
    print("Nombre: ")
    val nombre = scanner.next()
    print("Versión: ")
    val version = scanner.next()
    print("Tamaño (MB): ")
    val tamanoMb = scanner.nextInt()
    print("Fecha de Lanzamiento (yyyy-MM-dd): ")
    val fechaLanzamientoStr = scanner.next()
    val fechaLanzamiento = SimpleDateFormat("yyyy-MM-dd").parse(fechaLanzamientoStr)
    print("Categoría: ")
    val categoria = scanner.next()

    // Crear nueva aplicación y agregarla a la lista
    val nuevaAplicacion = Aplicacion(nombre, version, tamanoMb, fechaLanzamiento, categoria)
    aplicaciones.add(nuevaAplicacion)

    println("Aplicación creada con éxito.")
}








//READ
fun imprimirNombresSistemasOperativos(sistemasOperativos: MutableList<SistemaOperativo>) {
    sistemasOperativos.forEachIndexed { index, sistemaOperativo ->
        println("$index. ${sistemaOperativo.nombre}")
    }
}

fun imprimirInformacionSistemaOperativo(sistemaOperativo: SistemaOperativo) {
    println("\n--- Información del Sistema Operativo ---")
    println("Nombre: ${sistemaOperativo.nombre}")
    println("Versión: ${sistemaOperativo.version}")
    println("Fecha de Lanzamiento: ${SimpleDateFormat("yyyy-MM-dd").format(sistemaOperativo.fechaLanzamiento)}")
    println("Es Gratis: ${sistemaOperativo.esGratis}")
    println("Desarrollador: ${sistemaOperativo.desarrollador}")

    if (sistemaOperativo.aplicaciones.isNotEmpty()) {
        println("\n--- Aplicaciones ---")
        sistemaOperativo.aplicaciones.forEachIndexed { index, aplicacion ->
            println("$index. ${aplicacion.nombre} - Versión: ${aplicacion.version}")
        }
    } else {
        println("\nNo hay aplicaciones en este sistema operativo.")
    }
}
fun imprimirInformacionAplicacion(aplicacion: Aplicacion) {
    println("--- Información de la Aplicación ---")
    println("Nombre: ${aplicacion.nombre}")
    println("Versión: ${aplicacion.version}")
    println("Tamaño (MB): ${aplicacion.tamanoMb}")
    println("Fecha de Lanzamiento: ${SimpleDateFormat("yyyy-MM-dd").format(aplicacion.fechaLanzamiento)}")
    println("Categoría: ${aplicacion.categoria}")
}







//UPDATE

fun actualizarInformacionAplicacion(aplicacion: Aplicacion, scanner: Scanner) {
    println("\n--- Actualizar Información de una Aplicación ---")
    imprimirInformacionAplicacion(aplicacion)

    println("\nIngrese los nuevos datos para la aplicación:")

    print("Nuevo nombre: ")
    aplicacion.nombre = scanner.next()

    print("Nueva versión: ")
    aplicacion.version = scanner.next()

    print("Nuevo tamaño (MB): ")
    aplicacion.tamanoMb = scanner.nextInt()

    print("Nueva fecha de lanzamiento (yyyy-MM-dd): ")
    val nuevaFechaLanzamientoStr = scanner.next()
    aplicacion.fechaLanzamiento = SimpleDateFormat("yyyy-MM-dd").parse(nuevaFechaLanzamientoStr)

    print("Nueva categoría: ")
    aplicacion.categoria = scanner.next()

    println("Información de la aplicación actualizada con éxito.")
}

fun actualizarInformacionSistemaOperativo(sistemaOperativo: SistemaOperativo, aplicaciones: MutableList<Aplicacion>, scanner: Scanner) {
    println("\n--- Actualizar Información del Sistema Operativo ---")
    println("Sistema Operativo actual:")
    imprimirInformacionSistemaOperativo(sistemaOperativo)

    print("\nIngrese el nuevo nombre: ")
    val nuevoNombre = scanner.next()
    sistemaOperativo.nombre = nuevoNombre

    print("\nIngrese la nueva versión: ")
    val nuevaVersion = scanner.next()
    sistemaOperativo.version = nuevaVersion

    print("Ingrese la nueva fecha de lanzamiento (yyyy-MM-dd): ")
    val nuevaFechaStr = scanner.next()
    val nuevaFechaLanzamiento = SimpleDateFormat("yyyy-MM-dd").parse(nuevaFechaStr)
    sistemaOperativo.fechaLanzamiento = nuevaFechaLanzamiento

    print("¿Es Gratis? (true/false): ")
    val nuevoEsGratis = scanner.nextBoolean()
    sistemaOperativo.esGratis = nuevoEsGratis

    print("Ingrese el nuevo desarrollador: ")
    val nuevoDesarrollador = scanner.next()
    sistemaOperativo.desarrollador = nuevoDesarrollador

    println("\nAplicaciones Disponibles:")
    imprimirNombresAplicaciones(aplicaciones)
    print("Ingrese las nuevas aplicaciones (separados por comas): ")

    val aplicacionesSeleccionadas = scanner.next().split(",").map { it.trim() }
    val aplicacionesAAgregar = aplicaciones.filter { it.nombre in aplicacionesSeleccionadas }
    sistemaOperativo.aplicaciones = aplicacionesAAgregar.toMutableList()

    println("Sistema Operativo actualizado con éxito.")
}








//DELETE
fun eliminarAplicacion(aplicaciones: MutableList<Aplicacion>, indice: Int) {
    // Implementa la lógica para eliminar la aplicación en el índice proporcionado
    aplicaciones.removeAt(indice)
}

fun eliminarSistemaOperativo(sistemasOperativos: MutableList<SistemaOperativo>, indice: Int) {
    // Implementa la lógica para eliminar el sistema operativo en el índice proporcionado
    sistemasOperativos.removeAt(indice)
}

