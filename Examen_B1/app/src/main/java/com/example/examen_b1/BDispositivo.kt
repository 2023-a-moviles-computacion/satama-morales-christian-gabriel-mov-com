package com.example.examen_b1

class BDispositivo {
    var identificador: Int?=null
    var nombre: String?=null
    var puertoUsed: IntArray?=null
    var estado: Boolean?=null
    var upTime: Double= 0.0
    var idControlador: Int?=null

    constructor(
        _identificador: Int,
        _nombre: String,
        _puertoUsed: IntArray,
        _estado: Boolean,
        _Uptime: Double,
        _idControlador: Int
    ) {
        this.identificador = _identificador
        this.nombre = _nombre
        this.puertoUsed = _puertoUsed
        this.estado = _estado
        this.upTime = _Uptime
        this.idControlador = _idControlador
    }

    override fun toString(): String {
        return "$identificador - $nombre"
    }

}