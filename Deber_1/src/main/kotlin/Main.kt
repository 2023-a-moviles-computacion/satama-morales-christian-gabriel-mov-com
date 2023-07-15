import java.io.BufferedReader
import java.io.File
import java.io.FileReader
import java.io.IOException
import java.nio.file.Paths
import kotlin.system.exitProcess
/*
* AUTHOR: Christian Satama Morales
*/
class Controlador{

    var identificador: Int?=null
    var nombre: String?=null
    var lastFechaInstalacion: String?=null
    var version: Double?=null
    var isActive: Boolean?=null

    constructor(
        _identificador: Int,
        _nombre: String,
        _lasFechaInstalacion: String,
        _version: Double,
        _isActive: Boolean
    ){
        identificador = _identificador
        nombre = _nombre
        lastFechaInstalacion= _lasFechaInstalacion
        version = _version
        isActive = _isActive
    }

    constructor() {
        println("Por favor ingrese los datos del Controlador")
        print("Ingrese el identificador del Controlador (solo números):")
        var identificador = readLine()?.toInt()

        print("Ingrese el nombre del Controlador:")
        var nombre = readLine()

        print("Ingrese la fecha de instalacion del Controlador en formato (HH:mm-dd/MM/yyyy):")
        var lastFechaInstalacion = readLine()

        print("Ingrese la version del Controlador:")
        var version = readLine()?.toDouble()

        print("El controlador esta Activo? (true or false):")
        var isActive = readLine()?.toBoolean()


        this.identificador = identificador
        this.nombre = nombre
        this.lastFechaInstalacion = lastFechaInstalacion
        this.version = version
        this.isActive = isActive
    }

    constructor(atributos:List<String>) {

        identificador = atributos[0].toInt()
        nombre = atributos[1]
        lastFechaInstalacion= atributos[2]
        version = atributos[3].toDouble()
        isActive = atributos[4].toBoolean()
    }

    fun showControlador(): String {
        val salida: String
        salida = "Controlador{" + "\n" +
                "identificador: $identificador" + ",\n" +
                "nombre: $nombre" + ",\n" +
                "Última fecha de instalación: $lastFechaInstalacion" + ",\n" +
                "version del controlador: $version" + ",\n" +
                "Activo: $isActive" + "\n" +
                "}"
        return salida
    }
    fun showAtributos():String{
        val salida:String
        salida = "Controlador{" + "\n" +
                "1.- Identificador" + ",\n" +
                "2.- Nombre" + ",\n" +
                "3.- Última fecha de instalación" + ",\n" +
                "4.- Version del controlador" + ",\n" +
                "5.- Activo" + "\n" +
                "}"
        return salida
    }
    fun appendToFile(path:String){
        try {
            val file = File(path).appendText(this.showControlador()+",\n")
        } catch (e:IOException){
            println("ERROR: $e")
        }
    }

    fun writeToFile(path:String){
        try {
            val file = File(path).writeText(this.showControlador()+",\n")
        } catch (e:IOException){
            println("ERROR: $e")
        }
    }

}

class Dispositivo {
    var identificador: Int?=null
    var nombre: String?=null
    var puertoUsed: IntArray?=null
    var estado: Boolean?=null
    var upTime: Float?=null
    var idControlador: Int?=null

    constructor(
        _identificador: Int,
        _nombre: String,
        _puertoUsed: IntArray,
        _estado: Boolean,
        _Uptime: Float,
        _idControlador: Int
    ) {
        this.identificador = _identificador
        this.nombre = _nombre
        this.puertoUsed = _puertoUsed
        this.estado = _estado
        this.upTime = _Uptime
        this.idControlador = _idControlador
    }

    constructor( pathControlador:String) {
        println("Por favor ingrese los datos del Dispositivo")
        print("Ingrese el identificador del Dispositivo (solo números):")
        var identificador = readLine()?.toInt()
        print("Ingrese el nombre del Dispositivo (solo números):")
        var nombre = readLine()
        print("Ingrese el número del puerto usado por el Dispositivo (1-10):")
        var puertoUsed = IntArray(10)
        var posicion = readLine()?.toInt()
        try {
            puertoUsed[(posicion?.minus(1))!!] = 1
        } catch (e: Error){
            println("ERROR: $e")
        }
        print("El dispositivo esta encendido? (true or false):")
        var estado = readLine().toBoolean()
        print("Ingrese el tiempo que el dispositivo ha estado encendido (ej 1.23 horas):")
        var Uptime = readLine()?.toFloat()
        println("Seleccione el ID del controlador asociado:")
        var idControlador: Int = 0
        //Llamar a una funcion que liste los controladores
        val controladores = extractObjectsFromFile(pathControlador,true) as ArrayList<Controlador>
        var i = 1
        controladores.forEach {
            print("$i.-")
            println( it.showControlador())
            i++
        }
        val opcion = readLine()?.toInt()
        if (opcion != null) {
            if (opcion <= controladores.size){
                 idControlador = controladores.get(opcion-1).identificador!!
            }
        }

        this.identificador = identificador
        this.nombre = nombre
        this.puertoUsed = puertoUsed
        this.estado = estado
        this.upTime = Uptime
        this.idControlador = idControlador
    }
    constructor(atributos:List<Any>) {
        identificador = atributos[0].toString().toInt()
        nombre = atributos[1] as String
        puertoUsed= atributos[2].toString().replace("[", "").replace("]", "").split(",").map { it.trim().toInt() }.toIntArray()
        estado = atributos[3].toString().toBoolean()
        upTime= atributos[4].toString().toFloat()
        idControlador = atributos[5].toString().toInt()
    }
    fun showDispositivo(): String {
        val salida: String
        salida = "Dispositivo{" + "\n" +
                "identificador: $identificador" + ";\n" +
                "nombre: $nombre" + ";\n" +
                "Puerto usado: ${puertoUsed.contentToString()}" + ";\n" +
                "Estado del dispositivo: $estado" + ";\n" +
                "Tiempo Activo: $upTime" + ";\n" +
                "ID del controlador asociado: $idControlador" + "\n" +
                "}"
        return salida
    }

    fun showAtributos():String{
        val salida:String
        salida = "Controlador{" + "\n" +
                "1.- Identificador" + ",\n" +
                "2.- Nombre" + ",\n" +
                "3.- Puerto usado" + ",\n" +
                "4.- Tiempo Activo" + ",\n" +
                "5.- ID del controlador asociado" + "\n" +
                "}"
        return salida
    }
    fun appendToFile(path:String){
        try {
            val file = File(path).appendText(this.showDispositivo()+",\n")
        } catch (e:IOException){
            println("ERROR: $e")
        }
    }
    fun writeToFile(path:String){
        try {
            val file = File(path).writeText(this.showDispositivo()+",\n")
        } catch (e:IOException){
            println("ERROR: $e")
        }
    }

    override fun toString(): String {
        return "Dispositivo(identificador=$identificador, nombre=$nombre, puertoUsed=${puertoUsed?.contentToString()}, estado=$estado, Uptime=$upTime, idControlador=$idControlador)"
    }

}

fun main(args: Array<String>) {
    while(true){
        println("Escoja la tarea que desea realizar:")
        println(
            "1) Crear una nuevo elemento" + "\n" +
                    "2) Visualizar elementos" + "\n" +
                    "3) Actualizar un elemento" + "\n" +
                    "4) Eliminar un elemento" + "\n" +
                    "PRESIONA X PARA SALIR"
        )
        val opcion = readLine()
        //0 -> controlador | 1 -> Dispositivo
        val filesPath =ArrayList<String>()
        filesPath.add(createFiles(true))
        filesPath.add(createFiles(false))
        when (opcion) {
        1.toString() -> {
            println("Escogiste Crear un nuevo elemento")
            val entidad = whatEntityChosen()
            //var controlador = Controlador(1,"DriverPantalla","21:23-09/07/2023",2.20,true)
            when (entidad) {
                1 -> {
                    //Controlador
                    var controlador = Controlador()
                    controlador.appendToFile(filesPath[0])
                    println("Listo!")
                }

                2 -> {
                    //Dispositivo
                    var dispositivo = Dispositivo(filesPath[0])
                    dispositivo.appendToFile(filesPath[1])
                    println("Listo!")
                }

                else -> {
                    println("Mala elección")
                }
            }

        }

        2.toString() -> {
            println("Ecogiste Visualizar todos los elementos")
            val entidad = whatEntityChosen()
            when(entidad){
                1 ->{
                    //Controlador
                    val controladores = extractObjectsFromFile(filesPath[0],true) as ArrayList<Controlador>
                     extractObjectsFromFile(filesPath[0],true)
                    var i = 1
                    println("Lista de controladores encontrados:")
                    controladores.forEach {
                        print("$i.-")
                        println( it.showControlador())
                        i++
                    }
                }
                2 ->{
                    //Dispositivo
                    val dispositivos = extractObjectsFromFile(filesPath[1],false) as ArrayList<Dispositivo>
                    var i = 1
                    println("Lista de dispositivos encontrados:")
                    dispositivos.forEach {
                        print("$i.-")
                        println( it.showDispositivo())
                        i++
                    }
                }
                else -> {
                    println("Mala elección")
                }
            }
        }

        3.toString() -> {
            println("Escogiste Actualizar un elemento")
            val entidad = whatEntityChosen()
            when(entidad){
                1 -> {
                    //Controlador
                    var controlador = buscarElmento(true,filesPath[0]) as Controlador
                    val idControlador = controlador.identificador
                    println("Seleccione el atributo que desea modificar:")
                    println( controlador.showAtributos())
                    val opcion = readLine()?.toInt()
                    when(opcion){
                        1 -> {
                            print("Ingrese el identificador del Controlador (solo números):")
                            controlador.identificador = readLine()?.toInt()
                            println("Listo!")
                        }
                        2 -> {
                            print("Ingrese el nombre del Controlador:")
                            controlador.nombre = readLine()
                            println("Listo!")
                        }
                        3 -> {
                            print("Ingrese la fecha de instalacion del Controlador en formato (HH:mm-dd/MM/yyyy):")
                            controlador.lastFechaInstalacion = readLine()
                            println("Listo!")
                        }
                        4 -> {
                            print("Ingrese la version del Controlador:")
                            controlador.version = readLine()?.toDouble()
                            println("Listo!")
                        }
                        5 -> {
                            print("El controlador esta Activo? (true or false):")
                            controlador.isActive= readLine()?.toBoolean()
                            println("Listo!")
                        }
                    }
                    var controladores = extractObjectsFromFile(filesPath[0],true) as List<Controlador>
                    controladores = controladores.map {
                        if (it.identificador == idControlador) controlador else it
                    }
                    controladores.forEach{
                        it.showControlador()
                    }
                    sobreEscribirFile(controladores,true,filesPath[0])
                }
                2 -> {
                    //Dispositivo
                    var dispositivo = buscarElmento(false,filesPath[1]) as Dispositivo
                    val idDispositivo = dispositivo.identificador
                    println("Seleccione el atributo que desea modificar:")
                    println( dispositivo.showAtributos())
                    val opcion = readLine()?.toInt()
                    when(opcion){
                        1 -> {
                            print("Ingrese el identificador del Dispositivo (solo números):")
                            dispositivo.identificador = readLine()?.toInt()
                            println("Listo!")
                        }
                        2 -> {
                            print("Ingrese el nombre del Dispositivo:")
                            dispositivo.nombre = readLine()
                            println("Listo!")
                        }
                        3 -> {
                            print("Ingrese el número del puerto usado por el Dispositivo (1-10):")
                            var puertoUsed = IntArray(10)
                            var posicion = readLine()?.toInt()
                            try {
                                puertoUsed[(posicion?.minus(1))!!] = 1
                            } catch (e: Error){
                                println("ERROR: $e")
                            }
                            dispositivo.puertoUsed = puertoUsed
                            println("Listo!")
                        }
                        4 -> {
                            print("El dispositivo esta encendido? (true or false):")
                            dispositivo.estado = readLine()?.toBoolean()
                            println("Listo!")
                        }
                        5 -> {
                            print("Ingrese el tiempo que el dispositivo ha estado encendido (ej 1.23 horas):")
                            dispositivo.upTime= readLine()?.toFloat()
                            println("Listo!")
                        }
                        6 ->{
                            println("Seleccione el ID del controlador asociado:")
                            var idControlador: Int = 0
                            //Llamar a una funcion que liste los controladores
                            val controladores = extractObjectsFromFile(filesPath[0],true) as ArrayList<Controlador>
                            var i = 1
                            controladores.forEach {
                                print("$i.-")
                                println( it.showControlador())
                                i++
                            }
                            val opcion = readLine()?.toInt()
                            if (opcion != null) {
                                if (opcion <= controladores.size){
                                    idControlador = controladores.get(opcion-1).identificador!!
                                }
                            }
                            dispositivo.idControlador = idControlador
                        }
                    }
                    var dispositivos = extractObjectsFromFile(filesPath[1],false) as List<Dispositivo>
                    dispositivos = dispositivos.map {
                        if (it.identificador == idDispositivo) dispositivo else it
                    }
                    dispositivos.forEach{
                        it.showDispositivo()
                    }
                    sobreEscribirFile(dispositivos,false,filesPath[1])
                }
            }
        }

        4.toString() -> {
            println("Escogiste Eliminar un elemento")
            val entidad = whatEntityChosen()
            when(entidad){
                1 -> {
                    //Controlador
                    var controlador = buscarElmento(true,filesPath[0]) as Controlador
                    val idControlador = controlador.identificador
                    var controladores = extractObjectsFromFile(filesPath[0],true) as List<Controlador>
                    val controladoresFiltrados = controladores.filter { it.identificador != idControlador }
                    sobreEscribirFile(controladoresFiltrados,true,filesPath[0])
                    println("Listo!")
                }
                2 -> {
                    //Dispositivo
                    var dispositivo = buscarElmento(false,filesPath[1]) as Dispositivo
                    val idDispositivo = dispositivo.identificador
                    var dispositivos = extractObjectsFromFile(filesPath[1],false) as List<Dispositivo>
                    val dispositivosFiltrados = dispositivos.filter { it.identificador != idDispositivo }
                    sobreEscribirFile(dispositivosFiltrados,false,filesPath[1])
                    println("Listo!")
                }
            }
        }

        "X" -> {
            exitProcess(2)
        }
        "x" -> {
            exitProcess(2)
        }
        else -> {
            println("La opción ingresada no es correcta.")
        }
    }
    }
}
fun whatEntityChosen():Int{
    println("Escoja sobre que entidad desea trabajar:")
    println("1) Controlador" +"\n"+
            "2) Dispositivo")

    val opcion = readLine()
    val salida: Int
    when (opcion){
        1.toString() -> {
            println("Escogiste Controlador")
            salida = 1
        }
        2.toString() -> {
            println("Escogiste Dispositivo")
            salida = 2
        }
        else -> {
            println("La opción ingresada no es correcta.")
            salida = -1
        }
    }
    return salida
}

fun extractObjectsFromFile(
    //Devuelve una lista de objetos
    path: String,
    //true -> Controlador | false -> dispositivo
    type: Boolean
):Any{
    var salida : ArrayList<Any>
    var text = ""
    val regex = Regex("\\{([^}]*)\\}")

    when(type){
        true->{
            //Controlador
            val atributosControladores = ArrayList<List<String>>()
            val controladores = ArrayList<Controlador>()
            val file = File(path)
            val reader = BufferedReader(FileReader(file))
            reader.lines().forEach{
                text += it
            }
            val matchResults = regex.findAll(text)!!
            var iterador =  matchResults.iterator()

            while (iterador.hasNext()){
                val matchResult = iterador.next()
                val atributosControlador = matchResult.groupValues[1].split(",").map { it.substringAfter(": ") }
                atributosControladores.add(atributosControlador)
            }

            atributosControladores.forEach {
             controladores.add(Controlador(it))
            }
            return controladores

        }
        false->{
            //Dispositivo
            val atributosDispositivo = ArrayList<List<Any>>()
            val dispositivos = ArrayList<Dispositivo>()
            val file = File(path)
            val reader = BufferedReader(FileReader(file))
            reader.lines().forEach{
                text += it
            }
            val matchResults = regex.findAll(text)!!
            var iterador =  matchResults.iterator()

            while (iterador.hasNext()){
                val matchResult = iterador.next()
                val atributosControlador = matchResult.groupValues[1].split(";").map { it.substringAfter(": ") }
                atributosDispositivo.add(atributosControlador)
            }
            atributosDispositivo.forEach {
                dispositivos.add(Dispositivo(it))
            }
            return dispositivos
        }
    }
    return -1
}
fun createFiles(tipo: Boolean):String{
    //Tipo->true(Controlador) | Tipo->false(Dispositivo)
    if (tipo){
        //Controlador
        val controladorFilePath = Paths.get(System.getProperty("user.dir")).resolve("controlador.txt").toString()
        val controladorFile = File(controladorFilePath)
        if(!controladorFile.exists()){
            try {
                val createdControlador = controladorFile.createNewFile()
                if(createdControlador){
                    println("Archivo de entidad CONTROLADOR creado exitosamente")
                    return controladorFilePath
                }else{
                    println("No se creo el archivo para CONTROLADOR")
                }
            } catch (e: Exception){
                println("ERROR: $e")
            }
        }
        return controladorFilePath

    }else{
        //Dispositivo
        val dispositivoFilePath = Paths.get(System.getProperty("user.dir")).resolve("Dispositivo.txt").toString()
        val dispositivoFile = File(dispositivoFilePath)
        if(!dispositivoFile.exists()) {
            try {
                val createdDispositivo = dispositivoFile.createNewFile()
                if (createdDispositivo) {
                    println("Archivo de entidad DISPOSITIVO creado exitosamente")
                    return dispositivoFilePath
                } else {
                    println("No se creo el archivo para Dispositivo")
                }
            } catch (e: Exception) {
                println("ERROR: $e")
            }
        }
        return dispositivoFilePath
    }
    return ""
}
fun buscarElmento(tipo: Boolean,path:String):Any{
    when(tipo){
        true -> {
            //Controlador
            println("Seleccione el ID del controlador:")
            var idControlador: Int = 0
            //Llamar a una funcion que liste los controladores
            val controladores = extractObjectsFromFile(path,true) as ArrayList<Controlador>
            var i = 1
            controladores.forEach {
                print("$i.-")
                println( it.showControlador())
                i++
            }
            val opcion = readLine()?.toInt()
            if (opcion != null) {
                if (opcion <= controladores.size){
                    idControlador = controladores.get(opcion-1).identificador!!
                }
            }
            controladores.forEach {
                if (it.identificador == idControlador){
                    return it
                }
            }
        }
        false -> {
            //Dispositivo
            println("Seleccione el ID del DISPOSITIVO:")
            var idDispositivo: Int = 0
            //Llamar a una funcion que liste los controladores
            val dispositivos = extractObjectsFromFile(path,false) as ArrayList<Dispositivo>
            var i = 1
            dispositivos.forEach {
                print("$i.-")
                println( it.showDispositivo())
                i++
            }
            val opcion = readLine()?.toInt()
            if (opcion != null) {
                if (opcion <= dispositivos.size){
                    idDispositivo = dispositivos.get(opcion-1).identificador!!
                }
            }
            dispositivos.forEach {
                if (it.identificador == idDispositivo){
                    return it
                }
            }
        }
    }
    return -1
}
fun sobreEscribirFile(elementos: List<Any>, tipo:Boolean, path:String){
    val file = File(path)
    var _elementos = elementos
    if(file.exists()) {
        file.delete()
    }
    when(tipo){
        true ->{
           //Controlador
            createFiles(true)
            _elementos = _elementos as ArrayList<Controlador>
            _elementos.forEach {
                it.writeToFile(path)
            }
        }
        false ->{
            //Dispositivo
            createFiles(false)
            _elementos = _elementos as ArrayList<Dispositivo>
            _elementos.forEach {
                it.writeToFile(path)
            }
        }
    }

}


