package com.example.sistemaoperativo.Models

import kotlin.random.Random

data class ControladorModel (
    var identificador: Int = getAutoId(),
    var nombre: String = "",
    var lastFechaInstalacion: String = "",
    var version: Double = 0.0,
    var isActive: Int = 0
){
    companion object {
        fun getAutoId():Int{
            val random = Random
            return random.nextInt(100)
        }
    }
}