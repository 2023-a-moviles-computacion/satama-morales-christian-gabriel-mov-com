package com.example.sistemaoperativo

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.example.sistemaoperativo.Models.ControladorModel
import com.example.sistemaoperativo.db.DbHelper

class fill_controlador : AppCompatActivity() {
    private lateinit var nombre: EditText
    private lateinit var fecha: EditText
    private lateinit var version: EditText
    private lateinit var isActive: EditText
    private lateinit var btnOk: Button

    private lateinit var sqliteHelper: DbHelper
    private var idActualC= 0
    private var tipo = true
    private lateinit var controlador: ControladorModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_fill_controlador)

        initView()
        var bundle = intent.extras
        if (bundle != null){
            idActualC = bundle!!.getInt("id")
            tipo = bundle!!.getBoolean("tipo")
        }
        sqliteHelper = DbHelper(this)
        var letrero = findViewById<TextView>(R.id.letrero_fill_Controlador)
        if(tipo){
            letrero.text = "Cree un nuevo controlador"
            btnOk.setOnClickListener { addControlador() }
        }
        else{
            controlador = sqliteHelper.getControlador(idActualC)!!
            letrero.text = "Configure ${controlador.nombre}"
            nombre.setText(controlador.nombre)
            fecha.setText(controlador.lastFechaInstalacion)
            version.setText(controlador.version.toString())
            isActive.setText(controlador.isActive.toString())
            btnOk.setOnClickListener{
                controlador.nombre = nombre.text.toString()
                controlador.lastFechaInstalacion = fecha.text.toString()
                controlador.version = version.text.toString().toDouble()
                controlador.isActive = isActive.text.toString().toInt()

                var status = sqliteHelper.updateControlador(controlador)
                if (status > -1){
                    Toast.makeText(this,"Registro Exitoso",Toast.LENGTH_SHORT).show()
                    changeIntent()

                }else{
                    Toast.makeText(this,"ERROR",Toast.LENGTH_SHORT).show()

                }

            }

        }

    }



    private fun addControlador() {
        val nombre = nombre.text.toString()
        val fecha = fecha.text.toString()
        val version = version.text.toString()
        val isActive = isActive.text.toString()


        if (nombre.isEmpty() || fecha.isEmpty() || version.isEmpty() || isActive.isEmpty()){
            Toast.makeText(this, "No puede dejar campos vacios", Toast.LENGTH_SHORT).show()
        }else{
            val controlador = ControladorModel(nombre =  nombre, lastFechaInstalacion = fecha, version = version.toDouble(), isActive =  isActive.toInt())
            val status = sqliteHelper.insertarControlador(controlador)
            clearEditText()
            if (status > -1){
                Toast.makeText(this, "Se agregó correctamente", Toast.LENGTH_SHORT).show()
            }else{
                Toast.makeText(this, "ERROR", Toast.LENGTH_SHORT).show()
            }
        }
        changeIntent()

    }
    fun changeIntent(){
        val intent = Intent(this,MainActivity::class.java)
        startActivity(intent)
    }

    private fun clearEditText(){
        nombre.setText("")
        fecha.setText("")
        version.setText("")
        isActive.setText("")
    }
    private fun initView(){
        nombre = findViewById(R.id.txt_fill_Controlador_nombre)
        fecha = findViewById(R.id.txt_fill_Controlador_fecha)
        version = findViewById(R.id.txt_fill_Controlador_version)
        isActive = findViewById(R.id.txt_fill_Controlador_active)
        btnOk = findViewById(R.id.btn_fill_Controlador_OK)
    }
}