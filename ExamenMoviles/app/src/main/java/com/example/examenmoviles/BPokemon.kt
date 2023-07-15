package com.example.examenmoviles

class BPokemon{
    var id:Int=0
    lateinit var nombre: String
    lateinit var descripcion: String
    var idEntrenador: Int =0
    constructor(){
    }
    constructor(
        _id: Int,
        _nombre: String,
        _descripcion: String,
        _idEntrenador: Int
    ){
        id=_id
        nombre=_nombre
        descripcion=_descripcion
        idEntrenador=_idEntrenador
    }

    override fun toString(): String {
        return "${nombre}: ${descripcion}"
    }






}