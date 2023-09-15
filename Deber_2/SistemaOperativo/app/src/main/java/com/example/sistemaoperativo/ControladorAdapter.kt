package com.example.sistemaoperativo

import android.view.LayoutInflater
import android.view.ScrollCaptureCallback
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.sistemaoperativo.Models.ControladorModel

class ControladorAdapter: RecyclerView.Adapter<ControladorAdapter.controladorViewHolder>() {
    private var controladorList: ArrayList<ControladorModel> = ArrayList()
    private var onClickItem:((ControladorModel) -> Unit)? = null
    private var onClickDeleteItem:((ControladorModel) -> Unit)? = null
    private var onClickVerItem:((ControladorModel) -> Unit)? = null

    fun addItems(items: ArrayList<ControladorModel>){
        this.controladorList = items
        notifyDataSetChanged()

    }
    fun setOnClickItem(callback: (ControladorModel)-> Unit){
        this.onClickItem = callback
    }
    fun setOnClickDeleteItem(callback: (ControladorModel)-> Unit){
        this.onClickDeleteItem = callback
    }
    fun setOnClickVerItem(callback: (ControladorModel)-> Unit){
        this.onClickVerItem = callback
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = controladorViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.card_items_controlador,parent,false)

    )

    override fun getItemCount(): Int {
        return controladorList.size
    }

    override fun onBindViewHolder(holder: controladorViewHolder, position: Int) {
        val controlador = controladorList[position]
        holder.bindView(controlador)
        holder.itemView.setOnClickListener{ onClickItem?.invoke(controlador)}
        holder.btnDelete.setOnClickListener { onClickDeleteItem?.invoke(controlador) }
        holder.btnVerDispositivo.setOnClickListener { onClickVerItem?.invoke(controlador) }
    }

    class controladorViewHolder(var view: View): RecyclerView.ViewHolder(view){
        private var id = view.findViewById<TextView>(R.id.txt_card_controlador_id)
        private var nombre = view.findViewById<TextView>(R.id.txt_card_controlador_nombre)
        private var isActive = view.findViewById<TextView>(R.id.txt_card_controlador_isActive)
        var btnDelete = view.findViewById<Button>(R.id.btn_Borrar_Controlador)
        var btnVerDispositivo = view.findViewById<Button>(R.id.btn_ver_Dispositivos_Controlador)

        fun bindView(controlador: ControladorModel){
            id.text = controlador.identificador.toString()
            nombre.text = controlador.nombre
            isActive.text = controlador.isActive.toString()
        }
    }
}