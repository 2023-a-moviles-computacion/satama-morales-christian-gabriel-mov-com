package com.example.examen_b1

class BControlador {
    var identificador: Int
    var nombre: String
    var lastFechaInstalacion: String
    var version: Double
    var isActive: Boolean

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

    override fun toString(): String {
        return "$identificador-$nombre"
    }

}