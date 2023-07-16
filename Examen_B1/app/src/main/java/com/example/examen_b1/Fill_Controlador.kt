package com.example.examen_b1

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.ListView
import android.widget.TextView

class Fill_Controlador : AppCompatActivity() {
    val arregloControlador = BBaseDatosMemoria.arregloBControlador
    var idActualC = 0
    var tipo = true ////true -> crearControlador

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_fill_controlador)
        //letrero_fill_Controlador
        var bundle = intent.extras
        if (bundle != null){
            idActualC = bundle!!.getInt("id")
            tipo = bundle!!.getBoolean("tipo")
        }
        val rotulo = findViewById<TextView>(R.id.letrero_fill_Controlador)
        val identificador = findViewById<EditText>(R.id.txt_identificador_FC)
        val nombre = findViewById<EditText>(R.id.txt_nombre_FC)
        val fecha = findViewById<EditText>(R.id.txt_FechaInstalacion_FC)
        val version = findViewById<EditText>(R.id.txt_version_FC)
        val isActivo = findViewById<EditText>(R.id.txt_isActive_FC)
        val botonCrearControlador = findViewById<Button>(R.id.btn_fill_Controlador)

        if (tipo){
            rotulo.text = "Registre un nuevo Controlador"


            botonCrearControlador.setOnClickListener {
                val controlador = BControlador(
                    identificador.text.toString().toInt(),
                    nombre.text.toString(),
                    fecha.text.toString(),
                    version.text.toString().toDouble(),
                    isActivo.text.toString().toBooleanStrict(),
                )
                BBaseDatosMemoria.arregloBControlador.add(controlador)
                val intent =Intent(this,MainActivity::class.java)
                startActivity(intent)
            }
        }
        else{
            var index = 0
            arregloControlador.forEach {
                if (it.identificador == idActualC){
                    index = arregloControlador.indexOf(it)
                }
            }
            var controlador = arregloControlador.get(index)
            rotulo.text = "Modifica el controlador "+ controlador.nombre
            identificador.setText(controlador.identificador.toString())
            nombre.setText(controlador.nombre)
            fecha.setText(controlador.lastFechaInstalacion)
            version.setText(controlador.version.toString())
            isActivo.setText(controlador.isActive.toString())

            botonCrearControlador.setOnClickListener {
                val controlador = BControlador(
                    identificador.text.toString().toInt(),
                    nombre.text.toString(),
                    fecha.text.toString(),
                    version.text.toString().toDouble(),
                    isActivo.text.toString().toBooleanStrict(),
                )
                BBaseDatosMemoria.arregloBControlador.forEach {
                 if(it.identificador == controlador.identificador){
                     index = BBaseDatosMemoria.arregloBControlador.indexOf(it)
                     BBaseDatosMemoria.arregloBControlador.set(index,controlador)
                 }
                }
                val intent =Intent(this,MainActivity::class.java)
                startActivity(intent)
            }
        }

    }
}