package com.example.examenmoviles

import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.service.autofill.OnClickAction
import android.view.ContextMenu
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.ListView
import android.widget.TextView
import androidx.core.view.get
import androidx.core.view.size

class MainActivity : AppCompatActivity() {
    val arreglo = BBaseDatosMemoria.arregloBEntrenador
    var idItemSeleccionado = 0
    var idItem = 0
    lateinit var adaptador2 : ArrayAdapter<BEntrenador>
    lateinit var list : ListView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val listView = findViewById<ListView>(R.id.listViewEntrenadores)
        list = listView
        val adaptador = ArrayAdapter(
            this,
            android.R.layout.simple_list_item_1,
            arreglo
        )
        listView.adapter = adaptador
        adaptador2 = adaptador
        adaptador.notifyDataSetChanged()
        val botonCreaEntrenador = findViewById<Button>(
            R.id.btnCrear
        )
        botonCreaEntrenador.setOnClickListener {
            val nombreEntrenador = findViewById<EditText>(R.id.txtNombreEntrenador)
            val lastEntrenador = listView.get(listView.size-1)
            val newEntrenador = BEntrenador(lastEntrenador.id+1, nombreEntrenador.text.toString() ,"TEST")
            addEntrenador(adaptador,newEntrenador)
        }
        registerForContextMenu(listView)
    }
    fun addEntrenador(adaptador: ArrayAdapter<BEntrenador>, entrenador: BEntrenador){
        arreglo.add(
            BEntrenador(
                entrenador.id,
                entrenador.nombre,
                entrenador.descripcion
            )
        )
        adaptador.notifyDataSetChanged()
    }

    fun borrarEntrenador(adaptador: ArrayAdapter<BEntrenador>, index: Int){
        arreglo.removeAt(index)
        adaptador.notifyDataSetChanged()
    }
    override fun onCreateContextMenu(
        menu: ContextMenu?,
        v: View?,
        menuInfo: ContextMenu.ContextMenuInfo?
    ) {
        super.onCreateContextMenu(menu, v, menuInfo)

        var inflater = menuInflater
        inflater.inflate(R.menu.menu_entrenador,menu)
        var info = menuInfo as AdapterView.AdapterContextMenuInfo
        val id = info.position
        idItemSeleccionado = id
        var entrenador = list.getItemAtPosition(id) as BEntrenador
        idItem = entrenador.id
    }

    override fun onContextItemSelected(item: MenuItem): Boolean {
        val _adaptador = adaptador2
        return when(item.itemId){
            R.id.opEtidarE -> {
                "${idItemSeleccionado}"
                datosForEntrenador(_adaptador,idItem)
                return true
            }
            R.id.opEliminarE -> {
                "${idItemSeleccionado}"
                areYouSure(_adaptador,idItem)
                return true
            }
            R.id.opVerPokemonsE -> {
                "${idItemSeleccionado}"
                //actions
                openViewP(idItem,pokemon::class.java)
                return true
            }
            else -> {
                super.onContextItemSelected(item)
            }
        }
    }
    fun areYouSure(adaptador: ArrayAdapter<BEntrenador>,index:Int){
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Esta seguro que desea eliminar?")
        builder.setPositiveButton(
            "Aceptar",
            DialogInterface.OnClickListener { dialog,
                                              which -> borrarEntrenador(adaptador,index)
            }
        )
        builder.setNegativeButton(
            "Cancelar",
            null
        )
        val dialogo = builder.create()
        dialogo.show()
    }
    fun datosForEntrenador(adaptador: ArrayAdapter<BEntrenador>,idEntrenador: Int):BEntrenador{
        val builder = AlertDialog.Builder(this)
        val inflater = layoutInflater
        val dialogLayout = inflater.inflate(R.layout.edit_nombre_entrenador,null)
        val dialogLayout2 = inflater.inflate(R.layout.edit_descripcion_entrenador,null)
        var nombreE = dialogLayout.findViewById<EditText>(R.id.et_nombre_entrenador)
        var descE = dialogLayout2.findViewById<EditText>(R.id.et_desc_entrenador)
        var entrenador = BEntrenador()
        with(builder){
            setTitle("Ingresa los nuevos datos del entrenador")
            setPositiveButton("ok"){dialog,which->
                entrenador = BEntrenador(idEntrenador,nombreE.text.toString(),descE.text.toString())
               val arrfinal =  arreglo.map {
                    if (it.id == entrenador.id) entrenador else it
                }
                arreglo.clear()
                arrfinal.forEach{
                    arreglo.add(it)
                }
                adaptador.notifyDataSetChanged()
            }
            setNegativeButton(
                "Cancelar"){dialog,which->
                null
            }
            setView(dialogLayout)
            show()
        }
    return entrenador
    }
    fun openViewP(index:Int, clase:Class<*>){
        val intent = Intent(this,clase)
        var bundle = Bundle()
        bundle.putInt("index",index)
        intent.putExtras(bundle)
        startActivity(intent)
    }
}