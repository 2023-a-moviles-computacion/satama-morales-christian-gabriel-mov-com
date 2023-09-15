package com.example.sistemaoperativo

import android.app.AlertDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Parcelable
import android.util.Log
import android.view.ContextMenu
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.Button
import android.widget.LinearLayout
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.sistemaoperativo.Models.ControladorModel
import com.example.sistemaoperativo.db.DbHelper


class MainActivity : AppCompatActivity() {
    private lateinit var recyclerView:RecyclerView
    private lateinit var btn_editar_Controlador: Button
    private lateinit var btn_eliminar_Controlador: Button
    private lateinit var btn_ver_Controlador: Button
    private var adapter:ControladorAdapter? = null
    private lateinit var sqliteHelper: DbHelper


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        sqliteHelper = DbHelper(this)

        initView()
        initRecyclerView()
        adapter?.setOnClickItem {
            //Toast.makeText(this,it.nombre,Toast.LENGTH_SHORT).show()
            crearControlador(fill_controlador::class.java,false,it.identificador)

        }
        adapter?.setOnClickDeleteItem {
            deleteControlador(it.identificador)
        }
        adapter?.setOnClickVerItem {
            verDispositivo(it.identificador,Dispositivo::class.java)

        }
        val btnCrearControlador = findViewById<Button>(R.id.btn_Crear_Controlador)
        btnCrearControlador.setOnClickListener {
            crearControlador(fill_controlador::class.java,true,-1)
        }

    }


    private fun crearControlador(clase: Class<*>, tipo: Boolean,idControlador: Int) {
        //true -> crearControlador, false -> Modificar
        val intent = Intent(this,clase)
        var bundle = Bundle()
        bundle.putBoolean("tipo",tipo)
        bundle.putInt("id",idControlador)
        intent.putExtras(bundle)
        startActivity(intent)
        adapter?.notifyDataSetChanged()
    }

    private fun verDispositivo(idControlador: Int, clase: Class<*>){
        val intent = Intent(this,clase)
        var bundle = Bundle()
        bundle.putInt("id",idControlador)
        intent.putExtras(bundle)
        startActivity(intent)
        adapter?.notifyDataSetChanged()
    }
    private fun initRecyclerView(){
        val controladoresList = sqliteHelper.getAllControladores()
        recyclerView.layoutManager = LinearLayoutManager(this)
        adapter = ControladorAdapter()
        recyclerView.adapter = adapter
        adapter?.addItems(controladoresList)
        adapter?.notifyDataSetChanged()
    }

    private fun initView(){
        recyclerView = findViewById(R.id.rcv_ListaControladores)
    }

    private fun deleteControlador(id:Int){
        if(id==null) return

        val builder = AlertDialog.Builder(this)
        builder.setMessage("Estas seguro que desea eleminarlo permanentemente?")
        builder.setCancelable(true)
        builder.setPositiveButton("Yes"){dialog,_->
            val status = sqliteHelper.deleteControlador(id)
            if (status > -1){
                Toast.makeText(this,"Eliminación completa",Toast.LENGTH_SHORT).show()
                dialog.dismiss()
                val controladoresList = sqliteHelper.getAllControladores()
                adapter?.addItems(controladoresList)
                adapter?.notifyDataSetChanged()
            }else{
                Toast.makeText(this,"Error",Toast.LENGTH_SHORT).show()

            }
        }
        builder.setNegativeButton("No"){dialog, _ ->
            dialog.dismiss()
        }
        val alert = builder.create()
        alert.show()
    }

}