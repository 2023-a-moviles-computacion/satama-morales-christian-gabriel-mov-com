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
import android.widget.TextView

class Dispositivo : AppCompatActivity() {
    val arregloControlador = BBaseDatosMemoria.arregloBControlador
    var arregloDispositivo = BBaseDatosMemoria.arregloBDispositivo
    var idItemSeleccionado = 0
    lateinit var adaptador2 : ArrayAdapter<BDispositivo>
    var idActualC = 0
    var idItem = 0
    lateinit var  list: ListView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dispositivo)

        var bundle = intent.extras
        if (bundle != null){
            idActualC = bundle.getInt("id")
        }
        arregloDispositivo = arregloDispositivo.filter { it.idControlador!!.equals(idActualC) } as ArrayList<BDispositivo>
        val rotulo = findViewById<TextView>(R.id.letrero_ControladorEnDispositivo)
        arregloControlador.forEach {
            if(it.identificador == idActualC){
                rotulo.text = it.nombre
            }else{
                rotulo.text = "Not found"
            }
        }
        val listView = findViewById<ListView>(R.id.ListView_Dispositivo)
        list = listView
        val adaptador = ArrayAdapter(
            this,
            android.R.layout.simple_list_item_1,
            arregloDispositivo
        )
        listView.adapter = adaptador
        adaptador2 = adaptador
        adaptador.notifyDataSetChanged()

        val botonCrearDispositivo = findViewById<Button>(R.id.btn_CrearDispositivo)
        botonCrearDispositivo.setOnClickListener {
            crearDispositivo(Fill_Dispositivo::class.java,true,-1)
        }
        registerForContextMenu(listView)

    }
    fun crearDispositivo(clase: Class<*>, tipo: Boolean, idDispositivo: Int){
        //true -> crearDispositivo, false -> Modificar
        val intent = Intent(this,clase)
        var bundle = Bundle()
        bundle.putBoolean("tipo",tipo)
        bundle.putInt("id",idDispositivo)
        intent.putExtras(bundle)
        startActivity(intent)

    }
    fun borrarDispositivo(daptador: ArrayAdapter<BDispositivo>,idDispositivo: Int){
        arregloDispositivo.forEach {
            if(idDispositivo == it.identificador){
                BBaseDatosMemoria.arregloBDispositivo.removeAt(arregloDispositivo.indexOf(it))
            }
        }
        daptador.notifyDataSetChanged()
    }
    fun editarDispositivo(idDispositivo:Int){
        crearDispositivo(Fill_Dispositivo::class.java, false,idDispositivo)

    }

    override fun onCreateContextMenu(
        menu: ContextMenu?,
        v: View?,
        menuInfo: ContextMenu.ContextMenuInfo?
    ) {
        super.onCreateContextMenu(menu, v, menuInfo)
        var inflater = menuInflater
        inflater.inflate(R.menu.menu_dispositivo, menu)
        var info = menuInfo as AdapterView.AdapterContextMenuInfo
        val id = info.position
        idItemSeleccionado = id
        var dispositivo = list.getItemAtPosition(id) as BDispositivo
        idItem = dispositivo.identificador!!
    }

    override fun onContextItemSelected(item: MenuItem): Boolean {
        val _adaptador = adaptador2
        return  when(item.itemId){
            R.id.op_editarD ->{
                "${idItemSeleccionado}"
                editarDispositivo(idActualC)
                return true
            }
            R.id.op_eliminarD ->{
                "${idItemSeleccionado}"
                borrarDispositivo(_adaptador,idItem)
                return true

            }
            else ->{
                super.onContextItemSelected(item)
            }
        }

    }
}