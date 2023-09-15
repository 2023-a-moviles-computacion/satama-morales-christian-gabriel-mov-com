package com.example.sistemaoperativo

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.sistemaoperativo.Models.ControladorModel
import com.example.sistemaoperativo.Models.DispositivoModel
import com.example.sistemaoperativo.db.DbHelper

class fill_dispositivo : AppCompatActivity() {
    private lateinit var nombre: EditText
    private lateinit var puertoUsed: EditText
    private lateinit var estado: EditText
    private lateinit var upTime:EditText
    private var id= 0
    private var idControlador= 0
    private var tipo = true
    private lateinit var sqliteHelper: DbHelper
    private lateinit var btnOk: Button
    private lateinit var dispositivo: DispositivoModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_fill_dispositivo)

        initView()
        var bundle = intent.extras
        if (bundle != null){
            id = bundle!!.getInt("id")
            tipo = bundle!!.getBoolean("tipo")
        }
        sqliteHelper = DbHelper(this)
        var letrero = findViewById<TextView>(R.id.letrero_fill_dispositivo)

        if (tipo){
            //Crear Dispositivo
            idControlador = id
            letrero.text = "Cree un nuevo dispositivo"
            btnOk.setOnClickListener { addDispositivo() }
        }else{
            dispositivo = sqliteHelper.getDispositivo(id)!!
            letrero.text = "Configure ${dispositivo.nombre}"
            nombre.setText(dispositivo.nombre)
            puertoUsed.setText(dispositivo.puertoUsed)
            estado.setText(dispositivo.estado)
            upTime.setText(dispositivo.upTime.toString())
            btnOk.setOnClickListener{
                dispositivo.nombre = nombre.text.toString()
                dispositivo.puertoUsed = puertoUsed.text.toString()
                dispositivo.estado = estado.text.toString().toInt()
                dispositivo.upTime = upTime.text.toString().toDouble()

                var status = sqliteHelper.updateDispositivo(dispositivo)
                if(status > -1){
                    Toast.makeText(this,"Registro Exitoso",Toast.LENGTH_SHORT).show()
                    changeIntent()
                }else{
                    Toast.makeText(this,"ERROR",Toast.LENGTH_SHORT).show()
                }
            }
        }
    }


    private fun addDispositivo(){
        val nombre = nombre.text.toString()
        val puertoUsed = puertoUsed.text.toString()
        val estado = estado.text.toString()
        val upTime = upTime.text.toString()

        if( nombre.isEmpty() || puertoUsed.isEmpty() || estado.isEmpty() || upTime.isEmpty()){
            Toast.makeText(this, "No puede dejar campos vacios",Toast.LENGTH_SHORT)
        }else{
            val dispositivo = DispositivoModel(nombre = nombre, puertoUsed = puertoUsed, estado = estado.toInt(), upTime = upTime.toDouble(), idControladorFk = idControlador)
            val status = sqliteHelper.insertarDispositivo(dispositivo)
            clearEditText()
            if(status > -1){
                Toast.makeText(this, "Se agregó correctamente", Toast.LENGTH_SHORT).show()
                startActivity(Intent(this,Dispositivo::class.java))
            }else{
                Toast.makeText(this, "ERROR", Toast.LENGTH_SHORT).show()
            }
        }
    }
    private fun initView() {
        nombre = findViewById(R.id.txt_fill_dispositivo_nombre)
        puertoUsed = findViewById(R.id.txt_fill_dispositivo_PuertoUsed)
        estado = findViewById(R.id.txt_fill_dispositivo_Estado)
        upTime = findViewById(R.id.txt_fill_dispositivo_Uptime)
        btnOk = findViewById(R.id.btn_fill_dispositivo_ok)
    }
    private fun clearEditText(){
        nombre.setText("")
        puertoUsed.setText("")
        estado.setText("")
        upTime.setText("")
    }
    fun changeIntent(){
        val intent = Intent(this,Dispositivo::class.java)
        startActivity(intent)
    }


}