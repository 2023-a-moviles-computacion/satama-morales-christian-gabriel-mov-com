package com.example.sistemaoperativo.Models

import kotlin.random.Random

data class DispositivoModel(
    var identificador: Int = getAutoId(),
    var nombre: String = "",
    var puertoUsed: String = "",
    var estado: Int = 0,
    var upTime: Double = 0.0,
    var idControladorFk: Int = 0
)
{
    companion object {
        fun getAutoId():Int{
            val random = Random
            return random.nextInt(100)
        }
    }
}