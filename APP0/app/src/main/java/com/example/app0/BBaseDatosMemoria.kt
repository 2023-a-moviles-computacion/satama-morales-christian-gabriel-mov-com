package com.example.app0

class BBaseDatosMemoria {
    companion object {
        val arregloBEntrenador = arrayListOf<BEntrenador>()
        init {
            arregloBEntrenador
                .add(
                    BEntrenador(1,"Christian", "a@a.com")
                )
            arregloBEntrenador
                .add(
                    BEntrenador(2,"Satama","b@b.com")
                )
            arregloBEntrenador
                .add(
                    BEntrenador(3,"Rodolfo","c@c.com")
                )
        }
    }
}