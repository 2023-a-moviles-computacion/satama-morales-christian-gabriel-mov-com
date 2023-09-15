package com.example.sistemaoperativo

import android.view.LayoutInflater
import android.view.TextureView
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.sistemaoperativo.Models.DispositivoModel

class DispositivoAdapter: RecyclerView.Adapter<DispositivoAdapter.dispositivoViewHolder>(){
    private var dispositivoList: ArrayList<DispositivoModel> = ArrayList()
    private var onClickItem: ((DispositivoModel)->Unit)? = null
    private var onClickDeleteItem: ((DispositivoModel)->Unit)? = null
    fun addItems(items: ArrayList<DispositivoModel>){
        this.dispositivoList = items
        notifyDataSetChanged()
    }
    fun setOnClickItem(callback: (DispositivoModel) -> Unit){
        this.onClickItem = callback
    }
    fun setOnClickDeleteItem(callback: (DispositivoModel) -> Unit){
        this.onClickDeleteItem = callback
    }
    override fun onCreateViewHolder( parent: ViewGroup, viewType: Int) =  dispositivoViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.card_items_dispositivo,parent,false)
    )

    override fun onBindViewHolder(holder: dispositivoViewHolder, position: Int) {
        val dispositivo = dispositivoList[position]
        holder.bindView(dispositivo)
        holder.itemView.setOnClickListener { onClickItem?.invoke(dispositivo) }
        holder.btnDelete.setOnClickListener { onClickDeleteItem?.invoke(dispositivo) }
    }

    override fun getItemCount(): Int {
        return dispositivoList.size

    }
    class dispositivoViewHolder(var view: View): RecyclerView.ViewHolder(view){
        private var id = view.findViewById<TextView>(R.id.txt_card_dispositivo_id)
        private var nombre = view.findViewById<TextView>(R.id.txt_card_dispositivo_nombre)
        var btnDelete = view.findViewById<Button>(R.id.btn_Borrar_Dispositivo)
        fun bindView(dispositivo: DispositivoModel){
            id.text = dispositivo.identificador.toString()
            nombre.text = dispositivo.nombre
        }
    }
}