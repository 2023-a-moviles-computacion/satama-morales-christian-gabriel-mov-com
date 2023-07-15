package com.example.examenmoviles

import android.app.AlertDialog
import android.content.DialogInterface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
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
import kotlin.contracts.Returns

class pokemon : AppCompatActivity() {
    val arregloEntrenador = BBaseDatosMemoria.arregloBEntrenador
    var arregloPokemon = BBaseDatosMemoria.arregloBPokemon
    var idItemSeleccionado = 0
    lateinit var adaptador2 : ArrayAdapter<BPokemon>
    var idActualE = 0
    var idItem = 0
    lateinit var list: ListView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pokemon)
        var bundle = intent.extras
        if (bundle != null) {
            idActualE = bundle.getInt("index")
        }
        arregloPokemon = arregloPokemon.filter { it.id.equals(idActualE) } as ArrayList<BPokemon>
        val rotulo = findViewById<TextView>(R.id.txt_nombreEntrenador)
        arregloEntrenador.forEach {
            if(it.id == idActualE){
                rotulo.text = it.nombre
            }
        }
        val listView = findViewById<ListView>(R.id.listViewPokemon)
        list = listView
        val adaptador = ArrayAdapter(
            this,
            android.R.layout.simple_list_item_1,
            arregloPokemon
        )
        listView.adapter = adaptador
        adaptador2 = adaptador

        val botonCrearPokemon = findViewById<Button>(R.id.btnCrearPokemon)
        botonCrearPokemon.setOnClickListener {
            val nombrePokemon = findViewById<EditText>(R.id.et_nombre_pokemon)
            val lastPokemon = listView.get(listView.size-1)
            val newPokemon = BPokemon(lastPokemon.id+1,nombrePokemon.text.toString(),"TEST",idItemSeleccionado)
            addPokemon(adaptador,newPokemon)
        }
        registerForContextMenu(listView)
    }
    fun addPokemon(adaptador: ArrayAdapter<BPokemon>, pokemon:BPokemon){
        arregloPokemon.add(
            BPokemon(
                pokemon.id,
                pokemon.nombre,
                pokemon.descripcion,
                idActualE
            )
        )
        adaptador.notifyDataSetChanged()
    }
    fun borrarPokemon(adaptador: ArrayAdapter<BPokemon>, index: Int){
        arregloPokemon.removeAt(index)
        adaptador.notifyDataSetChanged()
    }

    override fun onCreateContextMenu(
        menu: ContextMenu?,
        v: View?,
        menuInfo: ContextMenu.ContextMenuInfo?
    ) {
        super.onCreateContextMenu(menu, v, menuInfo)
        var inflater = menuInflater
        inflater.inflate(R.menu.menu_pokemon, menu)
        var info = menuInfo as AdapterView.AdapterContextMenuInfo
        val id = info.position
        idItemSeleccionado = id
        var pokemon = list.getItemAtPosition(id) as BPokemon
        idItem = pokemon.id
    }

    override fun onContextItemSelected(item: MenuItem): Boolean {
        val _adaptador = adaptador2
        return  when(item.itemId){
            R.id.opEditarP -> {
                "${idItemSeleccionado}"
                datosForPokemon(_adaptador,idItem)
                return true
            }
            R.id.opEliminarP -> {
                "${idItemSeleccionado}"
                areYouSure(_adaptador,idItem)
                return true
            }
            else -> {
                super.onContextItemSelected(item)
            }
        }
    }
    fun areYouSure(adaptador: ArrayAdapter<BPokemon>, index: Int){
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Estas seguro de eliminar este elemento?")
        builder.setPositiveButton(
            "Aceptar",
            DialogInterface.OnClickListener{dialog,
                                            which -> borrarPokemon(adaptador,index)  }
        )
        builder.setNegativeButton(
            "Cancelar",
            null
        )
        val dialogo = builder.create()
        dialogo.show()
    }
    fun datosForPokemon(adaptador: ArrayAdapter<BPokemon>, idPokemon:Int):BPokemon{
        val builder = AlertDialog.Builder(this)
        val inflater = layoutInflater
        val dialogLayout = inflater.inflate(R.layout.edit_nombre_pokemon, null)
        val dialogLayout2 = inflater.inflate(R.layout.edit_descripcion_pokemon, null)
        var nombreP = dialogLayout.findViewById<EditText>(R.id.et_nombre_pokemon)
        var descP = dialogLayout2.findViewById<EditText>(R.id.et_desc_pokemon)
        var pokemon = BPokemon()
        with(builder){
            setTitle("Ingrese los nuevos datos del Pokemon")
            setPositiveButton("OK"){dialog, which ->
                pokemon = BPokemon(idPokemon, nombreP.text.toString(), descP.text.toString(),idActualE)
            }

            val arrfinal =  arregloPokemon.map {
                if (it.id == pokemon.id) pokemon else it
            }
            arregloPokemon.clear()
            arrfinal.forEach{
                arregloPokemon.add(it)
            }
            adaptador.notifyDataSetChanged()
            setNegativeButton(
                "Cancelar"){dialog,which->
                null
            }
            setView(dialogLayout)
            show()
            setView(dialogLayout2)
        }

        return pokemon
    }
}