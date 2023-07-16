package com.example.examen_b1

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.ContextMenu
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ListView

class MainActivity : AppCompatActivity() {
    val arreglo = BBaseDatosMemoria.arregloBControlador
    var idItemSeleccionado = 0
    var idItem = 0
    lateinit var adaptador2 : ArrayAdapter<BControlador>
    lateinit var list: ListView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val listView = findViewById<ListView>(R.id.listView_Controladores)
        list = listView
        val adaptador = ArrayAdapter(
            this,
            android.R.layout.simple_list_item_1,
            arreglo
        )
        listView.adapter = adaptador
        adaptador2 = adaptador
        adaptador.notifyDataSetChanged()

        val botonCrearControlador = findViewById<Button>(R.id.btn_crearControlador)
        botonCrearControlador.setOnClickListener {
            crearControlador(Fill_Controlador::class.java, true, -1)
            adaptador.notifyDataSetChanged()
        }
        registerForContextMenu(listView)
    }

    override fun onCreateContextMenu(
        menu: ContextMenu?,
        v: View?,
        menuInfo: ContextMenu.ContextMenuInfo?
    ) {
        super.onCreateContextMenu(menu, v, menuInfo)

        var inflater = menuInflater
        inflater.inflate(R.menu.menu_controlador,menu)
        var info = menuInfo as AdapterView.AdapterContextMenuInfo
        val id = info.position
        idItemSeleccionado = id
        var controlador = list.getItemAtPosition(idItemSeleccionado) as BControlador
        idItem = controlador.identificador
    }

    override fun onContextItemSelected(item: MenuItem): Boolean {
        val _adaptador = adaptador2
        return when(item.itemId){
            R.id.op_editarC ->{
                "${idItemSeleccionado}"
                editarControlador(idItem)
                _adaptador.notifyDataSetChanged()
                return true
            }
            R.id.op_borrarC ->{
                "${idItemSeleccionado}"
                borrarControlador(_adaptador,idItem)
                return true
            }
            R.id.op_verDispositivosC ->{
                "${idItemSeleccionado}"
                verDispositivosC(idItem,Dispositivo::class.java)
                _adaptador.notifyDataSetChanged()
                return true
            }
            else ->{
                super.onContextItemSelected(item)
            }
        }
    }
    fun crearControlador(clase: Class<*>, tipo: Boolean, idControlador: Int){
        //true -> crearControlador, false -> Modificar
        val intent = Intent(this,clase)
        var bundle = Bundle()
        bundle.putBoolean("tipo",tipo)
        bundle.putInt("id",idControlador)
        intent.putExtras(bundle)
        startActivity(intent)

    }
    fun editarControlador(idControlador:Int){
        crearControlador(Fill_Controlador::class.java, false, idControlador)
    }
    fun borrarControlador(adaptador: ArrayAdapter<BControlador>,idControlador: Int){
        arreglo.forEach {
            if(idControlador == it.identificador){
                BBaseDatosMemoria.arregloBControlador.removeAt(arreglo.indexOf(it))
            }
        }
        adaptador.notifyDataSetChanged()
    }
    fun verDispositivosC(idControlador: Int, clase:Class<*>){
        val intent = Intent(this,clase)
        var bundle = Bundle()
        bundle.putInt("id",idControlador)
        intent.putExtras(bundle)
        startActivity(intent)
    }

}