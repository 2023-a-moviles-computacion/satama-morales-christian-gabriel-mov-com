package com.example.sistemaoperativo

import android.app.AlertDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.sistemaoperativo.Models.ControladorModel
import com.example.sistemaoperativo.Models.DispositivoModel
import com.example.sistemaoperativo.db.DbHelper

class Dispositivo : AppCompatActivity() {
    private lateinit var recyclerView: RecyclerView
    private var adapter: DispositivoAdapter? = null

    private var idActualC= 0
    private var tipo = true
    private lateinit var sqliteHelper: DbHelper
    private lateinit var dispositivos: ArrayList<DispositivoModel>
    private lateinit var controlador: ControladorModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dispositivo)

        var bundle = intent.extras
        if (bundle != null){
            idActualC = bundle!!.getInt("id")
        }

        sqliteHelper = DbHelper(this)

        var letrero = findViewById<TextView>(R.id.letrero_Dispositivo)
        //letrero.text = sqliteHelper.getDispositivoByControladorId(idActualC)


        controlador = sqliteHelper.getControlador(idActualC)!!
        letrero.text = "controlador: ${controlador.nombre}"

        initView()
        initRecyclerView()

        adapter?.setOnClickItem {
            crearDispositivo(fill_dispositivo::class.java,false,it.identificador)
            startActivity(intent)
            val dispositivosList = sqliteHelper.getAllDispositivos()
            adapter?.addItems(dispositivosList)
            adapter?.notifyDataSetChanged()

        }
        adapter?.setOnClickItem {
            deleteDispositivo(it.identificador)
            adapter?.notifyDataSetChanged()

        }

        val btnCrearDispositivo = findViewById<Button>(R.id.btn_Dispositivo_OK)
        btnCrearDispositivo.setOnClickListener {
            crearDispositivo(fill_dispositivo::class.java,true, controlador.identificador)
        }

    }

    private fun deleteDispositivo(id: Int) {
        if (id == null) return

        val builder = AlertDialog.Builder(this)
        builder.setMessage("Estas seguro de borrar este dispositivo?")
        builder.setCancelable(true)
        builder.setPositiveButton("Yes"){dialog,_->
            val status = sqliteHelper.deleteDispositivo(id)
            if(status > -1){
                Toast.makeText(this, "Eliminación Completa", Toast.LENGTH_SHORT).show()
                dialog.dismiss()
                val dispositivosList = sqliteHelper.getAllDispositivos()
                adapter?.addItems(dispositivosList)
                adapter?.notifyDataSetChanged()
            }else{
                Toast.makeText(this,"Error",Toast.LENGTH_SHORT).show()
            }
        }
        builder.setNegativeButton("No") { dialog, _ ->
            dialog.dismiss()
        }
        val alert = builder.create()
        alert.show()
    }

    private fun crearDispositivo(clase: Class<*>, tipo: Boolean,idDispositivo: Int) {
        //true -> crearDispositivo, false -> Modificar
        val intent = Intent(this, clase)
        var bundle =Bundle()
        bundle.putBoolean("tipo",tipo)
        bundle.putInt("id",idDispositivo)
        intent.putExtras(bundle)
        startActivity(intent)
        adapter?.notifyDataSetChanged()
    }

    private fun initRecyclerView() {
        val dispositivosList = sqliteHelper.getDispositivoByControladorId(1)
        recyclerView.layoutManager = LinearLayoutManager(this)
        adapter = DispositivoAdapter()
        recyclerView.adapter = adapter
        adapter?.addItems(dispositivosList)
        adapter?.notifyDataSetChanged()

    }

    private fun initView() {
        recyclerView = findViewById(R.id.rcv_ListaDispositivos)
    }
}