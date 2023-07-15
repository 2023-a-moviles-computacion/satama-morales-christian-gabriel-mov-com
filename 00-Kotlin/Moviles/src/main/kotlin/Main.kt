import java.util.*
import kotlin.collections.ArrayList

fun main(args: Array<String>) {
    println("Hello World!")

    //tipos de variables
    val inmutable : String = "Christian"
    //inmutable = "Gabriel"

    //Mutables (re asignar)
    var mutable : String = "Christian"
    mutable = "Gabriel"

    //val >var

    //Duck typing

    val ejemploVarible = " Christian Satama "
    val edadEjemplo : Int = 12
    ejemploVarible.trim()
    //ejemploVariable = edadEjemplo;

    //Varible primitiva
    val nombreProfesor: String = "Adrian Eguez"
    val suedo: Double = 1.2
    val estadoCivil= 'C'
    val fechaNacimiento: Date = Date()

    //switch
    val estadoCivilWhen = "C"
    when (estadoCivilWhen){
        ("C") -> {
            println("Casado")
        }
        "S" -> {
            println("Soltero")
        }
        else -> {
            print("No sabemos")
        }

    }
     val coqueteo = if (estadoCivilWhen == "S") "Si" else "No"

    //void ->Unit
    fun imprimirNombre (nombre : String): Unit {
        print("Nombre : ${nombre}")
    }

    fun calculaSueldo(
        sueldo:Double,
        tasa:Double=12.00,
        bonoEspecial: Double? = null,
    ): Double {
        if (bonoEspecial == null) {
            return sueldo * (100 / tasa)
        } else {
            return sueldo * (100 / tasa) + bonoEspecial
        }
    }

    val sumaUno = Suma(1,1)
    val sumaDos = Suma(null,1)
    val sumaTres = Suma(1,null)

    //ARREGLOS

    //Tipos de arreglos
    //Arreglos estaticos

    val arregloEstatico: Array<Int> = arrayOf<Int>(1,2,3)
    println(arregloEstatico)


    //Arreglos Dinamicos

    val arregloDinamico: ArrayList<Int> = arrayListOf<Int>(1,2,3,4,5,6,7)
    println(arregloDinamico)
    arregloDinamico.add(11)
    arregloDinamico.add(12)
    println(arregloDinamico)

    //Operadores -> Sirven para los arreglos estaticos y dinamicos

    //FOR EACH -> Unit
    //Iterar un arreglo

    val respuestaForEach: Unit = arregloDinamico
        .forEach{ valorActual: Int ->
        println("Valor actual: ${valorActual}")
    }
    arregloDinamico.forEach{println(it)} // it significa el elemento actual dentro del bucle

    arregloEstatico
        .forEachIndexed{ indice: Int, valorActual: Int ->
            println("Valor ${valorActual} Indice: ${indice}")
        }
    println(respuestaForEach)

    //MAP -> Muta el arreglo (Cambia el arreglo)
    //1) Enviamos el nuevo valor de la iteracion
    ///2) Nos devuelve un NUEVO ARREGLO con los valores modificados

    val respuestaMap: List<Double> = arregloDinamico
        .map{valorActual: Int ->
            return@map valorActual.toDouble() + 100.00
        }
    println(respuestaMap)

    val respuestaMapDos = arregloDinamico.map { it + 15}

    //Filter ->FILTRAR EL ARREGLO

    //1)Devolver una expresion (TRUE OR FALSE)
    //2) Nuevo arreglo Filtrado
    val respuestaFilter: List<Int> = arregloDinamico
        .filter {valorActual: Int ->
            val mayoresACinco: Boolean = valorActual > 5 //Expresion Condicion
            return@filter mayoresACinco
        }
    val respuestaFilterDos = arregloDinamico.filter { it <= 5 }
    println(respuestaFilter)
    println(respuestaFilterDos)

    //OR AND
    //OR ->ANY (Alguno cumple?)
    //AND -> ALL (Todos cumplen?)

    val respuestaAny: Boolean = arregloDinamico
        .any{valorActual: Int ->
            return@any (valorActual > 5)
        }
    println(respuestaAny) //true

    val respuestaAll: Boolean = arregloDinamico
        .all{valorActual: Int ->
            return@all (valorActual > 5)
        }
    println(respuestaAll) //false

    //Reduce -> Valor acumulado
    val respuestaReduce: Int = arregloDinamico
        .reduce{ //acumulado = 0 -> siempre empieza en 0
            acumulado: Int, valorActual: Int ->
            return@reduce (acumulado + valorActual) //Logica de negocio
        }
    println(respuestaReduce)
}

abstract class NumerosJava{
    protected val numeroUno: Int
    protected val numeroDos: Int
    constructor(
        uno:Int,
        dos: Int
    ){ //Bloque del codigo constructor
        this.numeroUno = uno
        this.numeroDos = dos
        print("Inicializando")
     }
}

class Suma( //Constructor Primario Suma
    uno:Int, //Parametro
    dos: Int //Parametro
) : NumerosJava(uno, dos){ // Constructor padre
    init { //Bloque constructor primario
        this.numeroUno; numeroUno;
        this.numeroDos; numeroDos;
    }

    constructor( //Segundo constructor
        uno: Int? //Parametros
        dos: Int //Parametros
    ) : this(
        if(uno == null) 0 else uno,
        dos
    ){
        //Bloque de codigo
    }
    constructor( //Tercer constructor
        uno: Int, //Parametros
        dos: Int? //Parametros
    )  : this( //Llamada al constructor primario
        uno,
        if (dos == null) 0 else uno
    ){
        //Bloque de codigo
    }

    constructor( //Cuarto constructor
        uno: Int?, //Parametros
        dos: Int? //Parametros
    )  : this( //Llamada al constructor primario
        if(uno == null) 0 else uno,
        if (dos == null) 0 else uno
    ){
        //Bloque de codigo
    }

    public fun sumar(): Int{
        val total = numeroUno + numeroDos
        agregarHistorial(total)
        return total
    }
    companion object{// Atributos y metodos compartidos
        //entre instancias
        val pi=3.14
        fun elevarAlCuadrado(num:Int):Int{
            return num*num
        }

        val historialSumas = arrayListOf<Int>()
        fun agregarHistorial(valorNuevaSuma: Int){
            historialSumas.add(valorNuevaSuma)
        }
    }

}
