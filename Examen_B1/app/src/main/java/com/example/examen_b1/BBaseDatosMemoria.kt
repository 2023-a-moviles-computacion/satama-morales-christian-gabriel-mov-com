package com.example.examen_b1

class BBaseDatosMemoria {
    companion object{
        var arregloBControlador = arrayListOf<BControlador>()
        var arregloBDispositivo = arrayListOf<BDispositivo>()
        init {
            //Controladore
            arregloBControlador.add(
                BControlador(1,"Realtek","13/02/2023",1.2,true)
            )
            arregloBControlador.add(
                BControlador(2,"nahimic","15/07/2022",2.3,false)
            )
            //Dispositivos
            arregloBDispositivo.add(
                BDispositivo(1,"Parlantes", intArrayOf(0,0,1,0,0,0,0,0,0,0),true,4.3,1)
            )
            arregloBDispositivo.add(
                BDispositivo(2,"Audifonos", intArrayOf(1,0,0,0,0,0,0,0,0,0),true,4.2,2)
            )
            arregloBDispositivo.add(
                BDispositivo(3,"Estereo", intArrayOf(0,0,0,0,1,0,0,0,0,0),true,4.0,2)
            )
        }
    }
}