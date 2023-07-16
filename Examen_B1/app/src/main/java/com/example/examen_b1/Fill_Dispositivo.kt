package com.example.examen_b1

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView

class Fill_Dispositivo : AppCompatActivity() {
    val arregloControlador = BBaseDatosMemoria.arregloBControlador
    val arregloDispositivo = BBaseDatosMemoria.arregloBDispositivo
    var idActualC = 0
    var tipo = true ////true -> crearControlador

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_fill_dispositivo)

        var bundle = intent.extras
        if (bundle != null){
            idActualC = bundle!!.getInt("id")
            tipo = bundle!!.getBoolean("tipo")
        }
        val rotulo = findViewById<TextView>(R.id.letrero_fill_Dispositivo)

        val identificador = findViewById<EditText>(R.id.txt_identificador_FD)
        val nombre = findViewById<EditText>(R.id.txt_nombre_FD)
        val puertoUsado = findViewById<EditText>(R.id.txt_puertoUsed_FD)
        val estado = findViewById<EditText>(R.id.txt_estado_FD)
        val tiempoActivo = findViewById<EditText>(R.id.txt_tiempoActivo_FD)
        var idControlador = 0
        var arregloPuertos = intArrayOf(0,0,0,0,0,0,0,0,0,0)

        val botonCrearDispositivo = findViewById<Button>(R.id.btn_fill_Dispositivo)

        if (tipo){
            rotulo.text = "Registre un nuevo Dispositivo"

            botonCrearDispositivo.setOnClickListener {
                val i = puertoUsado.text.toString().toInt()
                arregloPuertos.set(i,1)
                BBaseDatosMemoria.arregloBDispositivo.forEach {
                    if (it.identificador == idActualC){
                        idControlador = it.idControlador!!
                    }
                }
                val dispositivo = BDispositivo(
                    identificador.text.toString().toInt(),
                    nombre.text.toString(),
                    arregloPuertos,
                    estado.text.toString().toBooleanStrict(),
                    tiempoActivo.text.toString().toDouble(),
                    idControlador
                )
                BBaseDatosMemoria.arregloBDispositivo.add(dispositivo)
                val intent = Intent(this,Dispositivo::class.java)
                startActivity(intent)
            }

        }

        else{
            var index = 0
            arregloDispositivo.forEach{
                if(it.identificador == idActualC){
                    index = arregloDispositivo.indexOf(it)
                }
            }
            var dispositivo = arregloDispositivo.get(index)
            rotulo.text = "Modifica el dispositivo " + dispositivo.nombre
            identificador.setText(dispositivo.identificador.toString())
            nombre.setText(dispositivo.nombre)
            puertoUsado.setText(dispositivo.puertoUsed!!.indexOf(1).toString())
            estado.setText(dispositivo.estado.toString())
            tiempoActivo.setText(dispositivo.upTime.toString())

            botonCrearDispositivo.setOnClickListener{
                arregloPuertos.set(puertoUsado.text.toString().toInt(),1)
                val Dispositivo = BDispositivo(
                    identificador.text.toString().toInt(),
                    nombre.text.toString(),
                    arregloPuertos,
                    estado.text.toString().toBooleanStrict(),
                    tiempoActivo.text.toString().toDouble(),
                    dispositivo.idControlador!!
                )
                BBaseDatosMemoria.arregloBDispositivo.forEach {
                    if(it.identificador == dispositivo.identificador){
                        index = BBaseDatosMemoria.arregloBDispositivo.indexOf(it)
                        BBaseDatosMemoria.arregloBDispositivo.set(index,dispositivo)
                    }
                }
                val intent = Intent(this,Dispositivo::class.java)
                startActivity(intent)
            }
        }

    }
}