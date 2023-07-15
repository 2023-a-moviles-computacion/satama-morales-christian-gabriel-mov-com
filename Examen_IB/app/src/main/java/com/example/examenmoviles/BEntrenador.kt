package com.example.examenmoviles

class BEntrenador{
    var  id: Int = 0
    lateinit var nombre: String
    lateinit var descripcion: String

    constructor(){
    }
    constructor(
        _id: Int,
        _nombre: String?,
        _descripcion: String
        ){
        id=_id
        if (_nombre != null) {
            nombre=_nombre
        }
        descripcion=_descripcion
    }
    override fun toString(): String {
        return "${nombre}: ${descripcion}"
    }

}